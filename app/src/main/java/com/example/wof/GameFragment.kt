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
import androidx.navigation.Navigation

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
    var gameWon: Boolean = false
    private var guessedLetter = ""
    private var chosenWord = ""
    private var displayWord = ""

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

            unknownWord.text = displayWord
            Toast.makeText(activity, chosenWord, Toast.LENGTH_SHORT).show()
        }

        guessButton.setOnClickListener{
            guessedLetter = guessField.text.toString().lowercase()
            guessField.text = null

            checkGuess()

            unknownWord.text = displayWord

            println(unknownWord.text)
            checkWinner(unknownWord.text as String)
            if (gameWon){
                Navigation.findNavController(view).navigate(R.id.action_gameWon)
            }
        }

        return view
    }

    /**
     * This function starts a new game
     */
    private fun newGame(){
        // Chooses the word to be guessed from a given array in strings.xml
        val wordsArr: Array<String> = resources.getStringArray(R.array.words)
        chosenWord = ""
        chosenWord = wordsArr.random()

        // Turn the length of the array into underscores, to represent the length of the word
        repeat(chosenWord.length) {
            displayWord += "_"
        }
    }

    /**
     * Updates the displayed word, with the correctly guessed characters
     */
    private fun displayGuess(){
        val guessedLetterIndex = chosenWord.indexesOf(guessedLetter, true)

        when (guessedLetterIndex.size){
            1 -> displayWord = displayWord.replaceRange(guessedLetterIndex[0],guessedLetterIndex[0]+1,guessedLetter)

            2 -> {displayWord = displayWord.replaceRange(guessedLetterIndex[0],guessedLetterIndex[0]+1,guessedLetter)
                displayWord = displayWord.replaceRange(guessedLetterIndex[1],guessedLetterIndex[1]+1,guessedLetter)}

            3 -> {displayWord = displayWord.replaceRange(guessedLetterIndex[0],guessedLetterIndex[0]+1,guessedLetter)
                displayWord = displayWord.replaceRange(guessedLetterIndex[1],guessedLetterIndex[1]+1,guessedLetter)
                displayWord = displayWord.replaceRange(guessedLetterIndex[2],guessedLetterIndex[2]+1,guessedLetter)}
        }
    }

    /**
     * Checks if the guess is a legitimate entry
     * If the guess is legitimate it then checks if the guess is within the given word
     */
    private fun checkGuess(){
        val isGuessOneLetter: Boolean = guessedLetter.length == 1

        if (isGuessOneLetter){
            if (guessedLetter in chosenWord.lowercase()){
                Toast.makeText(activity, "Hurra, you guessed correct", Toast.LENGTH_SHORT).show()
                displayGuess()
            } else {
                Toast.makeText(activity, "SMH, You guessed incorrect", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Please type one letter!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkWinner(unknownWord: String) {
        gameWon = unknownWord.chars().allMatch(Character::isLetter)

        if (gameWon){
            Toast.makeText(activity, "You won", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Funktionen kan finde indexes af substring i et string.
     * taget fra stackoverflow
     * link: https://stackoverflow.com/questions/62189457/get-indexes-of-substrings-contained-in-a-string-in-kotlin-way
    */
    private fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
        return this?.let {
            val regex = if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)
            regex.findAll(this).map { it.range.start }.toList()
        } ?: emptyList()
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