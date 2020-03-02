package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece

class Kyosha(drawable: Drawable, override var isWhite: Boolean? = false): Kinsho(drawable, isWhite){
    override val unpromotedName = R.string.kyosha
    override val promotedName = R.string.kyosha_promoted
    override val unpromotedImg = R.drawable.ic_kyosha
    override val promotedImg = R.drawable.ic_kyosha_promoted

    init {
        isEmpty = false
        rank = 7
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