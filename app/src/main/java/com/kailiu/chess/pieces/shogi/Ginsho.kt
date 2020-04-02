package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Ginsho (isWhite: Boolean? = false): Kinsho(isWhite) {
    
    init {
        isEmpty = false
        rank = 5
        board = BoardType.SHOGI
        unpromotedName = R.string.ginsho
        promotedName = R.string.ginsho_promoted
        unpromotedImg = R.drawable.ic_ginsho
        promotedImg = R.drawable.ic_ginsho_promoted
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        if (isPromoted) return super.calcMovement(list, position)

        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val direction = if (isWhite == true) 1 else -1

        val left = (position - 1 * direction) % 9 != 8
        val right = (position + 1 * direction) % 9 != 0
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