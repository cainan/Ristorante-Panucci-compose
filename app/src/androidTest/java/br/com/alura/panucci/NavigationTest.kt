package br.com.alura.panucci

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.panucci.navigation.checkoutRoute
import br.com.alura.panucci.navigation.drinksRoute
import br.com.alura.panucci.navigation.highlightsRoute
import br.com.alura.panucci.navigation.menuRoute
import br.com.alura.panucci.navigation.navigateToProductDetails
import br.com.alura.panucci.navigation.productDetailsRoute
import br.com.alura.panucci.navigation.productIdArgument
import br.com.alura.panucci.navigation.promoCodeParameter
import br.com.alura.panucci.sampledata.sampleProducts
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            PanucciApp(navController = navController)
        }
    }

    // Unit test
    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule
            .onNodeWithText("Destaques do dia")
            .assertIsDisplayed()
    }

    @Test
    fun appNavHost_verifyIfMenuScreenIsDisplayed() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Menu").performClick()

        composeTestRule.onAllNodesWithText("Menu").assertCountEquals(2)

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, menuRoute)
    }

    @Test
    fun appNavHost_verifyIfDrinksScreenIsDisplayed() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Bebidas").performClick()

        composeTestRule.onAllNodesWithText("Bebidas").assertCountEquals(2)

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, drinksRoute)
    }

    @Test
    fun appNavHost_verifyIfHighlightScreenIsDisplayed() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Destaques").performClick()

        composeTestRule.onNodeWithText("Destaques do dia").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, highlightsRoute)
    }

    @Test
    fun appNavHost_verifyIfProductDetailsScreenIsDisplayedFromHighlights() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Destaques").performClick()
        composeTestRule.onNodeWithText("Destaques do dia").assertIsDisplayed()

        composeTestRule.onAllNodesWithContentDescription("HighlightProductCard").onFirst()
            .performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}"
        )
    }

    @Test
    fun appNavHost_verifyIfProductDetailsScreenIsDisplayedFromHighlightsWithWrongProductId() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Destaques").performClick()
        composeTestRule.onNodeWithText("Destaques do dia").assertIsDisplayed()

        composeTestRule.onAllNodesWithContentDescription("HighlightProductCard").onFirst()
            .performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithText("Falha ao carregar o Produto")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithText("Falha ao carregar o Produto").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}"
        )
    }

    @Test
    fun appNavHost_verifyIfProductDetailsScreenIsDisplayedFromMenu() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Menu").performClick()
        composeTestRule.onAllNodesWithText("Menu").assertCountEquals(2)

        composeTestRule.onAllNodesWithContentDescription("MenuProductCard").onFirst()
            .performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithContentDescription("ProductDetailsScreen_Success")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithContentDescription("ProductDetailsScreen_Success")
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}"
        )
    }

    @Test
    fun appNavHost_verifyIfProductDetailsScreenIsDisplayedFromDrinks() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Bebidas").performClick()
        composeTestRule.onAllNodesWithText("Bebidas").assertCountEquals(2)

        composeTestRule.onAllNodesWithContentDescription("DrinkProductCard").onFirst()
            .performClick()

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithContentDescription("ProductDetailsScreen_Success")
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithContentDescription("ProductDetailsScreen_Success")
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}"
        )
    }

    @Test
    fun appNavHost_verifyIfCheckoutScreenIsDisplayedFromHighlights() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Destaques").performClick()
        composeTestRule.onNodeWithText("Destaques do dia").assertIsDisplayed()

        composeTestRule.onAllNodesWithText("Pedir").onFirst().performClick()

        composeTestRule.onNodeWithText("Pedido").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, checkoutRoute)
    }

    @Test
    fun appNavHost_verifyIfCheckoutScreenIsDisplayedFromMenu() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Menu").performClick()
        composeTestRule.onAllNodesWithText("Menu").assertCountEquals(2)

        composeTestRule.onNodeWithContentDescription("Floating Action Button to checkout")
            .performClick()

        composeTestRule.onNodeWithText("Pedido").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, checkoutRoute)
    }

    @Test
    fun appNavHost_verifyIfCheckoutScreenIsDisplayedFromDrinks() {
        composeTestRule.onRoot().printToLog("PannucciApp")
        composeTestRule.onNodeWithText("Bebidas").performClick()
        composeTestRule.onAllNodesWithText("Bebidas").assertCountEquals(2)

        composeTestRule.onNodeWithContentDescription("Floating Action Button to checkout")
            .performClick()

        composeTestRule.onNodeWithText("Pedido").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, checkoutRoute)
    }

    @Test
    fun appNavHost_verifyIfCheckoutScreenIsDisplayedFromProductDetails() {
        composeTestRule.onRoot().printToLog("PannucciApp")

        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.first().id)
        }

        composeTestRule.waitUntil(3000) {
            composeTestRule.onAllNodesWithText("Pedir").fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText("Pedir").performClick()

        composeTestRule.onNodeWithText("Pedido").assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, checkoutRoute)
    }

    @Test
    fun appNavHost_verifyIfSnackbarIsDisplayedWhenFinishTheOrder() {
        // TODO
    }

    @Test
    fun appNavHost_verifyIfFabIsDisplayedOnlyInMenuOrDrinksDestination() {
        // TODO
    }

    @Test
    fun appNavHost_verifyIfBottomAppBarIsDisplayedOnlyInHomeGraphNavigation() {
        // TODO
    }

    @Test
    fun appNavHost_verifyIfTopAppBarIsDisplayedOnlyInHomeGraphNavigation() {
        // TODO
    }

}