package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece

class Ginsho (drawable: Drawable, override var isWhite: Boolean? = false): Kinsho(drawable, isWhite) {
    override val unpromotedName = R.string.ginsho
    override val promotedName = R.string.ginsho_promoted
    override val unpromotedImg = R.drawable.ic_ginsho
    override val promotedImg = R.drawable.ic_ginsho_promoted
    
    init {
        isEmpty = false
        rank = 5
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        if (isPromoted) return super.calcMovement(list, position)

        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val direction = if (isWhite == true) 1 else -1

        val left = (position - 1) % 9 != 8
        val right = (position + 1) % 9 != 0
        val up = position - 9 * direction in 0..80
        val down = position + 9 * direction in 0..80

        if (up and left) if(!list[position - 10 * direction].isSameColor(this) and !isInCheck(list, position - 10 * direction).second) spaces.add(Pair((position - 10 * direction), !list[position - 10 * direction].isEmpty))
        if (up) if (!list[position - 9 * direction].isSameColor(this) and !isInCheck(list, position - 9 * direction).second) spaces.add(Pair((position - 9 * direction), !list[position - 9 * direction].isEmpty))
        if (up and right) if (!list[position - 8 * direction].isSameColor(this) and !isInCheck(list, position - 8 * direction).second) spaces.add(Pair((position - 8 * direction), !list[position - 8 * direction].isEmpty))
        if (down and left) if (!list[position + 8 * direction].isSameColor(this) and !isInCheck(list, position + 8 * direction).second) spaces.add(Pair((position + 8 * direction), !list[position + 8 * direction].isEmpty))
        if (down and right) if (!list[position + 10 * direction].isSameColor(this) and !isInCheck(list, position + 10 * direction).second) spaces.add(Pair((position + 10 * direction), !list[position + 10 * direction].isEmpty))

        return spaces
    }
}