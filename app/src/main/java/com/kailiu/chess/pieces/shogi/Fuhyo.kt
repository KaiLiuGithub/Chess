package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece

class Fuhyo(drawable: Drawable, override var isWhite: Boolean? = false): Kinsho(drawable, isWhite) {
    override val unpromotedName = R.string.fuhyo
    override val promotedName = R.string.fuhyo_promoted
    override val unpromotedImg = R.drawable.ic_fuhyo
    override val promotedImg = R.drawable.ic_fuhyo_promoted
    
    init {
        isEmpty = false
        rank = 8
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        if (isPromoted) return super.calcMovement(list, position)

        val spaces = arrayListOf<Pair<Int, Boolean>>()

        if (list[position].isWhite == true) {
            if (position - 9 > 0 && (list[position - 9].isEmpty || !list[position - 9].isSameColor(this))) {
                spaces.add(Pair(position - 9, !list[position - 9].isEmpty))
            }
        } else {
            if (position + 9 > 0 && (list[position + 9].isEmpty || !list[position + 9].isSameColor(this))) {
                spaces.add(Pair(position + 9, !list[position + 9].isEmpty))
            }
        }
        
        return spaces
    }
}