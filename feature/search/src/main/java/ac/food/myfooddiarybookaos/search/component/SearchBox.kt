package ac.food.myfooddiarybookaos.search.component

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.search.state.SearchState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    searchQuery: MutableState<TextFieldValue>,
    searchState: MutableState<SearchState>,
    onQueryChange: () -> Unit,
    onBackStage: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // 검색 아이콘
    val leadingIconView = @Composable {
        IconButton(
            onClick = {}
        ) {
            if (searchState.value == SearchState.MAIN_SEARCH) {
                Box(
                    modifier = Modifier
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            onBackStage()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.main_left),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {}
        ) {
            if (searchQuery.value.text.isEmpty()) {
                Box(modifier = Modifier.size(30.dp))
            } else {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onBackStage()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close_24px_copy),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    }

    Surface( // 배경
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.black)
        ),
        color = colorResource(id = R.color.white),
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
    ) {

        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                onQueryChange()
            },
            singleLine = true,
            textStyle = TextStyle(
                fontWeight = FontWeight.W300,
                fontSize = 16.scaledSp(),
                fontFamily = robotoRegular,
                color = Color.Black,
                lineHeight = 16.scaledSp(),
            ),
            modifier = Modifier.fillMaxSize(),
            cursorBrush = SolidColor(Color.Black),
            interactionSource = interactionSource,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = searchQuery.value.text,
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    Text(
                        text = "식사일기 검색",
                        fontWeight = FontWeight.W300,
                        fontFamily = robotoRegular,
                        color = colorResource(id = R.color.calender_next_color),
                        fontSize = 16.scaledSp(),
                        lineHeight = 16.scaledSp(),
                    )
                },
                trailingIcon = trailingIconView,
                leadingIcon = leadingIconView,
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}
