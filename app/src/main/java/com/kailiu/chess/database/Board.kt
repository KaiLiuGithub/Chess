package com.kailiu.chess.database

import androidx.room.*
import com.kailiu.chess.pieces.Piece

@Entity(
    tableName = "board",
    indices = [Index(value = ["game"], unique = true)]
)
@TypeConverters(value = [PiecesListTypeConverter::class])
class Board(
    @PrimaryKey
    @ColumnInfo(name = "game")
    val game: String,
    @ColumnInfo(name = "turn")
    val turn: Int,
    @ColumnInfo(name = "board")
    val board: ArrayList<Piece>,
    @ColumnInfo(name = "white")
    val white: ArrayList<Piece>,
    @ColumnInfo(name = "black")
    val black: ArrayList<Piece>
)