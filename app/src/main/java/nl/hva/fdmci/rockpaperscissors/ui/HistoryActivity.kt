package nl.hva.fdmci.rockpaperscissors.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.fdmci.rockpaperscissors.R
import nl.hva.fdmci.rockpaperscissors.database.GameRepository
import nl.hva.fdmci.rockpaperscissors.model.Game

class HistoryActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository
    private val gameHistory = arrayListOf<Game>()
    private val gameAdapter =
        GameAdapter(gameHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gameRepository = GameRepository(this)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        menu?.findItem(R.id.action_history)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu?.findItem(R.id.action_history)?.isVisible = false
        menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }

    private fun initViews() {
        rvGameHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGameHistory.adapter = gameAdapter
        rvGameHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        refreshHistory()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete -> {
            CoroutineScope(Dispatchers.Main).launch {
                gameRepository.deleteAllGames()
            }
            Toast.makeText(this@HistoryActivity,"Game history has been cleared.",
                Toast.LENGTH_LONG).show()
            refreshHistory()
            true
        }
        android.R.id.home ->{
            this.finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun refreshHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            val gameHistory = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryActivity.gameHistory.clear()
            this@HistoryActivity.gameHistory.addAll(gameHistory)
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()

        }
    }

}