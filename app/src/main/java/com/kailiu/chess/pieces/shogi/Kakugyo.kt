package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Kakugyo(isWhite: Boolean? = false): Piece(isWhite) {

    init {
        isEmpty = false
        rank = 3
        board = BoardType.SHOGI
        unpromotedName = R.string.kakugyo
        promotedName = R.string.kakugyo_promoted
        unpromotedImg = R.drawable.ic_kakugyo
        promotedImg = R.drawable.ic_kakugyo_promoted
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 9

        spaces.addAll(iterate(list, position, -10, 0, -1)) // NW
        spaces.addAll(iterate(list, position, -8, row, -1)) // NE
        spaces.addAll(iterate(list, position, 8, row))  // SW
        spaces.addAll(iterate(list, position, 10, 7))  // SE

        if (isPromoted) {
            val direction = if (isWhite == true) 1 else -1

            val left = (position - 1) % 9 != 8
            val right = (position + 1) % 9 != 0
            val up = position - 9 * direction in 0..80
            val down = position + 9 * direction in 0..80

            if (up) if (!list[position - 9 * direction].isSameColor(this) and !isInCheck(list, position - 9 * direction).second) spaces.add(Pair((position - 9 * direction), !list[position - 9 * direction].isEmpty))
            if (left) if (!list[position - 1 * direction].isSameColor(this) and !isInCheck(list, position - 1 * direction).second) spaces.add(Pair((position - 1 * direction), !list[position - 1 * direction].isEmpty))
            if (right) if (!list[position + 1 * direction].isSameColor(this) and !isInCheck(list, position + 1 * direction).second) spaces.add(Pair((position + 1 * direction), !list[position + 1 * direction].isEmpty))
            if (down) if (!list[position + 9 * direction].isSameColor(this) and !isInCheck(list, position + 9 * direction).second) spaces.add(Pair((position + 9 * direction), !list[position + 9 * direction].isEmpty))
        }

        return spaces
    }

    fun iterate(list: ArrayList<Piece>, position: Int, delta: Int, bound: Int, direction: Int = 1): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        for (i in 1..8) {

            if ((position + delta * i < 0) or (position + delta * i > 80) or ((position + delta * i) / 9 != position / 9 + i * direction)) break
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
        val row = thisPosition / 9

        var spaces = iterate(list, thisPosition, -10, 0, -1) // NW
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, thisPosition, -8, row, -1) // NE
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, thisPosition, 8, row)  // SW
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, thisPosition, 10, 7)  // SE
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