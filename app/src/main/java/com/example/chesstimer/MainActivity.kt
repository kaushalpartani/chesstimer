package com.example.chesstimer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var gameTimer: TwoPlayerTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.okButton).setOnClickListener { setGameTime(it) }

        findViewById<Button>(R.id.whiteTimer).setOnClickListener { whiteClick(it as Button) }
        findViewById<Button>(R.id.blackTimer).setOnClickListener { blackClick(it as Button) }
    }

    /* init game timers */
    private fun setGameTime(okButton: View) {
        val inputTime = findViewById<EditText>(R.id.editTextTime)
        val inputTimeText = inputTime.text.toString()

        val whiteButton = findViewById<Button>(R.id.whiteTimer)
        val blackButton = findViewById<Button>(R.id.blackTimer)

        hideInputs()
        showTimersAndSetTime(inputTimeText.toString())
        hideKeyboard(okButton)

        gameTimer = TwoPlayerTimer(convertStringToMS(inputTimeText), whiteButton,
            blackButton)
        gameTimer.startGame()
    }

    fun convertStringToMS(time: String) : Long {
        val timeSplit = time.split(":").map { it.toLong() }
        //only support format of min:sec
        if (timeSplit.size == 2) {
            return timeSplit[0] * 60000 + timeSplit[1] * 1000
        }
        else throw Exception("Need format of MIN:SEC")
    }

    fun convertMStoString(time: Long) : String {
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60

        return "${minutes.toString()}:${seconds.toString()}"
    }

    private fun hideInputs() {
        val inputTime = findViewById<EditText>(R.id.editTextTime)
        val okButton = findViewById<Button>(R.id.okButton)

        inputTime.visibility = View.GONE
        okButton.visibility = View.GONE
    }

    private fun showTimersAndSetTime(time: String) {
        val whiteButton = findViewById<Button>(R.id.whiteTimer)
        val blackButton = findViewById<Button>(R.id.blackTimer)

        whiteButton.text = time
        blackButton.text = time

        whiteButton.visibility = View.VISIBLE
        blackButton.visibility = View.VISIBLE
    }

    private fun hideKeyboard(button: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button.windowToken, 0)
    }

    /* on click events and helpers */

    private fun whiteClick(whiteTimeButton: Button) {
        gameTimer.flipTurn()
    }

    private fun blackClick(blackTimeButton: Button) {
        gameTimer.flipTurn()
    }


}