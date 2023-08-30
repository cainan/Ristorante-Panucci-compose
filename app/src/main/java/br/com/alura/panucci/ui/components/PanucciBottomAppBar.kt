package br.com.alura.panucci.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.com.alura.panucci.ui.theme.PanucciTheme

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
) {
    object HighlightList : BottomAppBarItem(
        label = "Destaques",
        icon = Icons.Filled.AutoAwesome,
    )

    object MenuList : BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
    )

    object DrinksList : BottomAppBarItem(
        label = "Bebidas",
        icon = Icons.Outlined.LocalBar,
    )
}

@Composable
fun PanucciBottomAppBar(
    item: BottomAppBarItem,
    modifier: Modifier = Modifier,
    items: List<BottomAppBarItem> = emptyList(),
    onItemChange: (BottomAppBarItem) -> Unit = {},
) {
    NavigationBar(modifier) {
        items.forEach {
            val label = it.label
            val icon = it.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = item.label == label,
                onClick = {
                    onItemChange(it)
                }
            )
        }
    }
}

val bottomAppBarItems = listOf(
    BottomAppBarItem.HighlightList,
    BottomAppBarItem.MenuList,
    BottomAppBarItem.DrinksList
)

@Preview
@Composable
fun PanucciBottomAppBarPreview() {
    PanucciTheme {
        PanucciBottomAppBar(
            item = BottomAppBarItem.MenuList,
            items = bottomAppBarItems
        )
    }
}