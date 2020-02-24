package com.kailiu.chess.pieces.chinese

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Ma(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 4
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 8 != 7
        val leftLeft = (position - 1 * 2) % 8 < 6 && position - 1 * 2 > 0
        val right = (position + 1) % 8 != 0
        val rightRight = (position + 1 * 2) % 8 > 1
        val up = position - 8 >= 0
        val upUp = position - 8 * 2 >= 0
        val down = position + 8 < 64
        val downDown = position + 8 * 2 < 64

        if (left and upUp) if (!list[position - 8 * 2 - 1].isSameColor(this)) spaces.add(Pair(position - 8 * 2 - 1, !list[position - 8 * 2 - 1].isEmpty))
        if (right and upUp) if (!list[position - 8 * 2 + 1].isSameColor(this)) spaces.add(Pair(position - 8 * 2 + 1, !list[position - 8 * 2 + 1].isEmpty))
        if (leftLeft and up) if (!list[position - 8 - 1 * 2].isSameColor(this)) spaces.add(Pair(position - 8 - 1 * 2, !list[position - 8 - 1 * 2].isEmpty))
        if (rightRight and up) if (!list[position - 8 + 1 * 2].isSameColor(this)) spaces.add(Pair(position - 8 + 1 * 2, !list[position - 8 + 1 * 2].isEmpty))
        if (rightRight and down) if (!list[position + 8 + 1 * 2].isSameColor(this)) spaces.add(Pair(position + 8 + 1 * 2, !list[position + 8 + 1 * 2].isEmpty))
        if (leftLeft and down) if (!list[position + 8 - 1 * 2].isSameColor(this)) spaces.add(Pair(position + 8 - 1 * 2, !list[position + 8 - 1 * 2].isEmpty))
        if (left and downDown) if (!list[position + 8 * 2 - 1].isSameColor(this)) spaces.add(Pair(position + 8 * 2 - 1, !list[position + 8 * 2 - 1].isEmpty))
        if (right and downDown) if (!list[position + 8 * 2 + 1].isSameColor(this)) spaces.add(Pair(position + 8 * 2 + 1, !list[position + 8 * 2 + 1].isEmpty))

        return spaces
    }

    override fun getSpacesToKing(list: ArrayList<Piece>, thisPosition: Int, king: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        for (i in list[thisPosition].calcMovement(list, thisPosition)) {
            if (i.first == king) {
                spaces.add(i)
                break
            }
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