package com.mathy.contestprep

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import kotlin.math.abs
import kotlin.math.max

class ladder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var topBar: ConstraintLayout
    private lateinit var db: DBhandler
    private lateinit var adapter: LadderAdapter
    private lateinit var items: MutableList<LadderItem>
    private lateinit var layoutManager: LinearLayoutManager

    private val prefsName = "ladder_prefs"
    private val lastLadderKey = "last_ladder_position"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ladder)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DBhandler(this)
        ensureStarterData()

        topBar = findViewById(R.id.constraintLayout2)
        recyclerView = findViewById(R.id.recyclerLadder)

        setTopBarColor(R.color.action)

        val backer = findViewById<ImageView>(R.id.backer)
        backer.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    backer.background = ContextCompat.getDrawable(this, R.drawable.backerbg)
                }

                MotionEvent.ACTION_UP -> {
                    backer.background = ContextCompat.getDrawable(this, R.drawable.baup)
                    finish()
                }
            }
            true
        }

        items = loadLadderItemsFromAssets()

        adapter = LadderAdapter(
            context = this,
            items = items,
            db = db
        )

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        val savedPosition = getSavedLadderPosition()
        if (savedPosition in items.indices) {
            animateToSavedPosition(savedPosition)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)

                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstVisible != RecyclerView.NO_POSITION && firstVisible < items.size) {
                    setTopBarColor(items[firstVisible].backgroundColorRes)
                    saveLadderPosition(firstVisible)
                }
            }
        })
    }

    private fun animateToSavedPosition(targetPosition: Int) {
        if (targetPosition !in items.indices) return

        recyclerView.post {
            val currentPosition = layoutManager.findFirstVisibleItemPosition().let {
                if (it == RecyclerView.NO_POSITION) 0 else it
            }

            if (currentPosition == targetPosition) {
                setTopBarColor(items[targetPosition].backgroundColorRes)
                return@post
            }

            val visibleChild = recyclerView.getChildAt(0)
            val estimatedItemHeight = visibleChild?.height ?: (resources.displayMetrics.density * 700).toInt()

            val distanceItems = abs(targetPosition - currentPosition)
            val estimatedDistancePx = max(distanceItems * estimatedItemHeight, 1)

            val totalDurationMs = 1000f
            val msPerPixel = totalDurationMs / estimatedDistancePx.toFloat()

            val smoothScroller = object : LinearSmoothScroller(this@ladder) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return msPerPixel
                }
            }

            smoothScroller.targetPosition = targetPosition
            layoutManager.startSmoothScroll(smoothScroller)

            recyclerView.postDelayed({
                if (targetPosition in items.indices) {
                    setTopBarColor(items[targetPosition].backgroundColorRes)
                }
            }, totalDurationMs.toLong())
        }
    }

    private fun ensureStarterData() {
        val cursor = db.readableDatabase.query(
            "ladderState",
            arrayOf("name", "state"),
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            if (!it.moveToFirst()) {
                db.starter()
            }
        }
    }

    private fun setTopBarColor(colorRes: Int) {
        val color = ContextCompat.getColor(this, colorRes)
        topBar.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = color
        }
    }

    private fun saveLadderPosition(position: Int) {
        getSharedPreferences(prefsName, MODE_PRIVATE)
            .edit()
            .putInt(lastLadderKey, position)
            .apply()
    }

    private fun getSavedLadderPosition(): Int {
        return getSharedPreferences(prefsName, MODE_PRIVATE)
            .getInt(lastLadderKey, 0)
    }

    private fun loadLadderItemsFromAssets(): MutableList<LadderItem> {
        val jsonString = assets.open("ladder_data.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val result = mutableListOf<LadderItem>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)

            val number = obj.getInt("number")
            val key = obj.getString("key")
            val imageName = obj.getString("imageName")
            val colorName = obj.getString("colorName")
            val prompt = obj.getString("prompt")
            val answerLabel = obj.getString("answerLabel")
            val inputTypeName = obj.getString("inputType")

            val imageRes = resources.getIdentifier(imageName, "drawable", packageName)
            val colorRes = resources.getIdentifier(colorName, "color", packageName)

            val acceptedAnswersJson = obj.getJSONArray("acceptedAnswers")
            val acceptedAnswers = mutableListOf<String>()
            for (j in 0 until acceptedAnswersJson.length()) {
                acceptedAnswers.add(acceptedAnswersJson.getString(j))
            }

            val inputType = when (inputTypeName) {
                "choice" -> {
                    val optionsJson = obj.getJSONArray("options")
                    val options = mutableListOf<String>()
                    for (j in 0 until optionsJson.length()) {
                        options.add(optionsJson.getString(j))
                    }
                    LadderInputType.Choice(options)
                }

                "single_input" -> {
                    val hint = if (obj.has("hint")) obj.getString("hint") else ""
                    val keyboard = if (obj.has("keyboard")) obj.getString("keyboard") else "number"
                    LadderInputType.SingleInput(
                        hint = hint,
                        inputMode = if (keyboard == "decimal") InputMode.DECIMAL else InputMode.NUMBER
                    )
                }

                "triple_input" -> {
                    LadderInputType.TripleInput(
                        hint1 = if (obj.has("hint1")) obj.getString("hint1") else "",
                        hint2 = if (obj.has("hint2")) obj.getString("hint2") else "",
                        hint3 = if (obj.has("hint3")) obj.getString("hint3") else ""
                    )
                }

                else -> {
                    LadderInputType.SingleInput("", InputMode.NUMBER)
                }
            }

            result.add(
                LadderItem(
                    number = number,
                    key = key,
                    imageRes = imageRes,
                    backgroundColorRes = colorRes,
                    prompt = prompt,
                    answerLabel = answerLabel,
                    inputType = inputType,
                    acceptedAnswers = acceptedAnswers
                )
            )
        }

        return result
    }
}