package com.WordStellar.Blitz.presentation.navigation

import MainMenuScreen
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Savage.Trail.presentation.navigatio.Screen
import com.Savage.Trail.presentation.navigatio.navigatePopUpInclusive
import com.Savage.Trail.presentation.navigatio.navigateSingleTop
import com.WordStellar.Blitz.data.Prefs
import com.WordStellar.Blitz.data.SoundManager
import com.WordStellar.Blitz.presentation.view.GameScreen
import com.WordStellar.Blitz.presentation.view.GameWinScreen
import com.WordStellar.Blitz.presentation.view.OptionsScreen
import com.WordStellar.Blitz.presentation.view.SplashScreen
import com.WordStellar.Blitz.ui.theme.WordStellarBlitzTheme
import com.WordStellar.Blitz.ui.theme.myfont

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        Prefs.init(applicationContext)
        Prefs.init(applicationContext)
        SoundManager.init(applicationContext)
        SoundManager.playMusic()
        setContent {
            WordStellarBlitzTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navController::navigatePopUpInclusive)
                    }
                    composable(Screen.MainMenuScreen.route) {
                        MainMenuScreen(
                            navController::navigateSingleTop,
                            onNextt = {

                                navController.navigate("game/${Prefs.level}") {
                                    launchSingleTop = true
                                }
                            },
                        )
                    }
                    composable(Screen.OptionScreen.route) {
                        OptionsScreen(onBack = navController::popBackStack)
                    }
                    composable("game/{level}",
                        arguments = listOf(
                            navArgument("level") { type = NavType.IntType }
                        )
                    ) {
                        val result = it.arguments?.getBoolean("result") ?: false
                        val score = it.arguments?.getInt("score") ?: 0
                        val level = it.arguments?.getInt("level") ?: 1
                        GameScreen(
                            level = level,
                            onResult = { isSuccess: Boolean, score: Int ->
                                navController.navigate("result/$isSuccess/$score") {
                                    launchSingleTop = true
                                    popUpTo("game") { inclusive = true }
                                }
                            },
                            onRetry = {
                                SoundManager.playSound()
                                // Переход к игре с передачей результата и уровня
                                navController.navigate("game/${Prefs.level - 1}") {
                                    launchSingleTop = true
                                    popUpTo("result/$result/$score") { inclusive = true }
                                }
                            },

                        )
//                        {
//
//                            navController.navigateUp()
//                        }
                    }
                    composable(
                        "result/{result}/{score}",
                        arguments = listOf(
                            navArgument("result") { type = NavType.BoolType },
                            navArgument("score") { type = NavType.IntType }
                        )
                    ) {
                        // Извлечение аргументов из маршрута
                        val result = it.arguments?.getBoolean("result") ?: false
                        val score = it.arguments?.getInt("score") ?: 0

                        // Экран с результатом
                        GameWinScreen(
                            result = result,
                            score = score, // Добавьте параметр для отображения счета (если требуется)
                            onRetry = {
                                SoundManager.playSound()
                                // Переход к игре с передачей результата и уровня
                                navController.navigate("game/${if (!result) Prefs.level else Prefs.level - 1}") {
                                    launchSingleTop = true
                                    popUpTo("result/$result/$score") { inclusive = true }
                                }
                            },
                            onExit = {
                                SoundManager.playSound()
                                navController.navigateUp()
                            },
                            onNext = {
                                SoundManager.playSound()
                                // Переход на следующий уровень с передачей уровня
                                navController.navigate("game/${Prefs.level}") {
                                    launchSingleTop = true
                                    popUpTo("result/$result/$score") { inclusive = true }
                                }
                            }
                        )
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        SoundManager.resumeSound()
    }

    override fun onPause() {
        super.onPause()
        SoundManager.pauseSound()
        SoundManager.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.onDestroy()
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = Color.Unspecified,
    outlineColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    outlineDrawStyle: Stroke = Stroke(
        width = 20f,
    ),
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            modifier = Modifier.semantics { invisibleToUser() },
            color = outlineColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = myfont,
            letterSpacing = letterSpacing,
            textDecoration = null,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style.copy(
                shadow = null,
                drawStyle = outlineDrawStyle,
            ),
        )

        Text(
            text = text,
            color = fillColor,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = myfont,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}


