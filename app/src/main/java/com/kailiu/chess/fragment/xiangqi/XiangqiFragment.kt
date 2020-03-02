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
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0)
                .dimensionRatio = "9:10"
        }

        resetBtn.setOnClickListener {
            initializeBoard()
            resetLayout.visibility = View.GONE
        }

        initializeBoard()
    }

    fun initializeBoard() {
        pieceArray.add(
            Ju(
                resources.getDrawable(
                    R.drawable.ic_ju_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Ma(
                resources.getDrawable(
                    R.drawable.ic_ma_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Xiang(
                resources.getDrawable(
                    R.drawable.ic_xiang_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Shi(
                resources.getDrawable(
                    R.drawable.ic_shi_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Shuai(
                resources.getDrawable(
                    R.drawable.ic_shuai_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Shi(
                resources.getDrawable(
                    R.drawable.ic_shi_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Xiang(
                resources.getDrawable(
                    R.drawable.ic_xiang_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Ma(
                resources.getDrawable(
                    R.drawable.ic_ma_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Ju(
                resources.getDrawable(
                    R.drawable.ic_ju_black,
                    activity?.theme
                )
            )
        )

        for (i in 0..9) {
            pieceArray.add(
                Piece(
                    resources.getDrawable(
                        R.drawable.ic_transparent,
                        activity?.theme
                    )
                )
            )
        }

        pieceArray.add(
            Pao(
                resources.getDrawable(
                    R.drawable.ic_pao_black,
                    activity?.theme
                )
            )
        )

        for (i in 0..4) {
            pieceArray.add(
                Piece(
                    resources.getDrawable(
                        R.drawable.ic_transparent,
                        activity?.theme
                    )
                )
            )
        }

        pieceArray.add(
            Pao(
                resources.getDrawable(
                    R.drawable.ic_pao_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )

        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_black,
                    activity?.theme
                )
            )
        )

        for (i in 0..17) {
            pieceArray.add(
                Piece(
                    resources.getDrawable(
                        R.drawable.ic_transparent,
                        activity?.theme
                    )
                )
            )
        }

        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bing(
                resources.getDrawable(
                    R.drawable.ic_bing_red,
                    activity?.theme
                ), true
            )
        )

        pieceArray.add(
            Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        )

        pieceArray.add(
            Pao(
                resources.getDrawable(
                    R.drawable.ic_pao_red,
                    activity?.theme
                ), true
            )
        )

        for (i in 0..4) {
            pieceArray.add(
                Piece(
                    resources.getDrawable(
                        R.drawable.ic_transparent,
                        activity?.theme
                    )
                )
            )
        }

        pieceArray.add(
            Pao(
                resources.getDrawable(
                    R.drawable.ic_pao_red,
                    activity?.theme
                ), true
            )
        )

        for (i in 0..9) {
            pieceArray.add(
                Piece(
                    resources.getDrawable(
                        R.drawable.ic_transparent,
                        activity?.theme
                    )
                )
            )
        }

        pieceArray.add(
            Ju(
                resources.getDrawable(
                    R.drawable.ic_ju_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Ma(
                resources.getDrawable(
                    R.drawable.ic_ma_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Xiang(
                resources.getDrawable(
                    R.drawable.ic_xiang_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Shi(
                resources.getDrawable(
                    R.drawable.ic_shi_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Shuai(
                resources.getDrawable(
                    R.drawable.ic_shuai_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Shi(
                resources.getDrawable(
                    R.drawable.ic_shi_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Xiang(
                resources.getDrawable(
                    R.drawable.ic_xiang_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Ma(
                resources.getDrawable(
                    R.drawable.ic_ma_red,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Ju(
                resources.getDrawable(
                    R.drawable.ic_ju_red,
                    activity?.theme
                ), true
            )
        )

        initializeListeners(gridLayout, BoardType.XIANGQI)
    }
}