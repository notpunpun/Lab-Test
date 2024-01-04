package com.example.mindsharpener

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mindsharpener.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {


    class MathQuizFragment : Fragment() {

        private lateinit var radioButton1: RadioButton
        private lateinit var radioButton2: RadioButton
        private lateinit var radioButton3: RadioButton
        private lateinit var textview4: TextView
        private lateinit var textview5: TextView
        private lateinit var textview6: TextView
        private lateinit var editText: EditText
        private lateinit var buttonSubmit: Button
        private lateinit var textviewScore: TextView

        private var currentScore = 0

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_first, container, false)

            // Initialize views
            radioButton1 = view.findViewById(R.id.radio_button1)
            radioButton2 = view.findViewById(R.id.radio_button2)
            radioButton3 = view.findViewById(R.id.radio_button3)
            textview4 = view.findViewById(R.id.textview4)
            textview5 = view.findViewById(R.id.textview5)
            textview6 = view.findViewById(R.id.textview6)
            editText = view.findViewById(R.id.edit_text)
            buttonSubmit = view.findViewById(R.id.button_submit)
//            textviewScore = view.findViewById(R.id.textview_score)

            // Set up click listener for the submit button
            buttonSubmit.setOnClickListener {
                checkAnswer()
                displayQuestion()
            }

            // Display the first question when the fragment is created
            displayQuestion()

            return view
        }

        private fun displayQuestion() {
            // Instantiate random class
            val random = Random()

            // Determine which radio button is checked
            val level = when {
                radioButton1.isChecked -> 3
                radioButton2.isChecked -> 5
                radioButton3.isChecked -> 7
                else -> 3 // Default level
            }

            // Generate 2 random numbers based on the checked radio button
            val firstNumber = generateRandomNumber(level)
            val secondNumber = generateRandomNumber(level)

            // Generate a random number for the operator
            val operator = random.nextInt(4) // 0: +, 1: -, 2: *, 3: /

            // Display generated numbers for operands and symbol for the generated operator
            textview4.text = firstNumber.toString()
            textview5.text = getOperatorSymbol(operator)
            textview6.text = secondNumber.toString()

            // Clear the user's previous answer
            editText.text.clear()
        }

        private fun checkAnswer() {
            val userAnswer = editText.text.toString().trim()

            // Get the first number, second number, and operator from the displayed question
            val firstNumber = textview4.text.toString().toInt()
            val secondNumber = textview6.text.toString().toInt()
            val operator = getOperatorSymbolInverse(textview5.text.toString())

            // Calculate the correct answer
            val correctAnswer = calculateAnswer(firstNumber, secondNumber, operator)

            // Compare the answer with the user's answer
            if (userAnswer == correctAnswer.toString()) {
                currentScore++
            } else {
                currentScore--
            }

            // Display the current score
            textviewScore.text = "Score: $currentScore"
        }

        private fun generateRandomNumber(level: Int): Int {
            val random = Random()
            return when (level) {
                3 -> random.nextInt(10) // Generate a random number between 0 and 9
                5 -> random.nextInt(100) // Generate a random number between 0 and 99
                7 -> random.nextInt(1000) // Generate a random number between 0 and 999
                else -> 0 // Default case
            }
        }

        private fun getOperatorSymbol(operator: Int): String {
            return when (operator) {
                0 -> "+" // Addition
                1 -> "-" // Subtraction
                2 -> "*" // Multiplication
                3 -> "/" // Division
                else -> "+"
            }
        }

        private fun getOperatorSymbolInverse(operator: String): Int {
            return when (operator) {
                "+" -> 0
                "-" -> 1
                "*" -> 2
                "/" -> 3
                else -> 0
            }
        }

        private fun calculateAnswer(firstNumber: Int, secondNumber: Int, operator: Int): Int {
            return when (operator) {
                0 -> firstNumber + secondNumber // Addition
                1 -> firstNumber - secondNumber // Subtraction
                2 -> firstNumber * secondNumber // Multiplication
                3 -> firstNumber / secondNumber // Division
                else -> 0
            }
        }
    }
}
