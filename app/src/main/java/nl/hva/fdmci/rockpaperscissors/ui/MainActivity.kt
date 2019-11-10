package nl.hva.fdmci.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import nl.hva.fdmci.rockpaperscissors.R
import nl.hva.fdmci.rockpaperscissors.database.GameRepository
import nl.hva.fdmci.rockpaperscissors.model.Game
import java.time.Instant
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)

        initViews()
    }

    private fun initViews() {
        resultTv.text = ""

        getStatistics()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        menu?.findItem(R.id.action_history)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu?.findItem(R.id.action_delete)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_history -> {
            startActivity(Intent(this, HistoryActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateStatistics(win: Int, draw: Int, lose: Int) {
        statisticsTv.text = "Win: $win Draw: " +
                "$draw Lose: $lose"
    }

    private fun fight(resId: Int) {
        when(resId) {
            R.drawable.rock -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        resultTv.text = getString(R.string.draw)
                        submitGame(R.drawable.rock, resId, "Draw")
                    }
                    R.drawable.paper -> {
                        resultTv.text = getString(R.string.computerWins)
                        submitGame(R.drawable.paper, resId, "Computer")
                    }
                    else -> {
                        resultTv.text = getString(R.string.youWin)
                        submitGame(R.drawable.scissors, resId, "You")
                    }
                }
            }
            R.drawable.scissors -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        resultTv.text = getString(R.string.computerWins)
                        submitGame(R.drawable.rock, resId, "Computer")
                    }
                    R.drawable.paper -> {
                        resultTv.text = getString(R.string.youWin)
                        submitGame(R.drawable.paper, resId, "You")
                    }
                    else -> {
                        resultTv.text = getString(R.string.draw)
                        submitGame(R.drawable.scissors, resId, "Draw")
                    }
                }
            }
            R.drawable.paper -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        resultTv.text = getString(R.string.youWin)
                        submitGame(R.drawable.rock, resId, "You")
                    }
                    R.drawable.paper -> {
                        resultTv.text = getString(R.string.draw)
                        submitGame(R.drawable.paper, resId, "Draw")
                    }
                    else -> {
                        resultTv.text = getString(R.string.computerWins)
                        submitGame(R.drawable.scissors, resId, "Computer")
                    }
                }
            }
            else -> {
                Toast.makeText(this, "Something went wrong! Try again.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitGame(computerMove: Int, userMove: Int, winner: String) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(Game(null, userMove, computerMove, winner, Date.from(Instant.now())))
            }
        }.invokeOnCompletion {
            getStatistics()
        }
    }

    private fun getStatistics() {

        var you = 0
        var draw = 0
        var computer = 0

       mainScope.launch {
           you = gameRepository.getGamesByWinner("You")
           draw = gameRepository.getGamesByWinner("Draw")
           computer = gameRepository.getGamesByWinner("Computer")
       }.invokeOnCompletion {
           updateStatistics(you, draw, computer)
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
