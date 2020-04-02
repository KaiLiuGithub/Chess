package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class Kyosha(isWhite: Boolean? = false): Kinsho(isWhite){

    init {
        isEmpty = false
        rank = 7
        board = BoardType.SHOGI
        unpromotedName = R.string.kyosha
        promotedName = R.string.kyosha_promoted
        unpromotedImg = R.drawable.ic_kyosha
        promotedImg = R.drawable.ic_kyosha_promoted
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        if (isPromoted) return super.calcMovement(list, position)

        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val direction = if (isWhite == true) 1 else -1

        val row = position / 9

        for (i in 1..9) {

            println("pos: ${position - 9 * i * direction}")
            if (((position - 9 * i * direction < 0) or (position - 9 * i * direction > 80))) break
            //if ((direction == -1 ) and (((position - 9 * i * direction) < row * 9) or ((position - 9 * i * direction) >= (row + 1) * 9))) break
            if (list[position - 9 * i * direction].isEmpty) {
                spaces.add(Pair(position - 9 * i * direction, false))
            } else {
                if (!list[position - 9 * i * direction].isSameColor(this)) {
                    spaces.add(Pair(position - 9 * i * direction, true))
                }
                break
            }
        }

        return spaces
    }
}