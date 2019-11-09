package nl.hva.fdmci.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    @ColumnInfo(name = "user_move")
    var user_move: List<String>,
    @ColumnInfo(name = "computer_move")
    var computer_move: List<String>,
    @ColumnInfo(name = "winner")
    var winner: String
) : Parcelable