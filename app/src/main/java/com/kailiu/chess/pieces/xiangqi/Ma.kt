package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Ma(isWhite: Boolean? = false): Piece(isWhite) {

    init {
        isEmpty = false
        rank = 4
        board = BoardType.XIANGQI
        unpromotedImg = R.drawable.ic_ma_black
        promotedImg = R.drawable.ic_ma_red
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 9 != 8
        val leftLeft = (position - 1 * 2) % 9 < 7 && position - 1 * 2 > 0
        val right = (position + 1) % 9 != 0
        val rightRight = (position + 1 * 2) % 9 > 1
        val up = position - 9 >= 0
        val upUp = position - 9 * 2 >= 0
        val down = position + 9 < 90
        val downDown = position + 9 * 2 < 90

        val upBlocked = up && !list[position - 9].isEmpty
        val leftBlocked = left && !list[position - 1].isEmpty
        val rightBlocked = right && !list[position + 1].isEmpty
        val downBlocked = down && !list[position + 9].isEmpty

        if (!upBlocked) {
            if (left and upUp) if (!list[position - 9 * 2 - 1].isSameColor(this)) spaces.add(
                Pair(
                    position - 9 * 2 - 1,
                    !list[position - 9 * 2 - 1].isEmpty
                )
            )
            if (right and upUp) if (!list[position - 9 * 2 + 1].isSameColor(this)) spaces.add(
                Pair(
                    position - 9 * 2 + 1,
                    !list[position - 9 * 2 + 1].isEmpty
                )
            )
        }
        if (!leftBlocked) {
            if (leftLeft and up) if (!list[position - 9 - 1 * 2].isSameColor(this)) spaces.add(
                Pair(
                    position - 9 - 1 * 2,
                    !list[position - 9 - 1 * 2].isEmpty
                )
            )
            if (leftLeft and down) if (!list[position + 9 - 1 * 2].isSameColor(this)) spaces.add(
                Pair(position + 9 - 1 * 2, !list[position + 9 - 1 * 2].isEmpty)
            )
        }

        if (!rightBlocked) {

            if (rightRight and up) if (!list[position - 9 + 1 * 2].isSameColor(this)) spaces.add(
                Pair(position - 9 + 1 * 2, !list[position - 9 + 1 * 2].isEmpty)
            )
            if (rightRight and down) if (!list[position + 9 + 1 * 2].isSameColor(this)) spaces.add(
                Pair(position + 9 + 1 * 2, !list[position + 9 + 1 * 2].isEmpty)
            )
        }

        if (!downBlocked) {
            if (left and downDown) if (!list[position + 9 * 2 - 1].isSameColor(this)) spaces.add(
                Pair(position + 9 * 2 - 1, !list[position + 9 * 2 - 1].isEmpty)
            )
            if (right and downDown) if (!list[position + 9 * 2 + 1].isSameColor(this)) spaces.add(
                Pair(position + 9 * 2 + 1, !list[position + 9 * 2 + 1].isEmpty)
            )
        }

        sort(spaces, 0, spaces.size - 1)

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