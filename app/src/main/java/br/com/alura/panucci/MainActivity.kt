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
import br.com.alura.panucci.sampledata.bottomAppBarItems
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
                                it.route == destination.route
                            }
                        } ?: bottomAppBarItems.first()

                        mutableStateOf(item)
                    }
                    PanucciApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = {
                            navController.navigate(it.route) {
                                launchSingleTop = true
                                popUpTo(it.route)
                            }
                        },
                        onFabClick = {
                            navController.navigate("checkout")
                        },
                        content = {
                            NavHost(
                                navController = navController,
                                startDestination = "highlights",
                            ) {
                                composable("highlights") {
                                    HighlightsListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = {
                                            navController.navigate("productDetails")
                                        },
                                        onNavigateToCheckout = {
                                            navController.navigate("checkout")
                                        }
                                    )
                                }
                                composable("menu") {
                                    MenuListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = {
                                            navController.navigate("productDetails")
                                        })
                                }
                                composable("drinks") {
                                    DrinksListScreen(
                                        products = sampleProducts,
                                        onNavigateToDetails = {
                                            navController.navigate("productDetails")
                                        }
                                    )
                                }
                                composable("productDetails") {
                                    ProductDetailsScreen(
                                        product = sampleProducts.random(),
                                        onNavigateToCheckout = {
                                            navController.navigate("checkout")
                                        })
                                }
                                composable("checkout") {
                                    CheckoutScreen(products = sampleProducts)
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
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Ristorante Panucci")
                },
            )
        },
        bottomBar = {
            PanucciBottomAppBar(
                item = bottomAppBarItemSelected,
                items = bottomAppBarItems,
                onItemChange = onBottomAppBarItemSelectedChange,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick
            ) {
                Icon(
                    Icons.Filled.PointOfSale,
                    contentDescription = null
                )
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