package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import br.com.alura.panucci.ui.components.BottomAppBarItem

@Composable
fun PanucciNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = highlightsRoute,
    ) {
        highlightsNavScreen(navController)
        menuNavScreen(navController)
        drinksNavScreen(navController)
        productDetailsNavScreen(navController)
        checkoutNavScreen(navController)
    }
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