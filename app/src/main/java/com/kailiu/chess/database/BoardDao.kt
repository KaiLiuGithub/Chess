package com.kailiu.chess.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kailiu.chess.pieces.Piece

@Dao
abstract class BoardDao {
    @Query("SELECT * FROM board WHERE game = :type LIMIT 1")
    abstract fun getGame(type: String): LiveData<Board>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setGame(board: Board)
}