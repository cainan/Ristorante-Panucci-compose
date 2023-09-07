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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.panucci.navigation.PanucciNavHost
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.navigateSingleTopWithPopUpTo
import br.com.alura.panucci.navigation.navigateToCheckout
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.components.bottomAppBarItems
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanucciApp(rememberNavController())
                }
            }
        }
    }

}

@Composable
public fun PanucciApp(navController: NavHostController = rememberNavController()) {

    val backStackEntryState by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntryState?.destination

    val orderDoneMessage =
        backStackEntryState?.savedStateHandle?.getStateFlow<String?>("order_done", null)
            ?.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    orderDoneMessage?.value?.let { message ->
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Log.i("MainActivity", "orderStatus: ${orderDoneMessage?.value}")

    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
            val map = navController.backQueue.map { it.destination.route }
            Log.i("MainActivity", "onCreate: $map")
        }
    }

    val currentRoute = currentDestination?.route

    var selectedItem by remember(currentDestination) {


        val item = when (currentRoute) {
            highlightsRoute -> BottomAppBarItem.HighlightList
            menuRoute -> BottomAppBarItem.MenuList
            drinksRoute -> BottomAppBarItem.DrinksList
            else -> BottomAppBarItem.HighlightList
        }

        mutableStateOf(item)
    }

    val isShowAppBars = when (currentRoute) {
        highlightsRoute, menuRoute, drinksRoute -> true
        else -> false
    }

    val isShowFab = when (currentRoute) {
        menuRoute, drinksRoute -> true
        else -> false
    }

    PanucciApp(
        snackbarHostState = snackbarHostState,
        bottomAppBarItemSelected = selectedItem,
        onBottomAppBarItemSelectedChange = {
            navController.navigateSingleTopWithPopUpTo(it)
        },
        onFabClick = {
            navController.navigateToCheckout()
        },
        isShowTopAppBar = isShowAppBars,
        isShowBottomAppBar = isShowAppBars,
        isShowFab = isShowFab,
        content = {
            PanucciNavHost(navController = navController)
        }
    )
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
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    content: @Composable () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(8.dp),
            ) {
                Snackbar {
                    Text(text = it.visuals.message)
                }
            }
        },
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
            PanucciApp(
                content = {},
                isShowBottomAppBar = true,
                isShowFab = true,
                isShowTopAppBar = true
            )
        }
    }
}