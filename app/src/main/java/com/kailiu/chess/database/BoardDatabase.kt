package com.kailiu.chess.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

@Database(
    entities = [Board::class],
    version = 1)
@TypeConverters(PiecesListTypeConverter::class)
abstract class BoardDatabase: RoomDatabase() {
    companion object {
        val DB_NAME = "boardDatabase.db"
        
        fun getInstance(context: Context): BoardDatabase {
            return Room.databaseBuilder(context, BoardDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    abstract fun boardDao(): BoardDao
    
    fun getGame(type: String): LiveData<Board> {
        return boardDao().getGame(type)
    }
    
    fun setGame(type: String, turn: Int, board: ArrayList<Piece>, white: ArrayList<Piece>, black: ArrayList<Piece>) {
        boardDao().setGame(Board(type, turn, board, white, black))
    }
}