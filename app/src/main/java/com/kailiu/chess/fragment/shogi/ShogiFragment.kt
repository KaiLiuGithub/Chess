package com.kailiu.chess.fragment.shogi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardFragment
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.fragment.ShogiPromoteDialog
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.shogi.*
import kotlinx.android.synthetic.main.fragment_board.*

class ShogiFragment: BoardFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayout.columnCount = 9
        gridLayout.rowCount = 10
        gridLayout.background = resources.getDrawable(R.drawable.ic_shogi_board, activity?.theme)

        gridLayout.apply {
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0)
                .dimensionRatio = "9:9"
        }

        resetBtn.setOnClickListener {
            initializeBoard()
            resetLayout.visibility = View.GONE
        }

        initializeBoard()
    }

    fun initializeBoard() {
        turn = 0

        pieceArray.clear()
        whiteCapture.clear()
        blackCapture.clear()

        clearGameIcons()

        pieceArray.add(Kyosha())
        pieceArray.add(Keima())
        pieceArray.add(Ginsho())
        pieceArray.add(Kinsho())
        pieceArray.add(Osho())
        pieceArray.add(Kinsho())
        pieceArray.add(Ginsho())
        pieceArray.add(Keima())
        pieceArray.add(Kyosha())

        pieceArray.add(Piece())
        pieceArray.add(Hisha())
        for (i in 0 until 5) {
            pieceArray.add(Piece())
        }
        pieceArray.add(Kakugyo())
        pieceArray.add(Piece())

        for (i in 0 until 9) {
            pieceArray.add(Fuhyo())
        }

        for (i in 0 until 27) {
            pieceArray.add(Piece())
        }

        for (i in 0 until 9) {
            pieceArray.add(Fuhyo(true))
        }

        pieceArray.add(Piece())
        pieceArray.add(Kakugyo(true))
        for (i in 0 until 5) {
            pieceArray.add(Piece())
        }
        pieceArray.add(Hisha(true))
        pieceArray.add(Piece())

        pieceArray.add(Kyosha(true))
        pieceArray.add(Keima(true))
        pieceArray.add(Ginsho(true))
        pieceArray.add(Kinsho(true))
        pieceArray.add(Osho(true))
        pieceArray.add(Kinsho(true))
        pieceArray.add(Ginsho(true))
        pieceArray.add(Keima(true))
        pieceArray.add(Kyosha(true))

        val listen = MutableLiveData<Int>()

        listen.setValue(turn) //Initilize with a value

        listen.observe(viewLifecycleOwner, Observer {
            if (turn % 2 == 0) {
                player1.setTextColor(Color.BLACK)
                player2.setTextColor(Color.GRAY)
            } else {
                player1.setTextColor(Color.GRAY)
                player2.setTextColor(Color.BLACK)
            }
        })

        initializeListeners(gridLayout, BoardType.SHOGI)
    }

    override fun promote(position: Int) {
        if (pieceArray[position].isWhite == true && position < 27) {
            val picker = ShogiPromoteDialog()
            picker.putPiece(pieceArray[position], position, turn)
            picker.setTargetFragment(this, 1)
            picker.show(fragmentManager!!.beginTransaction(), "Date Picker")

        } else if (pieceArray[position].isWhite != true && position > 53) {
            val picker = ShogiPromoteDialog()
            picker.putPiece(pieceArray[position], position, turn)
            picker.setTargetFragment(this, 1)
            picker.show(fragmentManager!!.beginTransaction(), "Date Picker")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val position = bundle!!.getInt("position")

                pieceArray[position].drawable = resources.getDrawable(pieceArray[position].promotedImg, activity?.theme)
                pieceArray[position].isPromoted = true
                (gridLayout[position] as ImageView).setImageDrawable(pieceArray[position].drawable)
            }
        }
    }
}