package com.kailiu.chess.pieces.chess

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class King(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {

    init {
        isEmpty = false
        rank = 1
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 8 != 7
        val right = (position + 1) % 8 != 0
        val up = position - 8 >= 0
        val down = position + 8 < 64

        if (up and left) if(!list[position - 9].isSameColor(this) and !isInCheck(list, position - 9)) spaces.add(Pair((position - 9), !list[position - 9].isEmpty))
        if (up) if (!list[position - 8].isSameColor(this) and !isInCheck(list, position - 8)) spaces.add(Pair((position - 8), !list[position - 8].isEmpty))
        if (up and right) if (!list[position - 7].isSameColor(this) and !isInCheck(list, position - 7)) spaces.add(Pair((position - 7), !list[position - 7].isEmpty))
        if (left) if (!list[position - 1].isSameColor(this) and !isInCheck(list, position - 1)) spaces.add(Pair((position - 1), !list[position - 1].isEmpty))
        if (right) if (!list[position + 1].isSameColor(this) and !isInCheck(list, position + 1)) spaces.add(Pair((position + 1), !list[position + 1].isEmpty))
        if (down and left) if (!list[position + 7].isSameColor(this) and !isInCheck(list, position + 7)) spaces.add(Pair((position + 7), !list[position + 7].isEmpty))
        if (down) if (!list[position + 8].isSameColor(this) and !isInCheck(list, position + 8)) spaces.add(Pair((position + 8), !list[position + 8].isEmpty))
        if (down and right) if (!list[position + 9].isSameColor(this) and !isInCheck(list, position + 9)) spaces.add(Pair((position + 9), !list[position + 9].isEmpty))

        return spaces
    }

    fun isInCheck(list: ArrayList<Piece>, position: Int): Boolean {
        var result = false

        val row = position / 8

        fun iterateCardinal(delta: Int, bound: Int, direction: Int = 1){
            for (i in 1..7) {
                if (((position + delta * i < 0) or (position + delta * i > 63))) break
                if ((direction == -1 ) and (((position + delta * i) < row * 8) or ((position + delta * i) >= (row + 1) * 8))) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!list[position + delta * i].isSameColor(this)) {
                        val rank = list[position + delta * i].rank
                        if ((rank == 2) or (rank == 3)) {
                            println("rank: $rank")
                            result = true
                        }
                    }
                    break
                }
            }
        }

        iterateCardinal(-8, 0) // up
        iterateCardinal(-1, row, -1) // left
        iterateCardinal(1, row,-1) // right
        iterateCardinal(8, 7) // down

        fun iterateDiagonal(delta: Int, bound: Int, direction: Int = 1) {
            for (i in 1..7) {

                if ((position + delta * i < 0) or (position + delta * i > 63) or ((position + delta * i) / 8 != position / 8 + i * direction)) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!list[position + delta * i].isSameColor(this)) {
                        val rank = list[position + delta * i].rank
                        if ((rank == 2) or (rank == 5)) {
                            result = true
                        }
                    }
                    break
                }
            }
        }

        iterateDiagonal(-9, 0, -1) // NW
        iterateDiagonal(-7, row, - 1) // NE
        iterateDiagonal(7, row)  // SW
        iterateDiagonal(9, 7)  // SE

        val left = (position - 1) % 8 != 7
        val leftLeft = (position - 1 * 2) % 8 != 7
        val right = (position + 1) % 8 != 0
        val rightRight = (position + 1 * 2) % 8 != 0
        val up = position - 8 >= 0
        val upUp = position - 8 * 2 >= 0
        val down = position + 8 < 64
        val downDown = position + 8 * 2 < 64

        if (left and upUp) if (!list[position - 8 * 2 - 1].isSameColor(this) && list[position - 8 * 2 - 1].rank == 4)
        {println("rank: 4"); result = true}
        if (right and upUp) if (!list[position - 8 * 2 + 1].isSameColor(this) && list[position - 8 * 2 + 1].rank == 4)
        {println("rank: 4"); result = true}
        if (leftLeft and up) if (!list[position - 8 - 1 * 2].isSameColor(this) && list[position - 8 - 1 * 2].rank == 4)
        {println("rank: 4"); result = true}
        if (rightRight and up) if (!list[position - 8 + 1 * 2].isSameColor(this) && list[position - 8 + 1 * 2].rank == 4)
        {println("rank: 4"); result = true}
        if (rightRight and down) if (!list[position + 8 + 1 * 2].isSameColor(this) && list[position + 8 + 1 * 2].rank == 4)
        {println("rank: 4"); result = true}
        if (leftLeft and down) if (!list[position + 8 - 1 * 2].isSameColor(this) && list[position + 8 - 1 * 2 ].rank == 4)
        {println("rank: 4"); result = true}
        if (right and downDown) if (!list[position + 8 * 2 - 1].isSameColor(this) && list[position + 8 * 2 - 1].rank == 4)
        {println("rank: 4"); result = true}
        if (left and downDown) if (!list[position + 8 * 2 + 1].isSameColor(this) && list[position + 8 * 2 + 1].rank == 4)
        {println("rank: 4"); result = true}

        fun iteratePawn(direction: Int = 1) {
            if (position + 7 * direction in 1..63 && position + 9 * direction in 1..63) {
                if (!list[position + 7 * direction].isSameColor(this) && list[position + 7 * direction].rank == 6)
                {println("rank: 6"); result = true}
                if (!list[position + 9 * direction].isSameColor(this) && list[position + 9 * direction].rank == 6)
                {println("rank: 6"); result = true}
            }
        }

        iteratePawn()
        iteratePawn(-1)

        println("inCheck $position :::: $result")

        return result
    }

    override fun blockOrEat(list: ArrayList<Piece>, checkPosition: Int, thisPosition: Int, king: Int): Pair<Int, Boolean> {
        if (list[king].isSameColor(this)) {
            return Pair(-1, false)
        }

        val checkSpaces = list[checkPosition].calcMovement(list, checkPosition)
        val thisSpaces = calcMovement(list, thisPosition)

        if (binarySearch(checkSpaces, checkPosition) != -1) {
            return Pair(thisPosition, true)
        }

        for (i in thisSpaces) {
            if (binarySearch(checkSpaces, i.first) != -1) {
                return Pair(thisPosition, false)
            }
        }

        return Pair(-1, false)
    }
}