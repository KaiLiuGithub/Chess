package com.kailiu.chess.fragment.chess

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_board.*
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kailiu.chess.R
import com.kailiu.chess.fragment.BoardFragment
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
        gridLayout.removeAllViews()
        whiteRecyclerView.removeAllViews()
        blackRecyclerView.removeAllViews()

        pieceArray.add(
            Rook(
                resources.getDrawable(
                    R.drawable.ic_rook_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Knight(
                resources.getDrawable(
                    R.drawable.ic_knight_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bishop(
                resources.getDrawable(
                    R.drawable.ic_bishop_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            King(
                resources.getDrawable(
                    R.drawable.ic_king_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Queen(
                resources.getDrawable(
                    R.drawable.ic_queen_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Bishop(
                resources.getDrawable(
                    R.drawable.ic_bishop_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Knight(
                resources.getDrawable(
                    R.drawable.ic_knight_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Rook(
                resources.getDrawable(
                    R.drawable.ic_rook_black,
                    activity?.theme
                )
            )
        )

        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_black,
                    activity?.theme
                )
            )
        )

        for (i in 0..31) {
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
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Pawn(
                resources.getDrawable(
                    R.drawable.ic_pawn_white,
                    activity?.theme
                ), true
            )
        )
        
        pieceArray.add(
            Rook(
                resources.getDrawable(
                    R.drawable.ic_rook_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Knight(
                resources.getDrawable(
                    R.drawable.ic_knight_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Bishop(
                resources.getDrawable(
                    R.drawable.ic_bishop_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            King(
                resources.getDrawable(
                    R.drawable.ic_king_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Queen(
                resources.getDrawable(
                    R.drawable.ic_queen_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Bishop(
                resources.getDrawable(
                    R.drawable.ic_bishop_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Knight(
                resources.getDrawable(
                    R.drawable.ic_knight_white,
                    activity?.theme
                ), true
            )
        )
        pieceArray.add(
            Rook(
                resources.getDrawable(
                    R.drawable.ic_rook_white,
                    activity?.theme
                ), true
            )
        )

        val listen =  MutableLiveData<Int>()

        listen.setValue(turn) //Initilize with a value

        listen.observe(this, Observer {
            if (turn % 2 == 0) {
                player1.setTextColor(Color.BLACK)
                player2.setTextColor(Color.GRAY)
            } else {
                player1.setTextColor(Color.GRAY)
                player2.setTextColor(Color.BLACK)
            }
        })

        initializeListeners(gridLayout, "Chess")
    }

    override fun promotePawn(position: Int) {
        if ((pieceArray[position].rank == 6) and ((position < 8) or (position > 55))) {
            super.promotePawn(position)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val bundle = data!!.extras
                val position = bundle!!.getInt("position")
                println("Rank: ${bundle.get("rank")} || $position")
                when (bundle.get("rank")) {
                    2 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_queen_black else R.drawable.ic_queen_white
                        pieceArray[position] = Queen(
                            resources.getDrawable(
                                img,
                                activity?.theme
                            ), turn % 2 != 0
                        )
                    }
                    3 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_rook_black else R.drawable.ic_rook_white
                        pieceArray[position] = Rook(
                            resources.getDrawable(
                                img,
                                activity?.theme
                            ), turn % 2 != 0
                        )
                    }
                    4 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_knight_black else R.drawable.ic_knight_white
                        pieceArray[position] = Knight(
                            resources.getDrawable(
                                img,
                                activity?.theme
                            ), turn % 2 != 0
                        )
                    }
                    5 -> {
                        val img = if (turn % 2 == 0) R.drawable.ic_bishop_black else R.drawable.ic_bishop_white
                        pieceArray[position] = Bishop(
                            resources.getDrawable(
                                img,
                                activity?.theme
                            ), turn % 2 != 0
                        )
                    }
                }
                (gridLayout[position] as ImageView).setImageDrawable(pieceArray[position].drawable)
            }
        }
    }
}