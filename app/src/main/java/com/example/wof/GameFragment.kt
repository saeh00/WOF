package com.example.wof

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
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
    var spinState: Boolean = true
    private var guessedLetter = ""
    private var chosenWord = ""
    private var displayWord = ""
    private var category = ""
    private var lives = 5
    private var points = 700
    private var spinResult: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val model by lazy { activity?.let { ViewModelProviders.of(it).get(Communicator::class.java) } }

        val spinButton = view.findViewById<Button>(R.id.wordPicker)
        val guessField = view.findViewById<EditText>(R.id.guessBox)
        val unknownWord = view.findViewById<TextView>(R.id.unknownWord)
        val guessButton = view.findViewById<Button>(R.id.guessButton)
        val categoryText = view.findViewById<TextView>(R.id.categoryView)
        val wheelImg = view.findViewById<ImageView>(R.id.wheelImg)

        spinButton.setOnClickListener {
            guessField.visibility = View.VISIBLE
            guessButton.visibility = View.VISIBLE
            spinButton.visibility = View.GONE

            newGame()

            unknownWord.text = displayWord
            categoryText.text = category
            Toast.makeText(activity, chosenWord, Toast.LENGTH_SHORT).show()
        }

        guessButton.setOnClickListener {
            if (spinState) {
                Toast.makeText(activity, "Please spin the wheel first!", Toast.LENGTH_SHORT).show()
            } else {
                guessedLetter = guessField.text.toString().lowercase()
                guessField.text = null

                checkGuess()

                unknownWord.text = displayWord

                println(unknownWord.text)

                checkWinner(unknownWord.text as String)

                if (gameWon) {
                    Navigation.findNavController(view).navigate(R.id.action_gameWon)
                    model?.points = points
                }
            }


        }


        wheelImg.setOnClickListener {
            spinWheel()
        }

        return view
    }

    /**
     * This function starts a new game
     */
    private fun newGame() {
        // Chooses the word to be guessed from a given array in strings.xml
        val wordsArr: Array<String> = resources.getStringArray(R.array.words)
        val catArr: Array<String> = resources.getStringArray(R.array.category)
        chosenWord = ""
        chosenWord = wordsArr.random()

        category = ""
        category = catArr.random()

        // Turn the length of the array into underscores, to represent the length of the word
        repeat(chosenWord.length) {
            displayWord += "_"
        }
    }

    private fun spinWheel() {
        val randInt = (0..4).random()
        spinResult = null
        spinResult = randInt

        when (spinResult) {
            0 -> Toast.makeText(activity, "0", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(activity, "4", Toast.LENGTH_SHORT).show()
        }
        spinState = false
    }

    /**
     * Updates the displayed word, with the correctly guessed characters
     */
    private fun displayGuess() {
        val guessedLetterIndex = chosenWord.indexesOf(guessedLetter, true)

        when (guessedLetterIndex.size) {
            1 -> displayWord = displayWord.replaceRange(
                guessedLetterIndex[0],
                guessedLetterIndex[0] + 1,
                guessedLetter
            )

            2 -> {
                displayWord = displayWord.replaceRange(
                    guessedLetterIndex[0],
                    guessedLetterIndex[0] + 1,
                    guessedLetter
                )
                displayWord = displayWord.replaceRange(
                    guessedLetterIndex[1],
                    guessedLetterIndex[1] + 1,
                    guessedLetter
                )
            }

            3 -> {
                displayWord = displayWord.replaceRange(
                    guessedLetterIndex[0],
                    guessedLetterIndex[0] + 1,
                    guessedLetter
                )
                displayWord = displayWord.replaceRange(
                    guessedLetterIndex[1],
                    guessedLetterIndex[1] + 1,
                    guessedLetter
                )
                displayWord = displayWord.replaceRange(
                    guessedLetterIndex[2],
                    guessedLetterIndex[2] + 1,
                    guessedLetter
                )
            }
        }
    }

    /**
     * Checks if the guess is a legitimate entry
     * If the guess is legitimate it then checks if the guess is within the given word
     */
    private fun checkGuess() {
        val isGuessOneLetter: Boolean = guessedLetter.length == 1

        if (isGuessOneLetter) {
            if (guessedLetter in chosenWord.lowercase()) {
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

        if (gameWon) {
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
}