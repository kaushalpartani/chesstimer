package com.example.chesstimer

import android.os.CountDownTimer
import android.view.View
import android.widget.Button

class TwoPlayerTimer (time: Long, var whiteButton: Button, var blackButton: Button) {
    /*
    This class will be used for the creation of the game's clock. This involves 2 countdown timers.
    One for Black and one for White. They will work synchronously, swapping which timer is running
    based on button click.

    Params: time -> time per clock in ms.
     */

    var whiteTime: Long = time
    var blackTime: Long = time
    lateinit var whiteTimer: CountDownTimer
    lateinit var blackTimer: CountDownTimer
    var gameState: State = State.GAME_PREP

    init {
        whiteTimer = makeWhiteTimer(time)
        blackTimer = makeBlackTimer(time)
    }

    private fun makeWhiteTimer(time: Long) : CountDownTimer{
        val whiteTimer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                whiteTime = millisUntilFinished
                whiteButton.text = convertMStoString(whiteTime)
            }
            override fun onFinish() {
                whiteButton.visibility = View.GONE
                blackButton.text = "BLACK WIN"
            }
        }
        return whiteTimer
    }

    private fun makeBlackTimer(time: Long) : CountDownTimer{
        val blackTimer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                blackTime = millisUntilFinished
                blackButton.text = convertMStoString(blackTime)
            }
            override fun onFinish() {
                blackButton.visibility = View.GONE
                whiteButton.text = "WHITE WIN"
            }
        }
        return blackTimer
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

    fun startGame() {
        gameState = State.WHITE_TURN
        whiteTimer.start()
    }

    fun flipTurn() {
        if (gameState == State.WHITE_TURN) {
            gameState = State.BLACK_TURN
            whiteTimer.cancel()
            blackTimer = makeBlackTimer(blackTime)
            blackTimer.start()
        } else {
            gameState = State.WHITE_TURN
            blackTimer.cancel()
            whiteTimer = makeWhiteTimer(whiteTime)
            whiteTimer.start()
        }
    }
}