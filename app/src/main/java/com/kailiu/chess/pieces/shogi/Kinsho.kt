package com.kailiu.chess.pieces.shogi

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

open class Kinsho(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 4
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()
        
        val direction = if (isWhite == true) 1 else -1

        val left = if (direction == 1) (position - 1) % 9 != 8 else (position + 1) % 9 != 0
        val right = if (direction == 1) (position + 1) % 9 != 0 else (position - 1) % 9 != 8
        val up = position - 9 * direction in 0..80
        val down = position + 9 * direction in 0..80

        println("${position - 1 * direction} || ${(position - 1 * direction) % 9} || $left")
        println("${position + 1 * direction} || ${(position + 1 * direction) % 9} || $right")
        println("${position - 9 * direction} || $up")
        println("${position + 9 * direction} || $down")

        if (up and left) if(!list[position - 10 * direction].isSameColor(this) and !isInCheck(list, position - 10 * direction).second) spaces.add(Pair((position - 10 * direction), !list[position - 10 * direction].isEmpty))
        if (up) if (!list[position - 9 * direction].isSameColor(this) and !isInCheck(list, position - 9 * direction).second) spaces.add(Pair((position - 9 * direction), !list[position - 9 * direction].isEmpty))
        if (up and right) if (!list[position - 8 * direction].isSameColor(this) and !isInCheck(list, position - 8 * direction).second) spaces.add(Pair((position - 8 * direction), !list[position - 8 * direction].isEmpty))
        if (left) if (!list[position - 1 * direction].isSameColor(this) and !isInCheck(list, position - 1 * direction).second) spaces.add(Pair((position - 1 * direction), !list[position - 1 * direction].isEmpty))
        if (right) if (!list[position + 1 * direction].isSameColor(this) and !isInCheck(list, position + 1 * direction).second) spaces.add(Pair((position + 1 * direction), !list[position + 1 * direction].isEmpty))
        if (down) if (!list[position + 9 * direction].isSameColor(this) and !isInCheck(list, position + 9 * direction).second) spaces.add(Pair((position + 9 * direction), !list[position + 9 * direction].isEmpty))

        return spaces
    }
}