package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Shi(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 2
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val center = if (isWhite != true) 13 else 76

        if (position != center) {
            spaces.add(Pair(center, !(list[center].isEmpty || list[center].isSameColor(this))))
        } else {
            if (list[center - 10].isEmpty || !list[center - 10].isSameColor(this)) spaces.add(Pair(center - 10, !(list[center - 10].isEmpty)))
            if (list[center - 8].isEmpty || !list[center - 8].isSameColor(this)) spaces.add(Pair(center - 8, !(list[center - 8].isEmpty)))
            if (list[center + 8].isEmpty || !list[center + 8].isSameColor(this)) spaces.add(Pair(center + 8, !(list[center + 8].isEmpty)))
            if (list[center + 10].isEmpty || !list[center + 10].isSameColor(this)) spaces.add(Pair(center + 10, !(list[center + 10].isEmpty)))
        }

        return spaces
    }

    override fun blockOrEat(list: ArrayList<Piece>, checkPosition: Int, thisPosition: Int, king: Int): Pair<Int, Boolean> {
        if (!list[thisPosition].isSameColor(list[king])) {
            return Pair(-1, false)
        }

        val checkSpaces = list[checkPosition].getSpacesToKing(list, checkPosition, king)

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