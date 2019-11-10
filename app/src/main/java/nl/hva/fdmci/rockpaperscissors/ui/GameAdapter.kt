package nl.hva.fdmci.rockpaperscissors.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.component_game.view.*
import nl.hva.fdmci.rockpaperscissors.R
import nl.hva.fdmci.rockpaperscissors.model.Game

class GameAdapter(private val games: ArrayList<Game>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

        private lateinit var context: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.component_game, parent, false)
            )
        }
        override fun getItemCount(): Int {
            return games.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(games[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(game : Game) {
                itemView.gameDate.text = game.date.toString()

                itemView.computerMoveImg.setImageResource(game.computer_move)

                when(game.winner){
                    "Draw" -> itemView.gameResultTitle.text = game.winner
                    "Computer" -> itemView.gameResultTitle.text = game.winner + " wins!"
                    "You" -> itemView.gameResultTitle.text = game.winner + " win!"
                }

                itemView.playerMoveImg.setImageResource(game.user_move)
            }
        }
}