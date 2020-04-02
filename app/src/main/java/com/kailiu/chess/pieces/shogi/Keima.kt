package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Keima(isWhite: Boolean? = false): Kinsho(isWhite) {
    
    init {
        isEmpty = false
        rank = 6
        board = BoardType.SHOGI
        unpromotedName = R.string.keima
        promotedName = R.string.keima_promoted
        unpromotedImg = R.drawable.ic_keima
        promotedImg = R.drawable.ic_keima_promoted
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        if (isPromoted) return super.calcMovement(list, position)

        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val direction = if (isWhite == true) 1 else -1

        val left = (position - 1) % 9 != 8
        val right = (position + 1) % 9 != 0
        val upUp = position - 9 * 2 * direction >= 0

        if (left and upUp) if (!list[position - 9 * 2 * direction - 1].isSameColor(this)) spaces.add(Pair(position - 9 * 2 * direction  - 1, !list[position - 9 * 2 * direction  - 1].isEmpty))
        if (right and upUp) if (!list[position - 9 * 2 * direction + 1].isSameColor(this)) spaces.add(Pair(position - 9 * 2 * direction  + 1, !list[position - 9 * 2 * direction  + 1].isEmpty))

        return spaces
    }
}