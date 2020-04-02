package com.kailiu.chess.pieces

import android.content.Context
import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardType

open class Piece(@Expose var isWhite: Boolean? = null) {
    var drawable: Drawable? = null
    @Expose
    var isEmpty = true
    @Expose
    var hasMoved = false
    @Expose
    var rank = 0
    @Expose
    var position = -1
    @Expose
    var isPromoted = false
    @Expose
    var board = BoardType.CHESS
    @Expose
    var unpromotedName = 0
    @Expose
    var promotedName = 0
    @Expose
    var unpromotedImg = R.drawable.ic_transparent
    @Expose
    var promotedImg = R.drawable.ic_transparent

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

    fun setDrawable(context: Context, isCaptured: Boolean = false) {
        var b = if (board == BoardType.SHOGI) isPromoted else isWhite
        
        if (isCaptured) { b = b != true }

        drawable = context.resources.getDrawable(
            if (b == true) {
                promotedImg
            } else {
                unpromotedImg
            }, context.theme
        )
    }
}