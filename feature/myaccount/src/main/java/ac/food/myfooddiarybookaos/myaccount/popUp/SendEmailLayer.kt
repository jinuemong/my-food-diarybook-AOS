package ac.food.myfooddiarybookaos.myaccount.popUp

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.ui.theme.EditTextBox
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SendEmailLayer(
    onClose: () -> Unit,
    onState: (String) -> Unit
) {
    val message = remember { mutableStateOf(TextFieldValue("")) }
    val isValid = remember { mutableStateOf(true) }
    val bottomNextColor = animateColorAsState(
        targetValue = if (isValid.value) {
            colorResource(id = R.color.main_color)
        } else {
            colorResource(id = R.color.main_color_30)
        }
    )

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "의견 입력",
                fontWeight = FontWeight.W700,
                fontFamily = robotoRegular,
                fontSize = 17.sp,
                lineHeight = 27.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.text_dark)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Spacer(modifier = Modifier.height(12.dp))

            EditTextBox(
                hintText = "의견 보내기",
                editText = message,
                strokeColor = isValid,
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Surface(
                    color = colorResource(id = R.color.popup_button_white),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onClose()
                        }
                ) {
                    Text(
                        text = "취소",
                        fontFamily = robotoRegular,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = colorResource(id = R.color._3A3A3D),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.5.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    color = bottomNextColor.value,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onClose()
                            onState(message.value.text)
                        }
                ) {
                    Text(
                        text = "확인",
                        fontFamily = robotoRegular,
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.5.dp),
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewSendEmailLayer() {
    SendEmailLayer(
        onClose = {}, onState = {}
    )
}
