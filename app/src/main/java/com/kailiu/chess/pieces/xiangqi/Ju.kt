package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Ju(isWhite: Boolean? = false): Piece(isWhite) {
    init {
        isEmpty = false
        rank = 3
        board = BoardType.XIANGQI
        unpromotedImg = R.drawable.ic_ju_black
        promotedImg = R.drawable.ic_ju_red
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 8

        spaces.addAll(iterate(list, position,-9, 0)) // up
        spaces.addAll(iterate(list, position, -1, row, -1)) // left
        spaces.addAll(iterate(list, position,1, row, -1)) // right
        spaces.addAll(iterate(list, position, 9, 7)) // down=

        return spaces
    }


    fun iterate(list: ArrayList<Piece>, position: Int, delta: Int, bound: Int, direction: Int = 1): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 9

        for (i in 1..8) {
            if (((position + delta * i < 0) or (position + delta * i > 89))) break
            if ((direction == -1 ) and (((position + delta * i) < row * 9) or ((position + delta * i) >= (row + 1) * 9))) break
            if (list[position + delta * i].isEmpty) {
                spaces.add(Pair(position + delta * i, false))
            } else {
                if (!list[position + delta * i].isSameColor(this)) {
                    spaces.add(Pair(position + delta * i, true))
                }
                break
            }
        }

        return spaces
    }

    override fun getSpacesToKing(list: ArrayList<Piece>, thisPosition: Int, king: Int): ArrayList<Pair<Int, Boolean>>  {
        var spaces = iterate(list, thisPosition,-9, 0)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,-1, -1)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,1, -1)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,9, 7)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        return ArrayList()
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