package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen


private const val productDetailsRoute = "productDetails"
private const val productIdArgument = "productId"
private const val promoCodeParameter = "promoCode"

fun NavGraphBuilder.productDetailsNavScreen(navController: NavHostController) {
    composable(
        "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCodeParameter}",
        arguments = listOf(navArgument("promoCode") { nullable = true })
    ) { navBackStack ->
        val productId = navBackStack.arguments?.getString(productIdArgument)
        val promoCode = navBackStack.arguments?.getString(promoCodeParameter)

        Log.i("MainActivity", "promoCode: $promoCode")
        Log.i("MainActivity", "productId: $productId")

        sampleProducts.find {
            it.id == productId
        }?.let { product ->

            if (promoCode == "ALURA") {
                Log.i("MainActivity", "Getting 10% discount")
            }

            ProductDetailsScreen(
                product = product,
                onNavigateToCheckout = {
                    navController.navigateToCheckout()
                })

        } ?: LaunchedEffect(Unit) {
            navController.navigateUp()
        }
    }
}

fun NavController.navigateToProductDetails(id: String) {
    navigate("$productDetailsRoute/$id")
}

fun NavController.navigateToProductDetails(id: String, promoCode2: String) {
    navigate("$productDetailsRoute/$id?promoCode=$promoCode2")
}