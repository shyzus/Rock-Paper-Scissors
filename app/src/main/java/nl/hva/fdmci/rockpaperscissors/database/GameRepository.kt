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

    suspend fun insertGame(Game: Game) {
        gameDAO.insertGame(Game)
    }

    suspend fun deleteGame(Game: Game) {
        gameDAO.deleteGame(Game)
    }

    suspend fun deleteAllGames() {
        gameDAO.deleteAllGames()
    }
}