package com.kailiu.chess.fragment

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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kailiu.chess.BoardViewModel
import com.kailiu.chess.MainActivity
import com.kailiu.chess.R
import com.kailiu.chess.database.Board
import com.kailiu.chess.pieces.Piece
import com.kailiu.chess.pieces.chess.King
import com.kailiu.chess.pieces.shogi.Osho
import com.kailiu.chess.pieces.xiangqi.Shuai
import kotlinx.android.synthetic.main.fragment_board.*
import kotlin.concurrent.thread

var globalTurn = 0

open class BoardFragment: Fragment() {
    var pieceArray = ArrayList<Piece>()
    var whiteCapture = ArrayList<Piece>()
    var blackCapture = ArrayList<Piece>()
    val validPieces = ArrayList<Pair<Int, Boolean>>()

    var selected: Pair<Int, LocationType>? = null
    var inCheck = false

    var turn = 0

    lateinit var type: BoardType

    val listen =  MutableLiveData<Int>()

    var getSpaces = { position: Int, location: LocationType, isBoard: Boolean ->
        val spaces = arrayListOf<Pair<Int, Boolean>>()

        var captureList: ArrayList<Piece>? = null
        if (location == LocationType.WHITE) captureList = whiteCapture
        if (location == LocationType.BLACK) captureList = blackCapture

        if (selected == null) {
            if (isBoard) selected = Pair(position, location)

            for (i in 0 until 9) {
                val tempSpaces = arrayListOf<Pair<Int, Boolean>>()
                for (j in 0 until 9) {
                    if (pieceArray[j * 9 + i].isEmpty) {
                        tempSpaces.add(Pair(j * 9 + i, false))
                    } else if (captureList!![position].rank == 8  && pieceArray[j * 9 + i].rank == 8 && pieceArray[j * 9 + i].isSameColor(captureList[position])) {
                        tempSpaces.clear()
                        break
                    }
                }

                for (j in tempSpaces) {
                    if (location != LocationType.BOARD) {
                        layout[j.first].setBackgroundColor(
                            resources.getColor(
                                R.color.empty_space,
                                activity?.theme
                            )
                        )
                    }
                }
                spaces.addAll(tempSpaces)
            }
        } else if (!isBoard) {
            selected = null
            for (i in 0 until pieceArray.size) {
                if (location != LocationType.BOARD) {
                    layout[i].setBackgroundColor(Color.TRANSPARENT)
                }
            }
        } else {
            for (i in 0 until 9) {
                val tempSpaces = arrayListOf<Pair<Int, Boolean>>()
                for (j in 0 until 9) {
                    if (pieceArray[j * 9 + i].isEmpty) {
                        tempSpaces.add(Pair(j * 9 + i, false))
                    } else if (captureList!!.get(selected!!.first).rank == 8  && pieceArray[j * 9 + i].rank == 8 && pieceArray[j * 9 + i].isSameColor(captureList[selected!!.first])) {
                        tempSpaces.clear()
                        break
                    }
                }
                spaces.addAll(tempSpaces)
            }
        }

        spaces
    }

    protected lateinit var whiteRecyclerView: RecyclerView
    protected lateinit var whiteViewAdapter: RecyclerView.Adapter<*>
    protected lateinit var whiteViewManager: RecyclerView.LayoutManager

    protected lateinit var blackRecyclerView: RecyclerView
    protected lateinit var blackViewAdapter: RecyclerView.Adapter<*>
    protected lateinit var blackViewManager: RecyclerView.LayoutManager

    lateinit var layout: GridLayout

    lateinit var boardViewModel: BoardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        globalTurn = 0
        boardViewModel = ViewModelProviders.of(activity!!).get(BoardViewModel::class.java)

        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCaptureLists()

        listen.value = turn

        listen.observe(viewLifecycleOwner, Observer {
            globalTurn++
            if (turn % 2 == 0) {
                player1.setTextColor(Color.BLACK)
                player2.setTextColor(Color.GRAY)
            } else {
                player1.setTextColor(Color.GRAY)
                player2.setTextColor(Color.BLACK)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        boardViewModel.getGame(type).observe(this,
            Observer { t ->
                t?.let {
                    clearGameIcons()
                    pieceArray = t.board
                    whiteCapture = t.white
                    blackCapture = t.black

                    for (p in whiteCapture) {
                        p.setDrawable(context!!, true)
                    }
                    for (p in blackCapture) {
                        p.setDrawable(context!!, true)
                    }

                    setupCaptureLists()

                    whiteViewAdapter.notifyDataSetChanged()
                    blackViewAdapter.notifyDataSetChanged()

                    turn = t.turn
                    initializeListeners(gridLayout, BoardType.valueOf(t.game))
                }
            })
    }

    override fun onPause() {
        super.onPause()

        thread {
            boardViewModel.setGame(type, turn, pieceArray, whiteCapture, blackCapture)
        }
    }

    protected fun clearGameIcons() {
        gridLayout.removeAllViews()
        whiteRecyclerView.removeAllViews()
        blackRecyclerView.removeAllViews()
    }

    private fun setupCaptureLists() {

        whiteViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        whiteViewAdapter = CapturedAdapter(whiteCapture, getSpaces)
        blackViewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        blackViewAdapter = CapturedAdapter(blackCapture, getSpaces)

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
    }

    fun initializeListeners(layout: GridLayout, type: BoardType) {
        this.layout = layout
        this.type = type
        for (i in 0 until pieceArray.size) {
            val piece = pieceArray[i]
            piece.setDrawable(context!!)
            val img = ImageView(context)
            img.setImageDrawable(piece.drawable)
            img.layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)).apply {
                width = 0
                height = 0
            }

            if (type == BoardType.SHOGI && piece.isWhite == false) {
                img.scaleY = -1f
            }

            layout.addView(img)
            piece.position = i

            var spaces: ArrayList<Pair<Int, Boolean>>

            img.setOnClickListener {

                val induceCheck = findAndCheckKing(i).second
                var selectable = ((turn % 2 == 0) and (pieceArray[i].isWhite == true)) or ((turn % 2 == 1) and (pieceArray[i].isWhite == false))
                val valid = validPieces.contains(Pair(i, false)) or validPieces.contains(Pair(i, true))

                if (inCheck and (pieceArray[i].rank != 1)) selectable = false
                if (inCheck and valid) selectable = true

                for (j in 0 until pieceArray.size) {
                    layout[j].setBackgroundColor(Color.TRANSPARENT)
                }

                if (selectable && (selected == null) /*&& !induceCheck*/) {
                    selected = Pair(i, LocationType.BOARD)

                    spaces = pieceArray[i].calcMovement(pieceArray, i)
                    layout[i].setBackgroundColor(resources.getColor(R.color.this_space, activity?.theme))
                    for (j in spaces) {
                        val color = if (j.second) resources.getColor(R.color.enemy_space, activity?.theme) else resources.getColor(R.color.empty_space, activity?.theme)
                        layout[j.first].setBackgroundColor(color)
                    }
                } else if (selected != null) {
                    if (selected!!.second == LocationType.BOARD) {
                        spaces = pieceArray[selected!!.first].calcMovement(pieceArray, selected!!.first)

                        for (j in spaces) {
                            if (j.first == i) {
                                checkCastling(j)
                                swapPieces(selected!!.first, i)
                                spaces.clear()
                                turn += 1
                                listen.postValue(turn)
                                break
                            }
                        }

                        if (type != BoardType.XIANGQI && !pieceArray[i].isPromoted) {
                            promote(i)
                        }
                    } else {
                        spaces = getSpaces(selected!!.first, selected!!.second, true)

                        for (j in spaces) {
                            if (j.first == i) {
                                placePiece(selected!!.first, i, selected!!.second)
                                turn += 1
                                listen.postValue(turn)
                            }
                        }
                    }

                    selected = null

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

                    checkWin()
                } else if (inCheck) {
                    val kingStatus = findAndCheckKing()
                    inCheck = kingStatus.second
                }
            }
        }
    }

    protected fun findAndCheckKing(removed: Int = -1): Triple<Int, Boolean, Boolean> {
        val checkColor = resources.getColor(R.color.check_space, activity?.theme)

        val white = (turn % 2) == 0

        val removedPiece: Piece?
        if (removed > 0 && pieceArray[removed].rank != 1) {
            removedPiece = pieceArray[removed]
            pieceArray[removed] = Piece()
        } else {
            removedPiece = null
        }

        for (j in 0 until pieceArray.size) {
            if (pieceArray[j].isWhite == white && pieceArray[j].rank == 1) {
                val kingPiece = when (type) {
                    BoardType.CHESS -> pieceArray[j] as King
                    BoardType.XIANGQI -> pieceArray[j] as Shuai
                    else -> pieceArray[j] as Osho
                }
                val check = kingPiece.isInCheck(pieceArray, j)

                var block =  false

                removedPiece?.let {
                    pieceArray[removed] = removedPiece
                    if (check.first != -1) {
                        block = pieceArray[check.first].calcMovement(pieceArray, check.first)
                            .contains(Pair(removed, true))
                    }
                }

                if (check.second /*&& !block*/) {
                    gridLayout[j].setBackgroundColor(checkColor)
                    if (pieceArray[j].calcMovement(pieceArray, j).isEmpty()) {
                        return Triple(j, true, true)
                    }
                    return Triple(j, true, false)
                } else {
                    removedPiece?.let { pieceArray[removed] = removedPiece }
                    return Triple(j, false, false)
                }
            }
        }

        return Triple(-1, false, false)
    }

    open fun promote(position: Int) {}

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

    protected fun placePiece(p1: Int, p2: Int, locationType: LocationType) {
        if (type == BoardType.SHOGI) {
            val captureList = if (locationType == LocationType.WHITE) whiteCapture else blackCapture

            pieceArray[p2] =
                captureList[p1]
            captureList.removeAt(p1)

            (gridLayout[p2] as ImageView).setImageDrawable(pieceArray[p2].drawable)
            (gridLayout[p2] as ImageView).scaleY = if (pieceArray[p2].isWhite == false) -1f else 1f
        }
    }


    protected fun swapPieces(p1: Int, p2: Int) {
        if (pieceArray[p2].isEmpty) {
            val temp = pieceArray[p1]
            pieceArray[p1] = pieceArray[p2]
            pieceArray[p2] = temp
        } else if (pieceArray[p1].isWhite != pieceArray[p2].isWhite) {
            pieceArray[p2].isPromoted = false
            pieceArray[p2].setDrawable(context!!)
            if (pieceArray[p2].isWhite == true) {
                pieceArray[p2].isWhite = false
                blackCapture.add(pieceArray[p2])
                blackViewAdapter.notifyDataSetChanged()
            } else {
                pieceArray[p2].isWhite = true
                whiteCapture.add(pieceArray[p2])
                whiteViewAdapter.notifyDataSetChanged()
            }

            pieceArray[p2] = pieceArray[p1]
            pieceArray[p1] = Piece()
        }

        pieceArray[p1].hasMoved = true
        pieceArray[p2].hasMoved = true

        (gridLayout[p1] as ImageView).setImageDrawable(pieceArray[p1].drawable)
        (gridLayout[p2] as ImageView).setImageDrawable(pieceArray[p2].drawable)
        if (type == BoardType.SHOGI) {
            (gridLayout[p1] as ImageView).scaleY = if (pieceArray[p1].isWhite == false) -1f else 1f
            (gridLayout[p2] as ImageView).scaleY = if (pieceArray[p2].isWhite == false) -1f else 1f
        }
    }

    fun checkCastling(pair: Pair<Int, Boolean>) {
        if (pieceArray[selected!!.first].rank == 1) {
            if (Math.abs(pair.first - selected!!.first) == 2) {
                swapPieces(selected!!.first - 3, selected!!.first - 1)
            } else if (Math.abs(pair.first - selected!!.first) == 3) {
                swapPieces(selected!!.first + 4, selected!!.first + 2)
            }
        }
    }

    class CapturedAdapter(private val myDataset: ArrayList<Piece>, val getSpaces: (Int, LocationType, Boolean) -> List<Pair<Int, Boolean>>):
        RecyclerView.Adapter<CapturedAdapter.MyViewHolder>() {

        class MyViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_piece, parent, false) as ImageView
            return MyViewHolder(textView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            println("drawable: ${myDataset[position].drawable}")
            holder.imageView.setImageDrawable(myDataset[position].drawable)
            holder.imageView.layoutParams = RecyclerView.LayoutParams(100, 100)
            holder.imageView.setOnClickListener {
                if (((globalTurn % 2 == 0) and (myDataset[position].isWhite != true)) or ((globalTurn % 2 == 1) and (myDataset[position].isWhite != false))) {
                    getSpaces(
                        position,
                        if (myDataset[position].isWhite == true) LocationType.WHITE else LocationType.BLACK,
                        false
                    )
                }
            }
        }

        override fun getItemCount() = myDataset.size
    }

}
