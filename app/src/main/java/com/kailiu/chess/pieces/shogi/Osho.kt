package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Osho(isWhite: Boolean? = false): Piece(isWhite) {
    init {
        isEmpty = false
        rank = 1
        board = BoardType.SHOGI
        unpromotedName = R.string.osho
        promotedName = R.string.gyokusho
        unpromotedImg = R.drawable.ic_osho
        promotedImg = R.drawable.ic_gyokusho
    }
    
    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 9 != 8
        val right = (position + 1) % 9 != 0
        val up = position - 9 >= 0
        val down = position + 9 < 80

        if (up and left) if(!list[position - 10].isSameColor(this) and !isInCheck(list, position - 10).second) spaces.add(Pair((position - 10), !list[position - 10].isEmpty))
        if (up) if (!list[position - 9].isSameColor(this) and !isInCheck(list, position - 9).second) spaces.add(Pair((position - 9), !list[position - 9].isEmpty))
        if (up and right) if (!list[position - 8].isSameColor(this) and !isInCheck(list, position - 8).second) spaces.add(Pair((position - 8), !list[position - 8].isEmpty))
        if (left) if (!list[position - 1].isSameColor(this) and !isInCheck(list, position - 1).second) spaces.add(Pair((position - 1), !list[position - 1].isEmpty))
        if (right) if (!list[position + 1].isSameColor(this) and !isInCheck(list, position + 1).second) spaces.add(Pair((position + 1), !list[position + 1].isEmpty))
        if (down and left) if (!list[position + 8].isSameColor(this) and !isInCheck(list, position + 8).second) spaces.add(Pair((position + 8), !list[position + 8].isEmpty))
        if (down) if (!list[position + 9].isSameColor(this) and !isInCheck(list, position + 9).second) spaces.add(Pair((position + 9), !list[position + 9].isEmpty))
        if (down and right) if (!list[position + 10].isSameColor(this) and !isInCheck(list, position + 10).second) spaces.add(Pair((position + 10), !list[position + 10].isEmpty))


        return spaces
    }
}