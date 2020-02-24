package com.kailiu.chess.fragment

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kailiu.chess.R
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.chess.King
import kotlinx.android.synthetic.main.fragment_board.*

open class BoardFragment: Fragment() {
    val pieceArray = ArrayList<Piece>()
    val whiteCapture = ArrayList<Piece>()
    val blackCapture = ArrayList<Piece>()
    val validPieces = ArrayList<Pair<Int, Boolean>>()

    var selected: Int? = null
    var inCheck = false

    var turn = 0

    var type: String = ""

    protected lateinit var whiteRecyclerView: RecyclerView
    protected lateinit var whiteViewAdapter: RecyclerView.Adapter<*>
    protected lateinit var whiteViewManager: RecyclerView.LayoutManager

    protected lateinit var blackRecyclerView: RecyclerView
    protected lateinit var blackViewAdapter: RecyclerView.Adapter<*>
    protected lateinit var blackViewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        whiteViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        whiteViewAdapter = CapturedAdapter(whiteCapture)
        blackViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        blackViewAdapter = CapturedAdapter(blackCapture)

        whiteRecyclerView = whiteCaptureLayout.apply {
            setHasFixedSize(true)
            layoutManager = whiteViewManager
            adapter = whiteViewAdapter
        }
        blackRecyclerView = blackCaptureLayout.apply {
            setHasFixedSize(true)
            layoutManager = blackViewManager
            adapter = blackViewAdapter
        }

        val listen =  MutableLiveData<Int>()

        listen.value = turn

        listen.observe(this, Observer {
            if (turn % 2 == 0) {
                player1.setTextColor(Color.BLACK)
                player2.setTextColor(Color.GRAY)
            } else {
                player1.setTextColor(Color.GRAY)
                player2.setTextColor(Color.BLACK)
            }
        })
    }

    fun initializeListeners(layout: GridLayout, type: String) {
        this.type = type
        for (i in 0 until pieceArray.size) {
            val piece = pieceArray[i]
            val img = ImageView(context)
            img.setImageDrawable(piece.drawable)
            img.layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)).apply {
                width = 0
                height = 0
            }
            layout.addView(img)
            piece.position = i

            var spaces: ArrayList<Pair<Int, Boolean>>

            img.setOnClickListener {

                var selectable = ((turn % 2 == 0) and (pieceArray[i].isWhite == true)) or ((turn % 2 == 1) and (pieceArray[i].isWhite == false))
                val valid = validPieces.contains(Pair(i, false)) or validPieces.contains(Pair(i, true))

                if (inCheck and (pieceArray[i].rank != 1)) selectable = false
                if (inCheck and valid) selectable = true

                for (j in 0 until pieceArray.size) {
                    layout[j].setBackgroundColor(Color.TRANSPARENT)
                }

                if (selectable and (selected == null)) {
                    selected = i

                    spaces = pieceArray[i].calcMovement(pieceArray, i)
                    layout[i].setBackgroundColor(Color.YELLOW)
                    for (j in spaces) {
                        val color = if (j.second) Color.RED else Color.BLUE
                        layout[j.first].setBackgroundColor(color)
                    }
                } else if (selected != null) {
                    spaces = pieceArray[selected!!].calcMovement(pieceArray, selected!!)

                    for (j in spaces) {
                        if (j.first == i) {
                            swapPieces(selected!!, i)
                            spaces.clear()
                            turn += 1
                            break
                        }
                    }

                    if (type != "Chinese") {
                        promotePawn(i)
                    }

                    val kingStatus = findAndCheckKing()
                    inCheck = kingStatus.second

                    if (inCheck) {
                        for (j in 0 until pieceArray.size) {
                            val validPos = pieceArray[j].blockOrEat(pieceArray, i, j, kingStatus.first)
                            if (validPos.first != -1) validPieces.add(validPos)
                        }
                    } else {
                        validPieces.clear()
                    }

                    selected = null

                    checkWin()
                } else if (inCheck) {
                    val kingStatus = findAndCheckKing()
                    inCheck = kingStatus.second
                }
            }
        }
    }

    protected fun findAndCheckKing(): Triple<Int, Boolean, Boolean> {
        val checkColor = Color.parseColor("#800000")

        val white = (turn % 2) == 0

        for (j in 0 until pieceArray.size) {
            if (pieceArray[j].isWhite == white && pieceArray[j].rank == 1) {
                if ((pieceArray[j] as King).isInCheck(pieceArray, j)) {
                    gridLayout[j].setBackgroundColor(checkColor)
                    if (pieceArray[j].calcMovement(pieceArray, j).isEmpty()) {
                        return Triple(j, true, true)
                    }
                    return Triple(j, true, false)
                } else {
                    return Triple(j, false, false)
                }
            }
        }

        return Triple(-1, false, false)
    }

    open fun promotePawn(position: Int) {
        val picker = PromoteDialog()
        picker.putPosition(position, turn)
        picker.setTargetFragment(this, 1)
        picker.show(fragmentManager!!.beginTransaction(), "Date Picker")
    }

    protected fun checkWin() {
        if (turn % 2 == 0) {
            for (i in blackCapture) {
                if (i.rank == 1) {
                    Toast.makeText(activity, "Winner is Black", Toast.LENGTH_LONG).show()
                    resetLayout.visibility = View.VISIBLE
                    winnerText.text = "Player 2 (Black) WINS!"
                    break
                }
            }
        } else {
            for (i in whiteCapture) {
                if (i.rank == 1) {
                    Toast.makeText(activity, "Winner is White", Toast.LENGTH_LONG).show()
                    resetLayout.visibility = View.VISIBLE
                    winnerText.text = "Player 1 (White) WINS!"
                    break
                }
            }
        }
    }

    protected fun swapPieces(p1: Int, p2: Int) {
        if (pieceArray[p2].isEmpty) {
            val temp = pieceArray[p1]
            pieceArray[p1] = pieceArray[p2]
            pieceArray[p2] = temp
        } else if (pieceArray[p1].isWhite != pieceArray[p2].isWhite) {
            if (pieceArray[p2].isWhite == true) {
                blackCapture.add(pieceArray[p2])
                blackViewAdapter.notifyDataSetChanged()
            } else {
                whiteCapture.add(pieceArray[p2])
                whiteViewAdapter.notifyDataSetChanged()
            }

            pieceArray[p2] = pieceArray[p1]
            pieceArray[p1] = Piece(
                resources.getDrawable(
                    R.drawable.ic_transparent,
                    activity?.theme
                )
            )
        }

        (gridLayout[p1] as ImageView).setImageDrawable(pieceArray[p1].drawable)
        (gridLayout[p2] as ImageView).setImageDrawable(pieceArray[p2].drawable)
    }

    class CapturedAdapter(private val myDataset: ArrayList<Piece>) :
        RecyclerView.Adapter<CapturedAdapter.MyViewHolder>() {

        class MyViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_piece, parent, false) as ImageView

            return MyViewHolder(textView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.imageView.setImageDrawable(myDataset[position].drawable)
            holder.imageView.layoutParams = RecyclerView.LayoutParams(40, 40)
        }

        override fun getItemCount() = myDataset.size
    }
}
