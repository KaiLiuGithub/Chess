package com.kailiu.chess.pieces.shogi

import android.content.Context
import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece

class Hisha(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    override val unpromotedName = R.string.hisha
    override val promotedName = R.string.hisha_promoted
    override val unpromotedImg = R.drawable.ic_hisha
    override val promotedImg = R.drawable.ic_hisha_promoted

    init {
        isEmpty = false
        rank = 2
    }


    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        spaces.addAll(iterate(list, position,-9)) // up
        spaces.addAll(iterate(list, position, -1, -1)) // left
        spaces.addAll(iterate(list, position,1, -1)) // right
        spaces.addAll(iterate(list, position, 9)) // down

        if (isPromoted) {
            val direction = if (isWhite == true) 1 else -1

            val left = (position - 1) % 9 != 8
            val right = (position + 1) % 9 != 0
            val up = position - 9 * direction in 0..80
            val down = position + 9 * direction in 0..80

            if (up and left) if(!list[position - 10 * direction].isSameColor(this) and !isInCheck(list, position - 10 * direction).second) spaces.add(Pair((position - 10 * direction), !list[position - 10 * direction].isEmpty))
            if (up and right) if (!list[position - 8 * direction].isSameColor(this) and !isInCheck(list, position - 8 * direction).second) spaces.add(Pair((position - 8 * direction), !list[position - 8 * direction].isEmpty))
            if (down and left) if (!list[position + 8 * direction].isSameColor(this) and !isInCheck(list, position + 8 * direction).second) spaces.add(Pair((position + 8 * direction), !list[position + 8 * direction].isEmpty))
            if (down and right) if (!list[position + 10 * direction].isSameColor(this) and !isInCheck(list, position + 10 * direction).second) spaces.add(Pair((position + 10 * direction), !list[position + 10 * direction].isEmpty))
        }

        return spaces
    }


    fun iterate(list: ArrayList<Piece>, position: Int, delta: Int, direction: Int = 1): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val row = position / 9

        for (i in 1..9) {
            if (((position + delta * i < 0) or (position + delta * i > 80))) break
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

    override fun getSpacesToKing(list: ArrayList<Piece>, thisPosition: Int, king: Int): ArrayList<Pair<Int, Boolean>>  {
        var spaces = iterate(list, thisPosition,-9)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,-1, -1)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,1, -1)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }
        spaces = iterate(list, thisPosition,9)
        if (spaces.contains(Pair(king, true))) {
            return spaces
        }

        return ArrayList()
    }

    override fun blockOrEat(list: ArrayList<Piece>, checkPosition: Int, thisPosition: Int, king: Int): Pair<Int, Boolean> {
        if (!list[thisPosition].isSameColor(list[king])) {
            return Pair(-1, false)
        }

        val checkSpaces = list[checkPosition].getSpacesToKing(list, checkPosition, king)
        checkSpaces.add(Pair(checkPosition, true))

        val thisSpaces = calcMovement(list, thisPosition)

        if (binarySearch(checkSpaces, thisPosition) != -1) {
            return Pair(thisPosition, true)
        }

        for (i in thisSpaces) {
            if (binarySearch(checkSpaces, i.first) != -1) {
                return Pair(thisPosition, false)
            }
        }

        return Pair(-1, false)
    }

    override fun promote(context: Context) {
        drawable = context.resources.getDrawable(R.drawable.ic_hisha_promoted, context.theme)
        isPromoted = true
    }
}