package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Bing(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 7
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 9 != 8
        val right = (position + 1) % 9 != 0

        if (isWhite == true) {
            if (position < 45) {
                if (left and !list[position - 1].isSameColor(this)) spaces.add(Pair(position - 1, !list[position - 1].isEmpty))
                if (right and !list[position + 1].isSameColor(this)) spaces.add(Pair(position + 1, !list[position + 1].isEmpty))
            }

            if (position - 9 >= 0) if (!list[position - 9].isSameColor(this))
                spaces.add(Pair(position - 9, !list[position - 9].isEmpty))
        } else {
            if (position > 44) {
                if (left and !list[position - 1].isSameColor(this)) spaces.add(Pair(position - 1, !list[position - 1].isEmpty))
                if (right and !list[position + 1].isSameColor(this)) spaces.add(Pair(position + 1, !list[position + 1].isEmpty))
            }
            if (position + 9 < 81) if (!list[position + 9].isSameColor(this))
                spaces.add(Pair(position + 9, !list[position + 9].isEmpty))
        }

        return spaces
    }
}