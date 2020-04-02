package com.kailiu.chess.pieces.chess

import android.graphics.drawable.Drawable
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece

class King(isWhite: Boolean? = false): Piece(isWhite) {

    init {
        isEmpty = false
        rank = 1
        board = BoardType.CHESS
        unpromotedImg = R.drawable.ic_king_black
        promotedImg = R.drawable.ic_king_white
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        val left = (position - 1) % 8 != 7
        val right = (position + 1) % 8 != 0
        val up = position - 8 >= 0
        val down = position + 8 < 64

        if (up and left) if(!list[position - 9].isSameColor(this) and !isInCheck(list, position - 9).second) spaces.add(Pair((position - 9), !list[position - 9].isEmpty))
        if (up) if (!list[position - 8].isSameColor(this) and !isInCheck(list, position - 8).second) spaces.add(Pair((position - 8), !list[position - 8].isEmpty))
        if (up and right) if (!list[position - 7].isSameColor(this) and !isInCheck(list, position - 7).second) spaces.add(Pair((position - 7), !list[position - 7].isEmpty))
        if (left) if (!list[position - 1].isSameColor(this) and !isInCheck(list, position - 1).second) spaces.add(Pair((position - 1), !list[position - 1].isEmpty))
        if (right) if (!list[position + 1].isSameColor(this) and !isInCheck(list, position + 1).second) spaces.add(Pair((position + 1), !list[position + 1].isEmpty))
        if (down and left) if (!list[position + 7].isSameColor(this) and !isInCheck(list, position + 7).second) spaces.add(Pair((position + 7), !list[position + 7].isEmpty))
        if (down) if (!list[position + 8].isSameColor(this) and !isInCheck(list, position + 8).second) spaces.add(Pair((position + 8), !list[position + 8].isEmpty))
        if (down and right) if (!list[position + 9].isSameColor(this) and !isInCheck(list, position + 9).second) spaces.add(Pair((position + 9), !list[position + 9].isEmpty))

        spaces.addAll(checkCastling(list))

        return spaces
    }

    override fun isInCheck(list: ArrayList<Piece>, position: Int): Pair<Int, Boolean> {
        var result = false
        var check = -1

        val row = position / 8

        fun iterateCardinal(delta: Int, bound: Int, direction: Int = 1) {
            for (i in 1..7) {
                if (((position + delta * i < 0) or (position + delta * i > 63))) break
                if ((direction == -1 ) and (((position + delta * i) < row * 8) or ((position + delta * i) >= (row + 1) * 8))) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!list[position + delta * i].isSameColor(this)) {
                        val rank = list[position + delta * i].rank
                        if ((rank == 2) or (rank == 3)) {
                            check = position + delta * i
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
        println("1! $position: $result")

        fun iterateDiagonal(delta: Int, bound: Int, direction: Int = 1) {
            for (i in 1..7) {

                if ((position + delta * i < 0) or (position + delta * i > 63) or ((position + delta * i) / 8 != position / 8 + i * direction)) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!list[position + delta * i].isSameColor(this)) {
                        val rank = list[position + delta * i].rank
                        if ((rank == 2) or (rank == 5)) {
                            check = position + delta * i
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
        println("2! $position: $result")

        val left = (position - 1) % 8 != 7
        val leftLeft = (position - 1 * 2) % 8 != 7
        val right = (position + 1) % 8 != 0
        val rightRight = (position + 1 * 2) % 8 != 0
        val up = position - 8 >= 0
        val upUp = position - 8 * 2 >= 0
        val down = position + 8 < 64
        val downDown = position + 8 * 2 < 64

        if (left and upUp) if (!list[position - 8 * 2 - 1].isSameColor(this) && list[position - 8 * 2 - 1].rank == 4) {
            check = position - 8 * 2 - 1
            result = true
        }
        if (right and upUp) if (!list[position - 8 * 2 + 1].isSameColor(this) && list[position - 8 * 2 + 1].rank == 4) {
            check = position - 8 * 2 + 1
            result = true
        }
        if (leftLeft and up) if (!list[position - 8 - 1 * 2].isSameColor(this) && list[position - 8 - 1 * 2].rank == 4) {
            check = position - 8 - 1 * 2
            result = true
        }
        if (rightRight and up) if (!list[position - 8 + 1 * 2].isSameColor(this) && list[position - 8 + 1 * 2].rank == 4) {
            check = position - 8 + 1 * 2
            result = true
        }
        if (rightRight and down) if (!list[position + 8 + 1 * 2].isSameColor(this) && list[position + 8 + 1 * 2].rank == 4) {
            check = position + 8 + 1 * 2
            result = true
        }
        if (leftLeft and down) if (!list[position + 8 - 1 * 2].isSameColor(this) && list[position + 8 - 1 * 2 ].rank == 4) {
            check = position + 8 - 1 * 2
            result = true
        }
        if (right and downDown) if (!list[position + 8 * 2 + 1].isSameColor(this) && list[position + 8 * 2 + 1].rank == 4) {
            check = position + 8 * 2 + 1
            result = true
        }
        if (left and downDown) if (!list[position + 8 * 2 - 1].isSameColor(this) && list[position + 8 * 2 - 1].rank == 4) {
            check = position + 8 * 2 - 1
            result = true
        }
        println("3! $position: $result")

        fun iteratePawn(direction: Int = 1) {
            if (position + 7 * direction in 1..63) {
                if (!list[position + 7 * direction].isSameColor(this) && list[position + 7 * direction].rank == 6) {
                    check = position + 7 * direction
                    result = true
                }
            }
            if (position + 9 * direction in 1..63) {
                if (!list[position + 9 * direction].isSameColor(this) && list[position + 9 * direction].rank == 6) {
                    check = position + 9 * direction
                    result = true
                }
            }
        }

        if (isWhite == true) iteratePawn(-1) else iteratePawn()

        println("4! $position: $result")
        return Pair(check, result)
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

    fun checkCastling(list: ArrayList<Piece>): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()
        if (isWhite != true) {
            if (list[0].rank == 3 && !list[0].hasMoved
                && list[1].isEmpty && !list[1].isInCheck(list, 1).second
                && list[2].isEmpty && !list[2].isInCheck(list, 1).second
            ) {
                spaces.add(Pair(1, false))
            }
            if (list[7].rank == 3 && !list[7].hasMoved
                && list[6].isEmpty && !list[6].isInCheck(list, 6).second
                && list[5].isEmpty && !list[5].isInCheck(list, 5).second
                && list[4].isEmpty && !list[4].isInCheck(list, 4).second
            ) {
                spaces.add(Pair(6, false))
            }
        } else {

            if (list[56].rank == 3 && !list[56].hasMoved
                && list[57].isEmpty && !list[57].isInCheck(list, 57).second
                && list[58].isEmpty && !list[58].isInCheck(list, 58).second
            ) {
                spaces.add(Pair(57, false))
            }
            if (list[62].rank == 3 && !list[62].hasMoved
                && list[61].isEmpty && !list[61].isInCheck(list, 61).second
                && list[60].isEmpty && !list[60].isInCheck(list, 60).second
                && list[59].isEmpty && !list[59].isInCheck(list, 59).second
            ) {
                spaces.add(Pair(61, false))
            }
        }

        return spaces
    }
}