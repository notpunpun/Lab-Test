/*
Author: Nuha Nordin
Program: Lab Test - MindSharpener
Date: 4 January 2024
*/
package com.example.mindsharpener

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var levelButton: Button
    private lateinit var number1TextView: TextView
    private lateinit var operatorTextView: TextView
    private lateinit var number2TextView: TextView
    private lateinit var editText: EditText
    private lateinit var pointTextView: TextView

    private var level: String = ""
    private var num1: Int = 0
    private var num2: Int = 0
    private var operator: Int = 0
    private var userAnswer: Int = 0
    private var currentPoint: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add AppBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize views
        radioGroup = findViewById(R.id.level)
        levelButton = findViewById(R.id.button)
        number1TextView = findViewById(R.id.number1)
        operatorTextView = findViewById(R.id.operator)
        number2TextView = findViewById(R.id.number2)
        editText = findViewById(R.id.answer)
        pointTextView = findViewById(R.id.point)

        // Set listener for the RadioGroup level
        radioGroup.setOnCheckedChangeListener { _, _ -> generateQuestion()
        }

        // Set listener for button
        levelButton.setOnClickListener { checkAnswer()
        }

        generateQuestion()
    }

    private fun generateQuestion() {
        // Check level
        when (radioGroup.checkedRadioButtonId) {
            R.id.radio_i3 -> level = "i3"
            R.id.radio_i5 -> level = "i5"
            R.id.radio_i7 -> level = "i7"
        }

        // Generate random numbers based on level
        num1 = generateRandomNumber(level)
        num2 = generateRandomNumber(level)

        // Generate random operator
        operator = Random.nextInt(4)

        // Display the generated question
        number1TextView.text = num1.toString()
        operatorTextView.text = getOperatorSymbol(operator)
        number2TextView.text = num2.toString()
    }

    private fun generateRandomNumber(level: String): Int {
        val maxRange = when (level) {
            "i3" -> 9
            "i5" -> 99
            "i7" -> 999
            else -> 9
        }
        return Random.nextInt(maxRange + 1)
    }

    private fun getOperatorSymbol(operator: Int): String {
        return when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> "+"
        }
    }

    private fun checkAnswer() {
        // Get input answer
        val answerText = editText.text.toString().trim()

        if (answerText.isBlank()) {
            // Display error message if answer is blank
            Toast.makeText(applicationContext, "Please enter an answer", Toast.LENGTH_SHORT).show()
            return
        }

        // Parse answer to Int
        userAnswer = answerText.toInt()

        // Calculate the correct answer based on the generated numbers and operator
        val correctAnswer = calculateAnswer(num1, operator, num2)

        // Check if answer is correct
        if (userAnswer == correctAnswer) {
            // Increment the point
            Toast.makeText(applicationContext, "Congrats! One point gained", Toast.LENGTH_LONG).show()
            currentPoint++
        } else {
            // Decrement the point
            Toast.makeText(applicationContext, "Ops! One point deducted", Toast.LENGTH_LONG).show()
            currentPoint--
        }

        // Update point
        pointTextView.text = currentPoint.toString()

        // Generate new question
        generateQuestion()

        // Clear the answer for the next input
        editText.text.clear()
    }


    private fun calculateAnswer(num1: Int, operator: Int, num2: Int): Int {
        return when (operator) {
            0 -> num1 + num2
            1 -> num1 - num2
            2 -> num1 * num2
            3 -> if (num2 != 0) num1 / num2 else 0
            else -> 0
        }
    }
}
