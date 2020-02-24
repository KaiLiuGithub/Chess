package com.kailiu.chess.pieces.chess

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Bishop(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 5
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 8

        spaces.addAll(iterate(list, position, -9, 0, -1)) // NW
        spaces.addAll(iterate(list, position, -7, row, -1)) // NE
        spaces.addAll(iterate(list, position, 7, row))  // SW
        spaces.addAll(iterate(list, position, 9, 7))  // SE

        return spaces
    }

    fun iterate(list: ArrayList<Piece>, position: Int, delta: Int, bound: Int, direction: Int = 1): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 8

        for (i in 1..7) {

            if ((position + delta * i < 0) or (position + delta * i > 63) or ((position + delta * i) / 8 != position / 8 + i * direction)) break
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
        val row = thisPosition / 8

        var spaces = iterate(list, position, -9, 0, -1) // NW
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, position, -7, row, -1) // NE
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, position, 7, row)  // SW
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        spaces = iterate(list, position, 9, 7)  // SE
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