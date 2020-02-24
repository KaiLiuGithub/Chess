package com.kailiu.chess.pieces.chinese

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Ju(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 3
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
}