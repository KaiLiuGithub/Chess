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


class PromoteDialog: DialogFragment() {

    private var position = -1
    private var turn = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_promote_dialog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        var isWhite = (turn % 2 ==  0)

        if (isWhite) queenImg.setImageDrawable(resources.getDrawable(if (turn % 2 == 0) R.drawable.ic_queen_black else R.drawable.ic_queen_white))
        if (isWhite) rookImg.setImageDrawable(resources.getDrawable(if (turn % 2 == 0) R.drawable.ic_rook_black else R.drawable.ic_rook_white))
        if (isWhite) knightImg.setImageDrawable(resources.getDrawable(if (turn % 2 == 0) R.drawable.ic_knight_black else R.drawable.ic_knight_white))
        if (isWhite) bishopImg.setImageDrawable(resources.getDrawable(if (turn % 2 == 0) R.drawable.ic_bishop_black else R.drawable.ic_bishop_white))
        
        queenLayout.setOnClickListener {
            sendResult(2)
        }

        rookLayout.setOnClickListener {
            sendResult(3)
        }

        knightLayout.setOnClickListener {
            sendResult(4)
        }

        bishopLayout.setOnClickListener {
            sendResult(5)
        }
    }

    fun putPosition(position: Int, turn: Int) {
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