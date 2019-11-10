package nl.hva.fdmci.rockpaperscissors.database

import android.content.Context
import androidx.room.*
import nl.hva.fdmci.rockpaperscissors.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class GameRoomDatabase : RoomDatabase() {

    abstract fun gameDAO(): GameDAO

    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var gameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (gameRoomDatabaseInstance == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (gameRoomDatabaseInstance == null) {
                        gameRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                GameRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gameRoomDatabaseInstance
        }
    }
}