package br.com.alura.panucci.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.panucci.R
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.theme.PanucciTheme
import br.com.alura.panucci.ui.uistate.ProductDetailsUiState
import coil.compose.AsyncImage

@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsUiState,
    modifier: Modifier = Modifier,
    onOrderClick: () -> Unit = {},
    onRetrySearch: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    when (uiState) {
        ProductDetailsUiState.Failure -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Falha ao carregar o Produto")
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { onRetrySearch() }) {
                    Text(text = "Tentar Novamente")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { onBackClick() }) {
                    Text(text = "Voltar")
                }
            }
        }

        ProductDetailsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ProductDetailsUiState.Success -> {
            val product = uiState.product
            Column(
                modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .semantics { contentDescription = "ProductDetailsScreen_Success" }
            ) {
                product.image?.let {
                    AsyncImage(
                        model = product.image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        placeholder = painterResource(id = R.drawable.placeholder),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(product.name, fontSize = 24.sp)
                    Text(product.price.toPlainString(), fontSize = 18.sp)
                    Text(product.description)
                    Button(
                        onClick = { onOrderClick() },
                        Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Pedir")
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    PanucciTheme {
        Surface {
            ProductDetailsScreen(
                uiState = ProductDetailsUiState.Success(product = sampleProducts.first()),
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenLoading() {
    PanucciTheme {
        Surface {
            ProductDetailsScreen(
                uiState = ProductDetailsUiState.Loading,
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenWithFailure() {
    PanucciTheme {
        Surface {
            ProductDetailsScreen(
                uiState = ProductDetailsUiState.Failure,
            )
        }
    }
}