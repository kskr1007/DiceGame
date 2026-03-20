package com.example.dicegame1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

@Composable
fun Dice() {
    // series scores
    var totalScore1 by remember { mutableIntStateOf(0) }
    var totalScore2 by remember { mutableIntStateOf(0) }

    // round  variables
    var dieIndex1 by remember { mutableIntStateOf(5) }
    var dieIndex2 by remember { mutableIntStateOf(5) }
    var score1 by remember { mutableIntStateOf(0) }
    var score2 by remember { mutableIntStateOf(0) }
    var isHeld1 by remember { mutableStateOf(false) }
    var isHeld2 by remember { mutableStateOf(false) }

    // track if a point was awarded this round
    var roundPointAwarded by remember { mutableStateOf(false) }

    // dice images
    val dieArray = arrayOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

    // logic for checking if round is over
    val roundOver = score1 >= 21 || score2 >= 21 || (isHeld1 && isHeld2)
    // logic for checking if game is over
    val gameEnded = totalScore1 >= 3 || totalScore2 >= 3

    // update series score once per round
    // **Utilized AI to generate scoring using blackjack rules**
    if (roundOver && !roundPointAwarded && !gameEnded) {
        if (score1 <= 21 && (score2 > 21 || score1 > score2)) {
            totalScore1++
        } else if (score2 <= 21 && (score1 > 21 || score2 > score1)) {
            totalScore2++
        }
        roundPointAwarded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFADD8E6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // **Utilized AI to create a professional looking scoreboard**
        Text(
            text = "DICE BLACKJACK",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 48.dp) // Extra top padding for status bar
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FIRST TO 3 WINS",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PLAYER 1", style = MaterialTheme.typography.labelSmall)
                        Text("$totalScore1", style = MaterialTheme.typography.displayMedium)
                    }

                    Box(modifier = Modifier.width(2.dp).height(40.dp).background(MaterialTheme.colorScheme.outlineVariant))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("PLAYER 2", style = MaterialTheme.typography.labelSmall)
                        Text("$totalScore2", style = MaterialTheme.typography.displayMedium)
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
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
                            enabled = !roundOver && !isHeld1
                        ) {
                            Text("Roll")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { isHeld1 = true },
                            enabled = !roundOver && !isHeld1 && score1 > 0
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
                            enabled = !roundOver && !isHeld2
                        ) {
                            Text("Roll")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { isHeld2 = true },
                            enabled = !roundOver && !isHeld2 && score2 > 0
                        ) {
                            Text("Hold")
                        }
                    }
                    Text(text = "$score2", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        // result buttons
        if (roundOver) {
            Spacer(modifier = Modifier.height(20.dp))
            if (gameEnded) {
                Text(text = "Game Over! ${if(totalScore1 >= 3) "Player 1" else "Player 2"} Wins Series!", color = Color.DarkGray)
                Button(onClick = {
                    totalScore1 = 0
                    totalScore2 = 0
                    score1 = 0
                    score2 = 0
                    isHeld1 = false
                    isHeld2 = false
                    roundPointAwarded = false
                })
                {
                    Text("Restart Series")
                }
            } else {
                Button(onClick = {
                    // reset series scores
                    score1 = 0
                    score2 = 0
                    isHeld1 = false
                    isHeld2 = false
                    roundPointAwarded = false
                    dieIndex1 = 5
                    dieIndex2 = 5
                })
                {
                    Text("Next Round")
                }
            }
        }
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

