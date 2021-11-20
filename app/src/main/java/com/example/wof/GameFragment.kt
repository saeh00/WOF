package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var guessedLetter = ""
    private var chosenWord = ""
    private var displayWordLength = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val spinButton = view.findViewById<Button>(R.id.wordPicker)
        val guessField = view.findViewById<EditText>(R.id.guessBox)
        val unknownWord = view.findViewById<TextView>(R.id.unknownWord)
        val guessButton = view.findViewById<Button>(R.id.guessButton)

        spinButton.setOnClickListener{
            guessField.visibility = View.VISIBLE
            guessButton.visibility = View.VISIBLE
            spinButton.visibility = View.GONE

            newGame()

            unknownWord.text = displayWordLength
            Toast.makeText(activity, chosenWord, Toast.LENGTH_SHORT).show()
        }

        guessButton.setOnClickListener{
            guessedLetter = guessField.text.toString().lowercase()
            guessField.text = null

            checkGuess()
        }

        return view
    }

    // This function starts the game
    private fun newGame(){
        // Chooses the word to be guessed from a given array in strings.xml
        val wordsArr: Array<String> = resources.getStringArray(R.array.words)
        chosenWord = ""
        chosenWord = wordsArr.random()

        // Turn the length of the array into underscores, to represent the length of the word
        repeat(chosenWord.length) {
            displayWordLength += "_"
        }
    }

    private fun displayGuess(){

    }

    private fun checkGuess(){
        val isGuessOneLetter: Boolean = guessedLetter.length == 1

        if (isGuessOneLetter){
            if (guessedLetter in chosenWord.lowercase()){
                Toast.makeText(activity, "Hurra you guessed correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "You guessed incorrect", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Please type one letter!", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}