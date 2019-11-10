package nl.hva.fdmci.rockpaperscissors.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.hva.fdmci.rockpaperscissors.model.Game

@Dao
interface GameDAO {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM game_table WHERE game_table.winner = :winner")
    suspend fun getGamesByWinner(winner: String): Int
}