package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.panucci.navigation.AppDestinations
import br.com.alura.panucci.navigation.bottomAppBarItems
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val backStackEntryState by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntryState?.destination

            LaunchedEffect(Unit) {
                navController.addOnDestinationChangedListener { _, _, _ ->
                    val map = navController.backQueue.map { it.destination.route }
                    Log.i("MainActivity", "onCreate: $map")
                }
            }

            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedItem by remember(currentDestination) {

                        val item = currentDestination?.let { destination ->
                            bottomAppBarItems.find {
                                it.destination.route == destination.route
                            }
                        } ?: bottomAppBarItems.first()

                        mutableStateOf(item)
                    }

                    val isShowAppBars = currentDestination?.let { destination ->
                        bottomAppBarItems.find {
                            it.destination.route == destination.route
                        }
                    } != null

                    val isShowFab = when (currentDestination?.route) {
                        AppDestinations.Menu.route, AppDestinations.Drinks.route -> true
                        else -> false
                    }

                    PanucciApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = {
                            navController.navigate(it.destination.route) {
                                launchSingleTop = true
                                popUpTo(it.destination.route)
                            }
                        },
                        onFabClick = {
                            navController.navigate(AppDestinations.Checkout.route)
                        },
                        isShowTopAppBar = isShowAppBars,
                        isShowBottomAppBar = isShowAppBars,
                        isShowFab = isShowFab,
                        content = {
                            NavHost(
                                navController = navController,
                                startDestination = AppDestinations.Highlights.route,
                            ) {
                                composable(AppDestinations.Highlights.route) {
                                    HighlightsListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = { product ->
                                            val discount = "ALURA"
                                            navController.navigate("${AppDestinations.ProductDetails.route}/${product.id}?promoCode=$discount")
                                        },
                                        onNavigateToCheckout = {
                                            navController.navigate(AppDestinations.Checkout.route)
                                        }
                                    )
                                }
                                composable(AppDestinations.Menu.route) {
                                    MenuListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = { product ->
                                            navController.navigate("${AppDestinations.ProductDetails.route}/${product.id}")
                                        })
                                }
                                composable(AppDestinations.Drinks.route) {
                                    DrinksListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = { product ->
                                            navController.navigate("${AppDestinations.ProductDetails.route}/${product.id}")
                                        }
                                    )
                                }
                                composable(
                                    "${AppDestinations.ProductDetails.route}/{productId}?promoCode={promoCode}",
                                    arguments = listOf(navArgument("promoCode") { nullable = true })
                                ) { navBackStack ->
                                    val productId = navBackStack.arguments?.getString("productId")
                                    val promoCode = navBackStack.arguments?.getString("promoCode")


                                    Log.i("MainActivity", "onCreate: productId: $productId")
                                    sampleProducts.find {
                                        it.id == productId
                                    }?.let { product ->

                                        if (promoCode == "ALURA") {
                                            Log.i("MainActivity", "Getting 10% discount")
                                        }

                                        Log.i("MainActivity", "onCreate: product: $product")
                                        ProductDetailsScreen(
                                            product = product,
                                            onNavigateToCheckout = {
                                                navController.navigate(AppDestinations.Checkout.route)
                                            })
                                    } ?: LaunchedEffect(Unit) {
                                        navController.navigateUp()
                                    }
                                }
                                composable(AppDestinations.Checkout.route) {
                                    CheckoutScreen(
                                        products = sampleProducts,
                                        onPopBackStack = {
                                            navController.navigateUp()
                                        },
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    isShowTopAppBar: Boolean = false,
    isShowBottomAppBar: Boolean = false,
    isShowFab: Boolean = false,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if (isShowTopAppBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                )
            }
        },
        bottomBar = {
            if (isShowBottomAppBar) {
                PanucciBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }
        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    PanucciTheme {
        Surface {
            PanucciApp {}
        }
    }
}