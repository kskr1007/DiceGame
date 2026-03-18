package com.example.dicegame1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
    val resultMessage = checkWinner(score1, score2, isHeld1, isHeld2)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            // Player 1
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
            }

            // Player 2
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
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = resultMessage,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Player 1: $score1", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Player 2: $score2", style = MaterialTheme.typography.bodyLarge)

        // play again button
        if (gameOver) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                score1 = 0
                score2 = 0
                dieIndex1 = 5
                dieIndex2 = 5
                isHeld1 = false
                isHeld2 = false
            }) {
                Text("Play Again")
            }
        }
    }
}

fun checkWinner(score1: Int, score2: Int, isHeld1: Boolean, isHeld2: Boolean): String {
    // Used AI to create blackjack win conditions
    // evaluate busts
    return when {
        score1 > 21 && score2 > 21 -> "Both Busted! Draw!"
        score1 > 21 -> "Player 1 Busted! Player 2 Wins!"
        score2 > 21 -> "Player 2 Busted! Player 1 Wins!"
        score1 == 21 && score2 == 21 -> "Tie!"
        score1 == 21 -> "Player 1 hits 21! Player 1 Wins!"
        score2 == 21 -> "Player 2 hits 21! Player 2 Wins!"
        // if both players hold then evaluate scores
        isHeld1 && isHeld2 -> {
            when {
                score1 > score2 -> "Player 1 Wins by Score!"
                score2 > score1 -> "Player 2 Wins by Score!"
                else -> "It's a Tie!"
            }
        }
        // continue playing
        else -> "Blackjack"
    }
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
