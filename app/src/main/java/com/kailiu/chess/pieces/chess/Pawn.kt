package com.kailiu.chess.pieces.chess

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Pawn(isWhite: Boolean? = false): Piece(isWhite) {
    
    init {
        isEmpty = false
        rank = 6
        board = BoardType.CHESS
        unpromotedImg = R.drawable.ic_pawn_black
        promotedImg = R.drawable.ic_pawn_white
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        if (isWhite == true) {
            val left = (position - 1) % 8 != 7
            val right = (position + 1) % 8 != 0
            val up = position - 8 >= 0

            if ((position > 47) and (position < 56)) {
                if (list[position - 16].isEmpty) spaces.add(Pair(position - 16, false))
            }

            if (up and left) if (!list[position - 9].isEmpty and !list[position - 9].isSameColor(this)) spaces.add(Pair(position - 9, true))
            if (up) if (list[position - 8].isEmpty) spaces.add(Pair(position - 8, false))
            if (up and right) if (!list[position - 7].isEmpty and !list[position - 7].isSameColor(this)) spaces.add(Pair(position - 7, true))
        } else {
            val left = (position - 1) % 8 != 7
            val right = (position + 1) % 8 != 0
            val down = position + 8 < 64

            if ((position > 7) and (position < 16)) {
                if (list[position + 16].isEmpty) spaces.add(Pair(position + 16, false))
            }

            if (down and left) if (!list[position + 7].isEmpty and !list[position + 7].isSameColor(this)) spaces.add(Pair(position + 7, true))
            if (down) if (list[position + 8].isEmpty) spaces.add(Pair(position + 8, false))
            if (down and right) if (!list[position + 9].isEmpty and !list[position + 9].isSameColor(this)) spaces.add(Pair(position + 9, true))
        }

        return spaces
    }

    override fun blockOrEat(list: ArrayList<Piece>, checkPosition: Int, thisPosition: Int, king: Int): Pair<Int, Boolean> {
        if (!list[thisPosition].isSameColor(list[king])) {
            return Pair(-1, false)
        }

        val checkSpaces = list[checkPosition].getSpacesToKing(list, checkPosition, king)
        checkSpaces.add(Pair(checkPosition, true))

        val thisSpaces = calcMovement(list, thisPosition)

        if (binarySearch(checkSpaces, thisPosition) != -1) {
            return Pair(thisPosition, true)
        }

        for (i in thisSpaces) {
            if (binarySearch(checkSpaces, i.first) != -1) {
                return Pair(thisPosition, false)
            }
        }

        return Pair(-1, false)
    }
}