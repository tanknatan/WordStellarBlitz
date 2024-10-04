package com.WordStellar.Blitz.presentation.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.WordStellar.Blitz.R

import com.WordStellar.Blitz.data.Prefs
import com.WordStellar.Blitz.data.SoundManager
import com.WordStellar.Blitz.presentation.navigation.OutlinedText


@Composable
fun OptionsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var musicVolume by remember { mutableFloatStateOf(Prefs.music) }
    var soundVolume by remember { mutableFloatStateOf(Prefs.sound) }

    val gradient1 = Brush.horizontalGradient(
        colors = listOf(Color(0xFF2176C7), Color(0xFF2176C7)) // черный и фиолетовый цвет
    )
    val gradient2 = Brush.horizontalGradient(
        colors = listOf(Color(0xFF2176C7), Color(0xFF2176C7)) // черный и фиолетовый цвет
    )

    // Обновление значений громкости при изменении
    LaunchedEffect(musicVolume) {
        Prefs.music = musicVolume
        SoundManager.setVolumeMusic()
    }

    LaunchedEffect(soundVolume) {
        Prefs.sound = soundVolume
        SoundManager.setVolumeSound()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Фоновое изображение
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Row в верхней части экрана


        // Основной контейнер с фоном main_rec для заголовка и слайдеров
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 100.dp
                ), // Сдвигаем вниз, чтобы оставить место для Row
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 340.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 280.dp, height = 320.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF78B7E7)),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedText(
                            text = "OPTIONS",
                            outlineColor = Color(0xFF2176C7),
                            fillColor = Color.White,
                            fontSize = 60.sp
                        )
                        // Заголовок "Settings" на фоне main_rec
                        Spacer(modifier = Modifier.height(5.dp))
                        // Sound Volume настройка с текстом слева от слайдера
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp, vertical = 8.dp)
                        ) {
                            OutlinedText(
                                text = "Sound",
                                outlineColor = Color.Gray,
                                fillColor = Color.White,
                                fontSize = 24.sp,
                                modifier = Modifier.weight(1f) // Равное распределение
                            )
                            Slider(
                                value = soundVolume,
                                onValueChange = { newValue ->
                                    soundVolume = newValue
                                    sharedPreferences.edit().putFloat("soundVolume", newValue)
                                        .apply()
                                },
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.White,
                                    activeTrackColor = Color.Transparent,
                                    inactiveTrackColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(2f)
                                    .drawBehind {
                                        val trackWidth = size.width
                                        val trackHeight = size.height / 4f
                                        val topLeft = Offset(0f, center.y - trackHeight / 2f)
                                        val size = Size(trackWidth, trackHeight)
                                        drawRoundRect(
                                            brush = gradient1,
                                            topLeft = topLeft,
                                            size = size,
                                            cornerRadius = CornerRadius(10f, 10f)
                                        )
                                    },
                                valueRange = 0f..1f,
                                steps = 10
                            )
                        }

                        // Music Volume настройка с текстом слева от слайдера
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp, vertical = 8.dp)
                        ) {
                            OutlinedText(
                                text = "Music",
                                outlineColor = Color.Gray,
                                fillColor = Color.White,
                                fontSize = 24.sp,
                                modifier = Modifier.weight(1f) // Равное распределение
                            )
                            Slider(
                                value = musicVolume,
                                onValueChange = { newValue ->
                                    musicVolume = newValue
                                    sharedPreferences.edit().putFloat("musicVolume", newValue)
                                        .apply()
                                },
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.White,
                                    activeTrackColor = Color.Transparent,
                                    inactiveTrackColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(2f)
                                    .drawBehind {
                                        val trackWidth = size.width
                                        val trackHeight = size.height / 4f
                                        val topLeft = Offset(0f, center.y - trackHeight / 2f)
                                        val size = Size(trackWidth, trackHeight)
                                        drawRoundRect(
                                            brush = gradient2,
                                            topLeft = topLeft,
                                            size = size,
                                            cornerRadius = CornerRadius(10f, 10f)
                                        )
                                    },
                                valueRange = 0f..1f,
                                steps = 10
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .offset(y = 20.dp)
                        ) {
                            // Кнопка "Дом"
                            Box(
                                modifier = Modifier
                                    .size(60.dp)  // Размер внешнего круга
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .clickable { SoundManager.playSound()
                                        onBack() },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2176C7)), // Голубой фон
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home",
                                        tint = Color.White // Цвет иконки
                                    )
                                }
                            }

                            // Кнопка "Галочка"
                            Box(
                                modifier = Modifier
                                    .size(60.dp)  // Размер внешнего круга
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .clickable { SoundManager.playSound()
                                        onBack() },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2176C7)), // Голубой фон
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Check",
                                        tint = Color.White // Цвет иконки
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
