package br.com.alura.panucci.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import br.com.alura.panucci.ui.components.BottomAppBarItem

sealed class AppDestinations(val route: String) {
    object Highlights : AppDestinations("highlights")
    object Menu : AppDestinations("menu")
    object Drinks : AppDestinations("drinks")
    object ProductDetails : AppDestinations("productDetails")
    object Checkout : AppDestinations("checkout")
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Destaques",
        icon = Icons.Filled.AutoAwesome,
        destination = AppDestinations.Highlights
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination = AppDestinations.Menu
    ),
    BottomAppBarItem(
        label = "Bebidas",
        icon = Icons.Outlined.LocalBar,
        destination = AppDestinations.Drinks
    ),
)