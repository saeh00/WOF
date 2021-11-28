package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

class GameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var gameWon: Boolean = false
    private var gameLoss: Boolean = false
    private var spinState: Boolean = true
    private var guessResult: Boolean = false
    private var guessedLetter = ""
    private var chosenWord = ""
    private var displayWord = ""
    private var category = ""
    private var lives: Int? = null
    private var points: Int? = null
    private var spinResult: Int? = null
    private var spinResultText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val model by lazy {
            activity?.let {
                ViewModelProviders.of(it).get(Communicator::class.java)
            }
        }

        val guessField = view.findViewById<EditText>(R.id.guessBox)
        val unknownWord = view.findViewById<TextView>(R.id.unknownWord)
        val guessButton = view.findViewById<Button>(R.id.guessButton)
        val categoryText = view.findViewById<TextView>(R.id.categoryView)
        val spinBtn = view.findViewById<CardView>(R.id.spinButton)
        val livesText = view.findViewById<TextView>(R.id.livesView)
        val pointsText = view.findViewById<TextView>(R.id.pointView)
        val spinView = view.findViewById<TextView>(R.id.spinResultView)

        // Initialize the game
        newGame()
        livesText.text = lives.toString()
        pointsText.text = points.toString()

        unknownWord.text = displayWord
        categoryText.text = category
        Toast.makeText(activity, chosenWord, Toast.LENGTH_SHORT).show()

        guessButton.setOnClickListener {
            if (spinState) {
                Toast.makeText(activity, "Please spin the wheel first!", Toast.LENGTH_SHORT).show()
            } else {

                // Takes the text the player entered in the textfield, and puts int in the variable guessedLetter
                // Resets the textfield
                guessedLetter = guessField.text.toString().lowercase()
                guessField.text = null

                checkGuess()
                livesText.text = lives.toString()
                pointsText.text = points.toString()
                spinView.text = spinResultText

                unknownWord.text = displayWord

                checkWin(unknownWord.text as String)
                checkLoss()

                if (gameWon) {
                    Navigation.findNavController(view).navigate(R.id.action_gameWon)
                    model?.points = points
                }

                if (gameLoss) {
                    Navigation.findNavController(view).navigate(R.id.action_gameLost)
                }
            }


        }


        spinBtn.setOnClickListener {
            spinWheel()
            spinView.text = spinResultText
            pointsText.text = points.toString()
            livesText.text = lives.toString()
        }

        return view

    }

    /**
     * This function starts a new game
     */
    private fun newGame() {
        lives = 5
        points = 0
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
        if (spinState) {
            val randInt = (0..15).random()
            spinResult = null
            spinResult = randInt

            when (spinResult) {
                0, 1 -> {// Player loses a life
                    lives = lives?.minus(1)
                    spinResultText = "Player loses a life"
                    spinState = true
                }
                2, 3 -> {// Player gains a life
                    lives = lives?.plus(1)
                    spinResultText = "Player gains a life"
                    spinState = true
                }
                4 -> {// Player Loses all points
                    points = 0
                    spinResultText = "Player loses all points"
                    spinState = true
                }
                6, 7 -> {// Player gains a 1000 points
                    spinResultText = "Player gains 1000 points"
                    spinState = false
                }
                8, 9 -> {// Player lose 500 points
                    points = points?.minus(500)
                    spinResultText = "Player loses 500 points"
                    spinState = true
                }
                10 -> {// Jackpot! Player gains 10000 points
                    spinResultText = "Player gains 10000 points"
                    spinState = false
                }
                else -> {// Standard outcome, player gains 100 points
                    spinResultText = "Player gains 100 points"
                    spinState = false
                }
            }
        } else {
            Toast.makeText(
                activity,
                "cannot spin the wheel twice, guess a letter first!",
                Toast.LENGTH_SHORT
            ).show()
        }
        guessResult = false
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
                when (spinResult) {
                    6, 7 -> points = points?.plus(1000)
                    10 -> points = points?.plus(10000)
                    else -> points = points?.plus(100)
                }
            } else {
                Toast.makeText(activity, "SMH, You guessed incorrect", Toast.LENGTH_SHORT).show()
                lives = lives?.minus(1)
            }
            spinResultText = ""
            spinState = true
        } else {
            Toast.makeText(activity, "Please type one letter!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkWin(unknownWord: String) {
        gameWon = unknownWord.chars().allMatch(Character::isLetter)

        if (gameWon) {
            Toast.makeText(activity, "You won", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLoss(){
        if (lives == 0){
            gameLoss = true
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