package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece

class Keima(drawable: Drawable, override var isWhite: Boolean? = false): Kinsho(drawable, isWhite) {
    override val unpromotedName = R.string.keima
    override val promotedName = R.string.keima_promoted
    override val unpromotedImg = R.drawable.ic_keima
    override val promotedImg = R.drawable.ic_keima_promoted
    
    init {
        isEmpty = false
        rank = 6
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