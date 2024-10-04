package com.WordStellar.Blitz.presentation.view


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Savage.Trail.presentation.navigatio.Screen
import com.WordStellar.Blitz.R
import com.WordStellar.Blitz.presentation.navigation.OutlinedText


import kotlinx.coroutines.delay


@Composable
fun SplashScreen(  onNext: (Screen) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(Unit) {
        delay(1000) // Short delay before transitioning
        onNext(Screen.MainMenuScreen)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop, // To make the image fill the entire screen
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.load),
                contentDescription = "Loading",
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer(rotationZ = rotation),
                contentScale = ContentScale.Fit
            )
        }

        // Column removed from center and placed at bottom center
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Add padding if necessary
            contentAlignment = Alignment.BottomCenter // Changed to BottomCenter
        ) {
            OutlinedText(
                text = "Loading...",
                outlineColor = Color(0xFF59B6D3),
                fillColor = Color.White,
                fontSize = 50.sp,
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
}


