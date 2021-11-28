package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

/**
 * A simple [Fragment] subclass.
 * Use the [GameWonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameWonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_won, container, false)
        val model by lazy { activity?.let { ViewModelProviders.of(it).get(Communicator::class.java) } }

        //val testCommunicator = view.findViewById<Button>(R.id.testViewModel)
        val playAgainBtn = view.findViewById<Button>(R.id.playAgain)

        //testCommunicator.setOnClickListener{Toast.makeText(activity, model?.points.toString(), Toast.LENGTH_SHORT).show()}

        playAgainBtn.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_playAgainW)
        }



        return view
    }

}