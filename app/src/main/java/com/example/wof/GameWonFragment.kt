package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

class GameWonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_won, container, false)
        val model by lazy {
            activity?.let {
                ViewModelProviders.of(it).get(Communicator::class.java)
            }
        }

        val playAgainBtn = view.findViewById<Button>(R.id.playAgain)
        val highScore = view.findViewById<TextView>(R.id.highScoreView)

        highScore.text = resources.getString(R.string.highScore) + model?.points.toString()

        playAgainBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_playAgainW)
        }

        return view
    }

}