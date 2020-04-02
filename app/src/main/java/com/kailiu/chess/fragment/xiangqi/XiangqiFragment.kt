package com.kailiu.chess.fragment.xiangqi

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardFragment
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.xiangqi.*
import kotlinx.android.synthetic.main.fragment_board.*

class XiangqiFragment: BoardFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayout.columnCount = 9
        gridLayout.rowCount = 10
        gridLayout.background = resources.getDrawable(R.drawable.ic_xiangqi_board, activity?.theme)

        gridLayout.apply {
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                .dimensionRatio = "9:10"
        }

        resetBtn.setOnClickListener {
            initializeBoard()
            resetLayout.visibility = View.GONE
        }

        initializeBoard()
    }

    fun initializeBoard() {

        pieceArray.clear()
        whiteCapture.clear()
        blackCapture.clear()

        clearGameIcons()
        
        pieceArray.add(Ju())
        pieceArray.add(Ma())
        pieceArray.add(Xiang())
        pieceArray.add(Shi())
        pieceArray.add(Shuai())
        pieceArray.add(Shi())
        pieceArray.add(Xiang())
        pieceArray.add(Ma())
        pieceArray.add(Ju())

        for (i in 0..9) {
            pieceArray.add(Piece())
        }
        
        pieceArray.add(Pao())
        for (i in 0..4) {
            pieceArray.add(Piece())
        }
        pieceArray.add(Pao())
        pieceArray.add(Piece())

        pieceArray.add(Bing())
        pieceArray.add(Piece())
        pieceArray.add(Bing())
        pieceArray.add(Piece())
        pieceArray.add(Bing())
        pieceArray.add(Piece())
        pieceArray.add(Bing())
        pieceArray.add(Piece())
        pieceArray.add(Bing())

        for (i in 0..17) {
            pieceArray.add(Piece())
        }

        pieceArray.add(Bing(true))
        pieceArray.add(Piece())
        pieceArray.add(Bing(true))
        pieceArray.add(Piece())
        pieceArray.add(Bing(true))
        pieceArray.add(Piece())
        pieceArray.add(Bing(true))
        pieceArray.add(Piece())
        pieceArray.add(Bing(true))

        pieceArray.add(Piece())
        pieceArray.add(Pao(true))
        for (i in 0..4) {
            pieceArray.add(Piece())
        }
        pieceArray.add(Pao(true))

        for (i in 0..9) {
            pieceArray.add(Piece())
        }
        
        pieceArray.add(Ju(true))
        pieceArray.add(Ma(true))
        pieceArray.add(Xiang(true))
        pieceArray.add(Shi(true))
        pieceArray.add(Shuai(true))
        pieceArray.add(Shi(true))
        pieceArray.add(Xiang(true))
        pieceArray.add(Ma(true))
        pieceArray.add(Ju(true))

        initializeListeners(gridLayout, BoardType.XIANGQI)
    }
}