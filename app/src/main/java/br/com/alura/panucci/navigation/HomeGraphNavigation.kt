package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import br.com.alura.panucci.ui.components.BottomAppBarItem

internal const val homeRoute = "home"

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(startDestination = highlightsRoute, route = homeRoute) {
        highlightsNavScreen(navController)
        menuNavScreen(navController)
        drinksNavScreen(navController)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(homeRoute, navOptions)
}

fun NavController.navigateSingleTopWithPopUpTo(
    it: BottomAppBarItem,
) {
    val (route, navigate) = when (it) {
        BottomAppBarItem.DrinksList -> Pair(
            drinksRoute,
            ::navigateToDrinks
        )

        BottomAppBarItem.HighlightList -> Pair(
            highlightsRoute,
            ::navigateToHighlights
        )

        BottomAppBarItem.MenuList -> Pair(
            menuRoute,
            ::navigateToMenu
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }

    navigate(navOptions)
}