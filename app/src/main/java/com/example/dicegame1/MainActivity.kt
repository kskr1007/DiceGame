package com.example.dicegame1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dice()
        }
    }
}
var newGame = false

@Composable
fun Dice() {
    // keep track fo dye 1
    var dieIndex1 by remember { mutableIntStateOf(5) }
    // keep track of dye 2
    var dieIndex2 by remember { mutableIntStateOf(5) }
    // total for player 1
    var score1 by remember { mutableIntStateOf(0) }
    // total for player 2
    var score2 by remember { mutableIntStateOf(0) }
    // if player 1 holds
    var isHeld1 by remember { mutableStateOf(false) }
    // if player 2 holds
    var isHeld2 by remember { mutableStateOf(false) }

    // dice drawables
    val dieArray = arrayOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

    // game is over if either score passes 21 or of both players hold
    val gameOver = score1 >= 21 || score2 >= 21 || (isHeld1 && isHeld2)
    val resultMessage = checkRound(score1, score2, isHeld1, isHeld2)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Blackjack", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Best of 3 \n Player 1: $totalScore1 \n Player 2: $totalScore2",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            // Player 1
            Card(
                modifier = Modifier.weight(1f).padding(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Player 1", style = MaterialTheme.typography.titleLarge)
                    if (isHeld1) Text(text = "(Held)", color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = dieArray[dieIndex1]),
                        contentDescription = "Player 1 Dice",
                        modifier = Modifier.size(140.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = {
                                dieIndex1 = rollDie()
                                score1 += dieIndex1 + 1
                            },
                            enabled = !gameOver && !isHeld1
                        ) {
                            Text("Roll")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { isHeld1 = true },
                            enabled = !gameOver && !isHeld1 && score1 > 0
                        ) {
                            Text("Hold")
                        }
                    }
                    Text(text = "$score1", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Player 2
            Card(
                modifier = Modifier.weight(1f).padding(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Player 2", style = MaterialTheme.typography.titleLarge)
                    if (isHeld2) Text(text = "(Held)", color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = dieArray[dieIndex2]),
                        contentDescription = "Player 2 Dice",
                        modifier = Modifier.size(140.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = {
                                dieIndex2 = rollDie()
                                score2 += dieIndex2 + 1
                            },
                            enabled = !gameOver && !isHeld2
                        ) {
                            Text("Roll")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { isHeld2 = true },
                            enabled = !gameOver && !isHeld2 && score2 > 0
                        ) {
                            Text("Hold")
                        }
                    }
                    Text(text = "$score2", style = MaterialTheme.typography.bodyLarge)

                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = resultMessage,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // play again button
        if (gameOver&&!newGame) {
            if (roundsPlayed < maxRounds) {
                // Still more rounds left
                Button(onClick = {
                    score1 = 0
                    score2 = 0
                    dieIndex1 = 5
                    dieIndex2 = 5
                    isHeld1 = false
                    isHeld2 = false
                }
                )
                {
                    Text("Play Next Round")
                }
            }
        }
        // start new game button
        else if(gameOver){
            newGame = false
            totalScore1 = 0;
            totalScore2 = 0;
            roundsPlayed = 0;

            Button(onClick = {
                score1 = 0
                score2 = 0
                dieIndex1 = 5
                dieIndex2 = 5
                isHeld1 = false
                isHeld2 = false
            }
            )
            {
                Text("Start New Game")
            }
        }
    }
}

var totalScore1 = 0
var totalScore2 = 0
var roundsPlayed = 0
const val maxRounds = 3

fun checkRound(score1: Int, score2: Int, isHeld1: Boolean, isHeld2: Boolean): String {
    // Used AI to generate win conditions
    if (roundsPlayed >= maxRounds) return "Player 1: $totalScore1, Player 2: $totalScore2"

    var roundMessage = when {
        score1 > 21 && score2 > 21 -> "Both players busted! Round is a draw."
        score1 > 21 -> "Player 1 busted! Player 2 wins this round!"
        score2 > 21 -> "Player 2 busted! Player 1 wins this round!"
        score1 == 21 && score2 == 21 -> "Both hit 21! Round is a tie."
        score1 == 21 -> "Player 1 hits 21! Player 1 wins this round!"
        score2 == 21 -> "Player 2 hits 21! Player 2 wins this round!"
        isHeld1 && isHeld2 -> {
            when {
                score1 > score2 -> "Both held. Player 1 wins this round with higher score!"
                score2 > score1 -> "Both held. Player 2 wins this round with higher score!"
                else -> "Both held. Round is a tie!"
            }
        }
        else -> ""
    }

    // Update totals if round ended
    if (roundMessage.isNotEmpty()) {
        when {
            roundMessage.contains("Player 1 wins") -> totalScore1++
            roundMessage.contains("Player 2 wins") -> totalScore2++
        }
        roundsPlayed++
    }

    // If best of 3 game is over, add final winner message
    if (roundsPlayed >= maxRounds) {
        val finalWinner = when {
            totalScore1 > totalScore2 -> "Player 1 wins!"
            totalScore2 > totalScore1 -> "Player 2 wins!"
            else -> "The game ends in a tie!"
        }
        newGame =true
        roundMessage += "\n\n$finalWinner \n Final Score - Player 1: $totalScore1, Player 2: $totalScore2"
    }
    return roundMessage
}



fun rollDie():Int{
    val roll=(0..5).random()
    return roll
}

@Preview(showBackground = true)
@Composable
fun DicePreview() {
    Dice()
}

