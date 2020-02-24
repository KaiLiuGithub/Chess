package com.kailiu.chess.pieces.chinese

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Bing(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 7
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()


        if (isWhite == true) {
            if (position < 45) {
                val left = (position - 1) % 9 != 8
                val right = (position + 1) % 9 != 0

                if (left and !list[position - 1].isSameColor(this)) spaces.add(Pair(position - 1, !list[position - 1].isEmpty))
                if (right and !list[position + 1].isSameColor(this)) spaces.add(Pair(position + 1, !list[position + 1].isEmpty))
            }

            if (position - 9 >= 0)
                spaces.add(Pair(position - 9, !list[position - 9].isEmpty))
        } else {
            if (position > 44) {
                val left = (position - 1) % 9 != 8
                val right = (position + 1) % 9 != 0

                if (left and !list[position - 1].isSameColor(this)) spaces.add(Pair(position - 1, !list[position - 1].isEmpty))
                if (right and !list[position + 1].isSameColor(this)) spaces.add(Pair(position + 1, !list[position + 1].isEmpty))
            }
            if (position + 9 < 81)
                spaces.add(Pair(position + 9, !list[position + 9].isEmpty))
        }

        return spaces
    }
}