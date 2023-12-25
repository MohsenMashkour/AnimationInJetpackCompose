package com.mkrdeveloper.animationinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkrdeveloper.animationinjetpackcompose.ui.theme.AnimationInJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationInJetpackComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //VisibilityEx()
                    //ShapeEx()
                    //TransitionEx()
                    //InfiniteEx()
                    AnimatedContentEx()
                }
            }
        }
    }
}

@Composable
fun VisibilityEx() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        var toggle by remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            toggle = !toggle
        }) {
            Text(text = "Start Animation", fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedVisibility(
            visible = toggle, exit =
            slideOutHorizontally() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Gray)
            )
        }


    }
}

@Composable
fun ShapeEx() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        var isRound by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            isRound = !isRound
        }) {
            Text(text = "Start Animation", fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        val cornerRadius by animateIntAsState(
            targetValue = if (isRound) 85 else 45, label = "",
            // animationSpec = tween(3000)
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(cornerRadius.dp))
                .background(Color.Gray)
        )
    }
}

@Composable
fun TransitionEx() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        var isRound by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            isRound = !isRound
        }) {
            Text(text = "Start Animation", fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        val transition = updateTransition(targetState = isRound, label = "")
        val cornerRadius by transition.animateInt(
            transitionSpec = { tween(3000) },
            label = "",
            targetValueByState = { isRound ->
                if (isRound) 100 else 0

            }
        )
        val color by transition.animateColor(
            transitionSpec = { tween(5000) },
            label = "",
            targetValueByState = { if (it) Color.Yellow else Color.Gray }
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(color)
        )

    }
}

@Composable
fun InfiniteEx() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val transition = rememberInfiniteTransition(label = "")
        val color by transition.animateColor(
            initialValue = Color.Gray,
            targetValue = Color.Yellow,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(color)
        )
    }
}

@Composable
fun AnimatedContentEx() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        var toggle by remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            toggle = !toggle
        }) {
            Text(text = "Start Animation", fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedContent(targetState = toggle, modifier = Modifier
            .fillMaxSize()
            .weight(1f), label = "",
            content = { toggle ->
                if (toggle) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Yellow)
                    )
                }

            },
            /* transitionSpec = {
                 fadeIn() togetherWith fadeOut()
             }*/
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { fullOut ->
                        if (toggle) -fullOut else fullOut
                    }
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { fullIn ->
                        if (toggle) fullIn else -fullIn
                    }
                )

            }
        )
    }
}