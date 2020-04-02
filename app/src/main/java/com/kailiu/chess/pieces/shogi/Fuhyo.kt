package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Fuhyo(isWhite: Boolean? = false): Kinsho(isWhite) {
    
    init {
        isEmpty = false
        rank = 8
        board = BoardType.SHOGI
        unpromotedName = R.string.fuhyo
        promotedName = R.string.fuhyo_promoted
        unpromotedImg = R.drawable.ic_fuhyo
        promotedImg = R.drawable.ic_fuhyo_promoted
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