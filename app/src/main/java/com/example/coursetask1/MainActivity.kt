package com.example.coursetask1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var board = Array(3) { CharArray(3) { ' ' } }
    private var currentPlayer = 'X'
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        // Set up buttons for the board
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                val tag = button.tag.toString().split(",")
                val row = tag[0].toInt()
                val col = tag[1].toInt()

                if (!isGameOver && board[row][col] == ' ') {
                    board[row][col] = currentPlayer
                    button.text = currentPlayer.toString()

                    if (checkWin()) {
                        statusTextView.text = "Player $currentPlayer Wins!"
                        isGameOver = true
                        playAgainButton.visibility = View.VISIBLE
                    } else if (isDraw()) {
                        statusTextView.text = "It's a Draw!!!"
                        isGameOver = true
                        playAgainButton.visibility = View.VISIBLE
                    } else {
                        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                        statusTextView.text = "Player $currentPlayer's Turn"
                    }
                }
            }
        }

        // Play Again Button Logic
        playAgainButton.setOnClickListener {
            resetGame(gridLayout, statusTextView)
            playAgainButton.visibility = View.GONE
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isDraw(): Boolean {
        for (row in board) {
            if (row.contains(' ')) return false
        }
        return true
    }

    private fun resetGame(gridLayout: GridLayout, statusTextView: TextView) {
        board = Array(3) { CharArray(3) { ' ' } }
        currentPlayer = 'X'
        isGameOver = false
        statusTextView.text = "Player X's Turn"

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
        }
    }
}
