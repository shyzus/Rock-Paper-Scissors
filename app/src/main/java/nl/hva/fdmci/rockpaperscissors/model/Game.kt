package nl.hva.fdmci.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    @ColumnInfo(name = "user_move")
    var user_move: Int,
    @ColumnInfo(name = "computer_move")
    var computer_move: Int,
    @ColumnInfo(name = "winner")
    var winner: String,
    @ColumnInfo(name = "date")
    var date: Date
) : Parcelable