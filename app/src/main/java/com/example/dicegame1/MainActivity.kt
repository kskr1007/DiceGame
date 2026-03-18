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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    var dieIndex1 by remember { mutableIntStateOf(5) }
    var dieIndex2 by remember { mutableIntStateOf(5) }
    var score1 by remember { mutableIntStateOf(0) }
    var score2 by remember { mutableIntStateOf(0) }


    val dieArray = arrayOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

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
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = dieArray[dieIndex1]),
                    contentDescription = "Player 1 Dice",
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick =
                        {
                            dieIndex1 = rollDie()
                            score1 += dieIndex1 + 1
                        }
                )
                {
                    Text("Roll P1")
                }
            }

            // Player 2
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Player 2", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = dieArray[dieIndex2]),
                    contentDescription = "Player 2 Dice",
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick =
                        {
                            dieIndex2 = rollDie()
                            score2 += dieIndex2 + 1
                        }
                )
                {
                    Text("Roll P2")
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        val resultMessage = "Roll Dice"
        Text(text = resultMessage, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Player 1: $score1", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Player 2: $score2", style = MaterialTheme.typography.bodyLarge)
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
