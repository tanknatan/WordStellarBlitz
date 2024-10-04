import android.app.Activity
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.Savage.Trail.presentation.navigatio.Screen
import com.WordStellar.Blitz.R
import com.WordStellar.Blitz.data.SoundManager
import com.WordStellar.Blitz.presentation.navigation.OutlinedText


@Composable
fun MainMenuScreen(
    onNext: (Screen) -> Unit,
    onNextt: () -> Unit,
) {
    val activity = LocalContext.current as? Activity
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Фоновое изображение
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedText(
                text = "WordStellar \n\n Blitz",
                outlineColor = Color(0xFF4296BD),
                fillColor = Color.White,
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.height(150.dp))
            OutlinedText(
                text = "MENU",
                outlineColor = Color(0xFF4296BD),
                fillColor = Color.White,
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .size(width = 270.dp, height = 170.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 250.dp, height = 150.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF78B7E7)),
                    contentAlignment = Alignment.Center
                ) {


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
                                    onNext(Screen.OptionScreen)
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
                                    imageVector = Icons.Default.Settings,
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
                                    onNextt()
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
                                    imageVector = Icons.Default.PlayArrow,
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
                                    activity?.finish()
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
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
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