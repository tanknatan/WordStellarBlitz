package com.Savage.Trail.presentation.navigatio

import androidx.navigation.NavBackStackEntry



sealed class Screen(
    val screenRoute: String,
) {
    open val route: String = screenRoute

    data object SplashScreen : Screen("splash_screen")
    data object MainMenuScreen : Screen("main_menu_screen")
    data object GameScreen : Screen("game_screen")
    data object OptionScreen : Screen("settings_screen")
    data object LevelScreen : Screen("level_screen")
    data object GameEndScreen : Screen("game_end_screen/{level}/{isVictory}")
    data  object PauseScreen : Screen("pause_screen")

}