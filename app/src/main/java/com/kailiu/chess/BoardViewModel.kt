package com.kailiu.chess

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kailiu.chess.database.Board
import com.kailiu.chess.database.BoardDao
import com.kailiu.chess.database.BoardDatabase
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class BoardViewModel(application: Application): AndroidViewModel(application) {
    var boardDatabase = BoardDatabase.getInstance(application.applicationContext)

    fun getGame(type: BoardType): LiveData<Board> {
        return boardDatabase.getGame(type.name)
    }

    fun setGame(type: BoardType, turn: Int, board: ArrayList<Piece>, white: ArrayList<Piece>, black: ArrayList<Piece>) {
        boardDatabase.setGame(type.name, turn, board, white, black)
    }
}