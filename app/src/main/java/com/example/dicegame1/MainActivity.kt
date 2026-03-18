package com.example.dicegame1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
    var dieIndex by remember { mutableIntStateOf(5) }
    var dieValue by remember { mutableIntStateOf(6) }
    val dieArray = arrayOf(
        R.drawable.die1,
        R.drawable.die2,
        R.drawable.die3,
        R.drawable.die4,
        R.drawable.die5,
        R.drawable.die6
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = dieArray[dieIndex]),
            contentDescription = "Dice image",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            dieIndex = rollDie()
            dieValue = dieIndex + 1
        }) {
            Text("Roll Die")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text("$dieValue")
    }
}

fun rollDie(): Int {
    return (0..5).random()
}

@Preview(showBackground = true)
@Composable
fun DicePreview() {
    Dice()
}
