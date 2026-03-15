package com.mathy.contestprep

import android.content.Context
import android.database.sqlite.SQLiteException
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class LadderAdapter(
    private val context: Context,
    private val items: MutableList<LadderItem>,
    private val db: DBhandler
) : RecyclerView.Adapter<LadderAdapter.LadderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LadderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ladder, parent, false)
        return LadderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LadderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class LadderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val root: View = itemView.findViewById(R.id.itemRoot)
        private val image: ImageView = itemView.findViewById(R.id.imageQuestion)
        private val prompt: TextView = itemView.findViewById(R.id.textPrompt)
        private val answer: TextView = itemView.findViewById(R.id.textAnswer)
        private val button: Button = itemView.findViewById(R.id.buttonSubmit)

        private val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
        private val radio1: RadioButton = itemView.findViewById(R.id.radio1)
        private val radio2: RadioButton = itemView.findViewById(R.id.radio2)

        private val singleInput: EditText = itemView.findViewById(R.id.editSingle)

        private val tripleContainer: LinearLayout = itemView.findViewById(R.id.tripleContainer)
        private val triple1: EditText = itemView.findViewById(R.id.editTriple1)
        private val triple2: EditText = itemView.findViewById(R.id.editTriple2)
        private val triple3: EditText = itemView.findViewById(R.id.editTriple3)

        fun bind(item: LadderItem) {
            root.setBackgroundColor(ContextCompat.getColor(context, item.backgroundColorRes))
            image.setImageResource(item.imageRes)
            prompt.text = item.prompt

            val alreadyDone = isCompleted(item.key)
            if (alreadyDone) {
                showCompletedState(item)
            } else {
                showFreshState(item)
            }

            when (val type = item.inputType) {
                is LadderInputType.Choice -> {
                    radioGroup.visibility = View.VISIBLE
                    singleInput.visibility = View.GONE
                    tripleContainer.visibility = View.GONE

                    radio1.text = type.options.getOrNull(0) ?: ""
                    radio2.text = type.options.getOrNull(1) ?: ""
                }

                is LadderInputType.SingleInput -> {
                    radioGroup.visibility = View.GONE
                    singleInput.visibility = View.VISIBLE
                    tripleContainer.visibility = View.GONE
                    singleInput.hint = type.hint
                    singleInput.inputType = when (type.inputMode) {
                        InputMode.NUMBER -> InputType.TYPE_CLASS_NUMBER
                        InputMode.DECIMAL -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    }
                }

                is LadderInputType.TripleInput -> {
                    radioGroup.visibility = View.GONE
                    singleInput.visibility = View.GONE
                    tripleContainer.visibility = View.VISIBLE
                    triple1.hint = type.hint1
                    triple2.hint = type.hint2
                    triple3.hint = type.hint3
                }
            }

            button.setOnClickListener {
                if (alreadyDone || !button.isEnabled) return@setOnClickListener

                val userAnswer = getUserAnswer(item)
                if (userAnswer == null) {
                    Toast.makeText(context, "Make a selection", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (item.isCorrect(userAnswer)) {
                    saveCompletion(item)
                    showCompletedState(item)
                    Toast.makeText(context, "Congratulations!!!", Toast.LENGTH_SHORT).show()
                } else {
                    if (item.key == "one") {
                        try {
                            val msg = db.readVal("one")
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        } catch (e: SQLiteException) {
                            Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        private fun getUserAnswer(item: LadderItem): String? {
            return when (item.inputType) {
                is LadderInputType.Choice -> {
                    val checkedId = radioGroup.checkedRadioButtonId
                    if (checkedId == -1) null
                    else itemView.findViewById<RadioButton>(checkedId).text.toString()
                }

                is LadderInputType.SingleInput -> {
                    val value = singleInput.text.toString().trim()
                    if (value.isEmpty()) null else value
                }

                is LadderInputType.TripleInput -> {
                    val a = triple1.text.toString().trim()
                    val b = triple2.text.toString().trim()
                    val c = triple3.text.toString().trim()
                    if (a.isEmpty() || b.isEmpty() || c.isEmpty()) null else a + b + c
                }
            }
        }

        private fun saveCompletion(item: LadderItem) {
            try {
                db.update(item.key, "true", item.key)
            } catch (e: SQLiteException) {
                e.printStackTrace()
                Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show()
            }
        }

        private fun showCompletedState(item: LadderItem) {
            answer.text = item.answerLabel
            button.text = "COMPLETED"
            button.isEnabled = false
        }

        private fun showFreshState(item: LadderItem) {
            answer.text = "ANS"
            button.text = "NEXT LADDER"
            button.isEnabled = true
        }

        private fun isCompleted(key: String): Boolean {
            return try {
                db.readVal(key).equals("true", ignoreCase = true)
            } catch (_: Exception) {
                false
            }
        }
    }
}