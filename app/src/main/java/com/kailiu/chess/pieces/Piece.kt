package com.kailiu.chess.pieces

import android.content.Context
import android.graphics.drawable.Drawable
import com.kailiu.chess.R

open class Piece(var drawable: Drawable) {
    var isEmpty = true
    open var isWhite: Boolean? = null
    var hasMoved = false
    var rank = 0
    var position = -1
    var isPromoted = false
    open val unpromotedName = 0
    open val promotedName = 0
    open val unpromotedImg = 0
    open val promotedImg = 0

    open fun calcMovement(list: ArrayList<Piece>, position: Int): ArrayList<Pair<Int, Boolean>> {
        return arrayListOf()
    }

    fun isSameColor(piece: Piece): Boolean {
        return piece.isWhite == isWhite
    }

    open fun blockOrEat(list: ArrayList<Piece>, checkPosition: Int, thisPosition: Int, king: Int): Pair<Int, Boolean> {
        return Pair(-1, false)
    }

    open fun getSpacesToKing(
        list: ArrayList<Piece>,
        thisPosition: Int,
        king: Int
    ): ArrayList<Pair<Int, Boolean>>  {
        return ArrayList()
    }

    private fun partition(arr: ArrayList<Pair<Int, Boolean>>, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1
        for (j in low until high) {
            if (arr[j].first < pivot.first) {
                i++

                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }

        val temp = arr[i + 1]
        arr[i + 1] = arr[high]
        arr[high] = temp

        return i + 1
    }

    protected fun sort(arr: ArrayList<Pair<Int, Boolean>>, low: Int, high: Int) {
        if (low < high) {
            val pi = partition(arr, low, high)

            sort(arr, low, pi - 1)
            sort(arr, pi + 1, high)
        }
    }

    fun binarySearch(arr: ArrayList<Pair<Int, Boolean>>, x: Int): Int {
        for (i in arr) {
            if (i.first == x) {
                return i.first
            }
        }

        return -1
    }


    open fun isInCheck(list: ArrayList<Piece>, position: Int): Pair<Int, Boolean> {
        return Pair(-1, false)
    }

    open fun promote(context: Context) {}
}