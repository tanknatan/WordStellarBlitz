package com.WordStellar.Blitz.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Savage.Trail.presentation.navigatio.Screen
import com.WordStellar.Blitz.R
import com.WordStellar.Blitz.data.Prefs
import com.WordStellar.Blitz.data.SoundManager
import com.WordStellar.Blitz.presentation.navigation.OutlinedText
import com.WordStellar.Blitz.ui.theme.myfont
import java.util.Locale


@Composable
fun PauseScreen(

    result: Boolean = true,
    onRetry: () -> Unit,
    onOption: () -> Unit,
    onExit: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.wl), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 280.dp, height = 200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF78B7E7)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedText(
                        text = "PAUSE",
                        fontSize = 64.sp, // Увеличен размер текста
                        fillColor = Color.White,
                        outlineColor = Color(0xFF1E54BD)
                    )


                    // Back Button
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Кнопка настроек
                        Box(
                            modifier = Modifier
                                .size(60.dp)  // Размер кнопки
                                .clip(CircleShape)  // Округлая форма
                                .background(Color.White)
                                .clickable {
                                    SoundManager.playSound()
                                    onRetry()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2176C7)), // Голубой фон внутри
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Settings",
                                    tint = Color.White // Цвет иконки
                                )
                            }
                        }

                        // Кнопка воспроизведения
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    SoundManager.playSound()
                                    onOption()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2176C7)),  // Голубой фон внутри
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Play",
                                    tint = Color.White,  // Цвет иконки
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }

                        // Кнопка закрытия
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable {
                                    SoundManager.playSound()
                                    onExit()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2176C7)),  // Голубой фон внутри
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Close",
                                    tint = Color.White // Цвет иконки
                                )
                            }
                        }
                    }
                }
            }
        }

        // Next Button at the bottom center of the screen


    }

}

