package com.kailiu.chess.fragment.chess

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_board.*
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardFragment
import com.kailiu.chess.fragment.BoardType
import com.kailiu.chess.fragment.PromoteDialog
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.chess.*

class ChessFragment: BoardFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetBtn.setOnClickListener {
            initializeBoard()
            resetLayout.visibility = View.GONE
        }

        initializeBoard()
    }

    private fun initializeBoard() {
        turn = 0

        pieceArray.clear()
        whiteCapture.clear()
        blackCapture.clear()

        clearGameIcons()

        pieceArray.add(
            Rook()
        )
        pieceArray.add(
            Knight()
        )
        pieceArray.add(
            Bishop()
        )
        pieceArray.add(
            King()
        )
        pieceArray.add(
            Queen()
        )
        pieceArray.add(
            Bishop()
        )
        pieceArray.add(
            Knight()
        )
        pieceArray.add(
            Rook()
        )

        for (i in 0 until 8) {
            pieceArray.add(
                Pawn()
            )
        }

        for (i in 0..31) {
            pieceArray.add(
                Piece()
            )
        }

        for (i in 0 until 8) {
            pieceArray.add(
                Pawn(true)
            )
        }
        
        pieceArray.add(
            Rook(true)
        )
        pieceArray.add(
            Knight(true)
        )
        pieceArray.add(
            Bishop(true)
        )
        pieceArray.add(
            King(true)
        )
        pieceArray.add(
            Queen(true)
        )
        pieceArray.add(
            Bishop(true)
        )
        pieceArray.add(
            Knight(true)
        )
        pieceArray.add(
            Rook(true)
        )

        val listen =  MutableLiveData<Int>()

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

        initializeListeners(gridLayout, BoardType.CHESS)
    }

    override fun promote(position: Int) {
        if ((pieceArray[position].rank == 6) and ((position < 8) or (position > 55))) {
            val picker = PromoteDialog()
            picker.putPosition(position, turn)
            picker.setTargetFragment(this, 1)
            picker.show(fragmentManager!!.beginTransaction(), "Date Picker")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val position = bundle!!.getInt("position")

                when (bundle.get("rank")) {
                    2 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_queen_black else R.drawable.ic_queen_white
                        pieceArray[position] = Queen( turn % 2 != 0)
                    }
                    3 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_rook_black else R.drawable.ic_rook_white
                        pieceArray[position] = Rook(turn % 2 != 0)
                    }
                    4 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_knight_black else R.drawable.ic_knight_white
                        pieceArray[position] = Knight(turn % 2 != 0)
                    }
                    5 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_bishop_black else R.drawable.ic_bishop_white
                        pieceArray[position] = Bishop(turn % 2 != 0)
                    }
                }
                (gridLayout[position] as ImageView).setImageDrawable(pieceArray[position].drawable)
            }
        }
    }
}