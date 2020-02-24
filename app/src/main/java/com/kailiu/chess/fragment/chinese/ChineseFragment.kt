package com.kailiu.chess.fragment.chinese

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardFragment
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.chess.*
import com.kailiu.chess.pieces.chinese.*
import kotlinx.android.synthetic.main.fragment_board.*

class ChineseFragment: BoardFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayout.columnCount = 9
        gridLayout.rowCount = 10
        gridLayout.background = resources.getDrawable(R.drawable.ic_chinese_board, activity?.theme)

        gridLayout.apply {
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0)
                .dimensionRatio = "9:10"
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
                    R.drawable.ic_pao_red,
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

        initializeListeners(gridLayout, "Chinese")
    }
}