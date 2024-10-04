package com.Savage.Trail.presentation.navigatio

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder



fun NavHostController.navigateSingleTop(
    screen: Screen,
    vararg args: Any,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    val routeBuilder = StringBuilder(screen.screenRoute)

    val appendArg: (Any) -> Unit = { arg: Any ->
        routeBuilder.append("/$arg")
    }

    args.forEach(appendArg)

    navigate(routeBuilder.toString()) {
        builder(this)
        launchSingleTop = true
    }
}

fun NavHostController.navigatePopUpInclusive(screen: Screen) {
    val currentRoute = currentBackStackEntry?.destination?.route ?: return
    navigate(screen.route) {
        popUpTo(currentRoute) {
            inclusive = true

        }
    }
}

