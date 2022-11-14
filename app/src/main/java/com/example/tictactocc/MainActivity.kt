package com.example.tictactocc

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var playerOneTurn = true
    private var playerOneMove = mutableListOf<Int>()
    private var playerTwoMove = mutableListOf<Int>()

    private val possibleWin : Array<List<Int>> = arrayOf(
        //horizontal lines
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),

        //vertical lines
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),

        //diagonal lines
        listOf(1, 5, 9),
        listOf(3, 5, 7)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBoard()

    }

    private fun setUpBoard() {
        var counter = 1

        val param1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )

        val  param2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        for(i in 1..3)
        {
          val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = param1
            param1.weight = 1.0F

            for(j in 1..3)
            {
                val button = Button(this)
                button.id = counter
                button.textSize = 42.0F
                button.setTextColor(ContextCompat.getColor(this,R.color.white))

                button.layoutParams=param2
                param2.weight = 1.0F
                button.setOnClickListener{
                    recordMove(it)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }

    }

    private fun recordMove(view: View) {
         val button = view as Button
         val id = button.id

        if(playerOneTurn)
        {
            playerOneMove.add(id)
            button.text= "0"
            button.isEnabled = false
            if(checkWin(playerOneMove))
            {
                showWinMessage(Player_one_name)
            }
            else{
                playerOneTurn = false
                togglePlayerTurn(Player_two_label,Player_one_lable)
            }
        }
        else{
            playerTwoMove.add(id)
            button.text="X"
            button.isEnabled=false
            if(checkWin(playerTwoMove))
            {
                showWinMessage((Player_two_name))
            }
            else{
                playerOneTurn=true
                togglePlayerTurn(Player_one_lable,Player_two_label)
            }
        }
    }


    private fun checkWin(moves: List<Int>): Boolean {

        var won = false
        if(moves.size >= 3)
        {
            run loops@{
                possibleWin.forEach {
                    if(moves.containsAll(it)){
                        won = true
                        return@loops
                    }
                }
            }
        }
        return  won
    }
    private fun togglePlayerTurn(playerOn: TextView, playerOff: TextView) {
        playerOff.setTextColor(
            ContextCompat.getColor(this, com.google.android.material.R.color.m3_switch_thumb_tint))
        playerOff.setTypeface(null, Typeface.NORMAL)
        playerOn.setTextColor(
            ContextCompat.getColor(this, R.color.teal_200))
        playerOn.setTypeface(null, Typeface.BOLD)
    }

    private fun showWinMessage(player: EditText) {
        var playerName = player.text.toString()
        if(playerName.isBlank()){
            playerName = player.hint.toString()
        }
        Toast.makeText(this, "Congratulations! $playerName You Won", Toast.LENGTH_SHORT).show()
    }
}