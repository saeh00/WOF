package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [GameLostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameLostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_lost, container, false)

        val playAgainBtn = view.findViewById<Button>(R.id.playAgain)

        playAgainBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_playAgainL)
        }

        return view
    }

}