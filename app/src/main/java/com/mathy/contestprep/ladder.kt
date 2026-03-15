package com.mathy.contestprep

import android.content.Context
import android.os.Build
import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView

class ladder : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var topBar: ConstraintLayout
    private lateinit var db: DBhandler
    private lateinit var adapter: LadderAdapter

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

        val items = buildLadderItems(this, db)

        adapter = LadderAdapter(
            context = this,
            items = items,
            db = db
        )

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstVisible != RecyclerView.NO_POSITION) {
                    val colorRes = items[firstVisible].backgroundColorRes
                    setTopBarColor(colorRes)
                }
            }
        })
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
}