package com.kailiu.chess.fragment

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_promote_dialog.*
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.app.Activity
import android.content.Intent
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece
import kotlinx.android.synthetic.main.layout_shogi_promote_dialog.*


class ShogiPromoteDialog: DialogFragment() {

    private var position = -1
    private var turn = -1
    private lateinit var piece: Piece

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_shogi_promote_dialog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isWhite = (turn % 2 ==  0)

        if (piece.rank > 5) {
            if ((position < 9 && !isWhite) || (position > 71 && isWhite)) {
                sendResult(piece.rank)
                dismiss()
            }
        }

        unpromotedImg.setImageDrawable(piece.drawable)
        unpromotedText.text = resources.getString(piece.unpromotedName)
        promotedImg.setImageDrawable(resources.getDrawable(piece.promotedImg, context?.theme))
        promotedText.text = resources.getString(piece.promotedName)

        promoteYesBtn.setOnClickListener {
            sendResult(piece.rank)
        }

        promoteNoBtn.setOnClickListener {
            dismiss()
        }
    }

    fun putPiece(piece: Piece, position: Int, turn: Int) {
        this.piece = piece
        this.position = position
        this.turn = turn
    }

    private fun sendResult(rank: Int) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra("rank", rank)
        intent.putExtra("position", position)
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        dismiss()
    }
}