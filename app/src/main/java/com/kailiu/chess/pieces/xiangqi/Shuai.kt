package com.kailiu.chess.pieces.xiangqi

import android.graphics.drawable.Drawable
import com.kailiu.chess.pieces.Piece

class Shuai(drawable: Drawable, override var isWhite: Boolean? = false): Piece(drawable) {
    init {
        isEmpty = false
        rank = 1
    }

    override fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        if ((position - 9 in 1..26) || (position - 9 in 64..89)) if (list[position - 9].isEmpty or !list[position - 9].isSameColor(this)) {
            if (!isInCheck(list, position - 9).second) spaces.add(Pair(position - 9, !list[position - 9].isEmpty))
        }
        if ((position - 1) % 9 > 2) if (list[position - 1].isEmpty or !list[position - 1].isSameColor(this)) {
            if (!isInCheck(list, position - 1).second) spaces.add(Pair(position - 1, !list[position - 1].isEmpty))
        }
        if ((position + 1) % 9 < 6) if (list[position + 1].isEmpty or !list[position + 1].isSameColor(this)) {
            if (!isInCheck(list, position + 1).second)  spaces.add(Pair(position + 1, !list[position + 1].isEmpty))
        }
        if ((position + 9 in 1..26) || (position + 9 in 64..89)) if (list[position + 9].isEmpty or !list[position + 9].isSameColor(this)) {
            if (!isInCheck(list, position + 9).second) spaces.add(Pair(position + 9, !list[position + 9].isEmpty))
        }

        return spaces
    }

    override fun isInCheck(list: ArrayList<Piece>, position: Int): Pair<Int, Boolean> {
        var result = false
        var check = -1

        val row = position / 9

        fun iterateCardinal(delta: Int, bound: Int, direction: Int = 1){
            for (i in 1..8) {
                if (position + delta * i !in 0..89) break
                if ((direction == -1 ) and (((position + delta * i) < row * 9) or ((position + delta * i) >= (row + 1) * 9))) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!list[position + delta * i].isSameColor(this)) {
                        val rank = list[position + delta * i].rank
                        if (rank == 3) {
                            check = position + delta * i
                            result = true
                        }
                    }
                    break
                }
            }
        }

        iterateCardinal(-9, 0) // up
        iterateCardinal(-1, row, -1) // left
        iterateCardinal(1, row,-1) // right
        iterateCardinal(9, 8) // down

        fun iterateCannon(delta: Int, bound: Int, direction: Int = 1) {
            var platform = false
            for (i in 1..8) {
                if (position + delta * i !in 0..89) break
                if ((direction == -1 ) and (((position + delta * i) < row * 9) or ((position + delta * i) >= (row + 1) * 9))) break
                if (list[position + delta * i].isEmpty) {
                    continue
                } else {
                    if (!platform) {
                        platform = true
                    } else {
                        if (!list[position + delta * i].isSameColor(this)) {
                            val rank = list[position + delta * i].rank
                            if (rank == 6) {
                                check = position + delta * i
                                result = true
                            }
                        }
                        break
                    }
                }
            }
        }

        iterateCannon(-9, 0) // up
        iterateCannon(-1, row, -1) // left
        iterateCannon(1, row,-1) // right
        iterateCannon(9, 8) // down
        
        val left = (position - 1) % 9 != 8
        val leftLeft = (position - 1 * 2) % 9 < 7 && position - 1 * 2 > 0
        val right = (position + 1) % 9 != 0
        val rightRight = (position + 1 * 2) % 9 > 1
        val up = position - 9 >= 0
        val upUp = position - 9 * 2 >= 0
        val down = position + 9 < 90
        val downDown = position + 9 * 2 < 90

        if (left and upUp) if (!list[position - 9 * 2 - 1].isSameColor(this) && list[position - 9 * 2 - 1].rank == 4) {
            check = position - 9 * 2 - 1
            result = true
        }
        if (right and upUp) if (!list[position - 9 * 2 + 1].isSameColor(this) && list[position - 9 * 2 + 1].rank == 4) {
            check = position - 9 * 2 + 1
            result = true
        }
        if (leftLeft and up) if (!list[position - 9 - 1 * 2].isSameColor(this) && list[position - 9 - 1 * 2].rank == 4) {
            check = position - 9 - 1 * 2
            result = true
        }
        if (rightRight and up) if (!list[position - 9 + 1 * 2].isSameColor(this) && list[position - 9 + 1 * 2].rank == 4) {
            check = position - 9 + 1 * 2
            result = true
        }
        if (rightRight and down) if (!list[position + 9 + 1 * 2].isSameColor(this) && list[position + 9 + 1 * 2].rank == 4) {
            check = position + 9 + 1 * 2
            result = true
        }
        if (leftLeft and down) if (!list[position + 9 - 1 * 2].isSameColor(this) && list[position + 9 - 1 * 2 ].rank == 4) {
            check = position + 9 - 1 * 2
            result = true
        }
        if (right and downDown) if (!list[position + 9 * 2 - 1].isSameColor(this) && list[position + 9 * 2 - 1].rank == 4) {
            check = position + 9 * 2 - 1
            result = true
        }
        if (left and downDown) if (!list[position + 9 * 2 + 1].isSameColor(this) && list[position + 9 * 2 + 1].rank == 4) {
            check = position + 9 * 2 + 1
            result = true
        }

        fun iteratePawn(direction: Int = 1) {
            if (up) {
                if (!list[position - 9 * direction].isSameColor(this) && list[position - 9 * direction].rank == 7) {
                    check = position - 9 * direction
                    result = true
                }
            }
            if (left) {
                if (!list[position - 1 * direction].isSameColor(this) && list[position - 1 * direction].rank == 7) {
                    check = position - 1 * direction
                    result = true
                }
            }
            if (right) {
                if (!list[position + 1 * direction].isSameColor(this) && list[position + 1 * direction].rank == 7) {
                    check = position + 1 * direction
                    result = true
                }
            }
        }

        fun iterateShuai(direction: Int = 1) {
            for (i in 1..9) {
                if (position - 9 * i * direction !in 0..89) {
                    break
                }
                if (list[position - 9 * i * direction].isEmpty) {
                    continue
                } else {
                    if (direction == 1 && list[position - 9 * i * direction].isWhite == true
                        || direction == -1 && list[position - 9 * i * direction].isWhite != true)
                        break

                    if (list[position - 9 * i * direction].rank == 1) {
                        check = position - 9 * i * direction
                        result = true
                    }
                    break
                }
            }
        }

        if (isWhite != true) {
            iteratePawn(-1)
            iterateShuai(-1)
        } else {
            iteratePawn()
            iterateShuai()
        }

        return Pair(check, result)
    }
}