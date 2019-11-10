package nl.hva.fdmci.rockpaperscissors.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import nl.hva.fdmci.rockpaperscissors.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
    }

    private fun initViews() {
        resultTv.text = ""

        rockBtn.setOnClickListener {
            playerMoveImg.setImageResource(R.drawable.rock)
            fight(R.drawable.rock)
        }

        paperBtn.setOnClickListener {
            playerMoveImg.setImageResource(R.drawable.paper)
            fight(R.drawable.paper)
        }

        scissorsBtn.setOnClickListener {
            playerMoveImg.setImageResource(R.drawable.scissors)
            fight(R.drawable.scissors)
        }
    }

    private fun fight(resId: Int) {
        when(resId) {
            R.drawable.rock -> {
                when (computerMove()) {
                    R.drawable.rock -> resultTv.text = getString(R.string.draw)
                    R.drawable.paper -> resultTv.text = getString(R.string.computerWins)
                    else -> resultTv.text = getString(R.string.youWin)
                }
            }
            R.drawable.scissors -> {
                when (computerMove()) {
                    R.drawable.rock -> resultTv.text = getString(R.string.computerWins)
                    R.drawable.paper -> resultTv.text = getString(R.string.youWin)
                    else -> resultTv.text = getString(R.string.draw)
                }
            }
            R.drawable.paper -> {
                when (computerMove()) {
                    R.drawable.rock -> resultTv.text = getString(R.string.youWin)
                    R.drawable.paper -> resultTv.text = getString(R.string.draw)
                    else -> resultTv.text = getString(R.string.computerWins)
                }
            }
            else -> {
                Toast.makeText(this, "Something went wrong! Try again.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun computerMove() : Int {
        when((0..2).random()) {
            0 -> {
                computerMoveImg.setImageResource(R.drawable.rock)
                return R.drawable.rock
            }
            1 -> {
                computerMoveImg.setImageResource(R.drawable.scissors)
                return R.drawable.scissors
            }
            2 -> {
                computerMoveImg.setImageResource(R.drawable.paper)
                return R.drawable.paper
            }
        }

        return -1
    }
}
