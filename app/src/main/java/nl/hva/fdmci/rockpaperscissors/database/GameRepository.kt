package nl.hva.fdmci.rockpaperscissors.database

import android.content.Context
import nl.hva.fdmci.rockpaperscissors.model.Game

class GameRepository(context: Context) {

    private val gameDAO: GameDAO

    init {
        val database =
            GameRoomDatabase.getDatabase(
                context
            )
        gameDAO = database!!.gameDAO()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDAO.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDAO.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDAO.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDAO.deleteAllGames()
    }

    suspend fun getGamesByWinner(winner: String): Int {
        return gameDAO.getGamesByWinner(winner)
    }
}