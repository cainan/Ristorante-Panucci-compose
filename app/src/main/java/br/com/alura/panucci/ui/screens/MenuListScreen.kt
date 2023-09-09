package br.com.alura.panucci.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.panucci.model.Product
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.components.MenuProductCard
import br.com.alura.panucci.ui.theme.PanucciTheme
import br.com.alura.panucci.ui.theme.caveatFont
import br.com.alura.panucci.ui.uistate.MenuListUiState

@Composable
fun MenuListScreen(
    modifier: Modifier = Modifier,
    title: String = "Menu",
    uiState: MenuListUiState = MenuListUiState(),
    onProductClick: (Product) -> Unit = {},
) {
    val products = uiState.products
    Column(
        modifier.fillMaxSize()
    ) {
        Surface {
            Text(
                text = title,
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontFamily = caveatFont,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { p ->
                MenuProductCard(
                    product = p,
                    Modifier
                        .semantics { contentDescription = "MenuProductCard" }
                        .clickable { onProductClick(p) }
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuListScreenPreview() {
    PanucciTheme {
        Surface {
            MenuListScreen(
                uiState = MenuListUiState(products = sampleProducts),
            )
        }
    }
}