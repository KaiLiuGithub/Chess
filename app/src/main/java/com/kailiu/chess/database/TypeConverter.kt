package com.kailiu.chess.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.chess.*
import com.kailiu.chess.pieces.shogi.*
import com.kailiu.chess.pieces.xiangqi.*
import kotlin.coroutines.coroutineContext

class PiecesListTypeConverter {
    @TypeConverter
    fun toListOfPieces(value: String): ArrayList<Piece> {
        if (value.isEmpty()) {
            return ArrayList()
        }

        val list = Gson().fromJson(value, Array<Piece>::class.java).toCollection(ArrayList())

        return cast(list)
    }

    @TypeConverter
    fun fromListOfPieces(value: ArrayList<Piece>): String {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(value)
    }

    private fun cast(list: ArrayList<Piece>): ArrayList<Piece> {
        var newlist = ArrayList<Piece>()
        for (i in list) {
            var piece = i
            when (i.board) {
                BoardType.CHESS -> {
                    piece = when (i.rank) {
                        1 -> King(i.isWhite)
                        2 -> Queen(i.isWhite)
                        3 -> Rook(i.isWhite)
                        4 -> Knight(i.isWhite)
                        5 -> Bishop(i.isWhite)
                        6 -> Pawn(i.isWhite)
                        else -> i
                    }
                }
                BoardType.XIANGQI -> {
                    piece = when (i.rank) {
                        1 -> Shuai(i.isWhite)
                        2 -> Shi(i.isWhite)
                        3 -> Ju(i.isWhite)
                        4 -> Ma(i.isWhite)
                        5 -> Xiang(i.isWhite)
                        6 -> Pao(i.isWhite)
                        7 -> Bing(i.isWhite)
                        else -> i
                    }
                }
                BoardType.SHOGI -> {
                    piece = when(i.rank) {
                        1 -> Osho(i.isWhite)
                        2 -> Hisha(i.isWhite)
                        3 -> Kakugyo(i.isWhite)
                        4 -> Kinsho(i.isWhite)
                        5 -> Ginsho(i.isWhite)
                        6 -> Keima(i.isWhite)
                        7 -> Kyosha(i.isWhite)
                        8 -> Fuhyo(i.isWhite)
                        else -> i
                    }
                }
            }

            piece.hasMoved = i.hasMoved
            piece.isEmpty = i.isEmpty
            piece.isPromoted = i.isPromoted
            piece.position = i.position

            newlist.add(piece)
        }

        return newlist
    }
}