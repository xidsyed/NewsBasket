package com.loc.newsapp.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.newsapp.R
import com.loc.newsapp.presentation.Dimens
import com.loc.newsapp.ui.theme.NewsAppTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    text: String,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    readOnly: Boolean
) {

    val interactionSource = remember { MutableInteractionSource() }

    val isClicked by interactionSource.collectIsPressedAsState()

    LaunchedEffect(key1 = isClicked) { if (isClicked) onClick?.invoke() }

    Box(modifier = modifier) {
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .searchBar(),
            onValueChange = onValueChange,
            value = text,
            readOnly = readOnly,
            interactionSource = interactionSource,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.IconSize),
                    tint = colorResource(id = R.color.placeholder)
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                keyboardController?.hide()
            }),
            textStyle = MaterialTheme.typography.bodySmall,
            placeholder = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.placeholder)
                )
            },
        )
    }

}


fun Modifier.searchBar() = composed {
    if (!isSystemInDarkTheme()) this
        .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
    else this
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable()
fun PreviewSearchBar() {
    NewsAppTheme {
        SearchBar(
            text = "",
            modifier = Modifier.fillMaxWidth(),
            onSearch = { },
            onClick = { },
            onValueChange = {},
            readOnly = false
        )
    }
}