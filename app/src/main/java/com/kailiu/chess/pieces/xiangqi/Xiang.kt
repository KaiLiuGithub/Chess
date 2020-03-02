package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Xiang(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 5
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 9 != 8
        val leftLeft = (position - 1 * 2) % 9 < 7 && position - 1 * 2 > 0
        val right = (position + 1) % 9 != 0
        val rightRight = (position + 1 * 2) % 9 > 2
        val up = position - 9 >= 0
        val upUp = position - 9 * 2 >= 0
        val down = position + 9 < 90
        val downDown = position + 9 * 2 < 90

        val nwBlocked = up && left && !list[position - 9 - 1].isEmpty
        val neBlocked = up && right && !list[position - 9 + 1].isEmpty
        val swBlocked = down && left && !list[position + 9 - 1].isEmpty
        val seBlocked = down && right && !list[position + 9 + 1].isEmpty

        var check = (isWhite == true) and (position / 9 >= 6)

        if (check or (isWhite != true)) {
            if (!nwBlocked) {
                if (leftLeft and upUp) if (!list[position - 9 * 2 - 1 * 2].isSameColor(this)) spaces.add(
                    Pair(position - 9 * 2 - 1 * 2, !list[position - 9 * 2 - 1 * 2].isEmpty)
                )
            }
            if (!neBlocked) {
                if (rightRight and upUp) if (!list[position - 9 * 2 + 1 * 2].isSameColor(this)) spaces.add(
                    Pair(position - 9 * 2 + 1 * 2, !list[position - 9 * 2 + 1 * 2].isEmpty)
                )
            }
        }

        check = (isWhite != true) and (position / 9 <= 5)

        if (check or (isWhite == true)) {
            if (!swBlocked) {
                if (leftLeft and downDown) if (!list[position + 9 * 2 - 1 * 2].isSameColor(this)) spaces.add(
                    Pair(position + 9 * 2 - 1 * 2, !list[position + 9 * 2 - 1 * 2].isEmpty)
                )
            }

            if (!seBlocked) {
                if (rightRight and downDown) if (!list[position + 9 * 2 + 1 * 2].isSameColor(this)) spaces.add(
                    Pair(position + 9 * 2 + 1 * 2, !list[position + 9 * 2 + 1 * 2].isEmpty)
                )
            }
        }

        sort(spaces, 0, spaces.size - 1)

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