package com.kailiu.chess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment: Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        navController = Navigation.findNavController(view)

        chessBtn.setOnClickListener {
            navController.navigate(R.id.action_mainMenuFragment_to_chessFragment)
        }

        xiangqiBtn.setOnClickListener {
            navController.navigate( R.id.action_mainMenuFragment_to_xiangqiFragment)
        }

        shogiBtn.setOnClickListener {
            navController.navigate( R.id.action_mainMenuFragment_to_shogiFragment)
        }
    }
}