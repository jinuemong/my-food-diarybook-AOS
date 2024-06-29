package ac.food.myfooddiarybookaos.login.popUp

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.robotoBold
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuccessFindEmailScreen(
    userEmail: String,
    onLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.background(
            color = Color.White,
            RoundedCornerShape(8.dp)
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "임시 비밀번호 발급 완료",
                fontSize = 16.scaledSp(),
                fontFamily = robotoBold,
                fontWeight = FontWeight.W700,
                color = colorResource(id = R.color.text_dark),
                modifier = Modifier.width(276.dp),
                textAlign = TextAlign.Center,
                lineHeight = 27.scaledSp()
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = buildAnnotatedString {

                    append("회원님의 이메일 주소\n")
                    withStyle(
                        SpanStyle(color = Color(0xFFF2994A))
                    ) {
                        append(userEmail)
                    }
                    append(
                        "으로\n" +
                                "임시 비밀번호를 보내 드렸습니다.\n" +
                                "\n" +
                                "임시 비밀번호로 로그인 후\n" +
                                "비밀번호를 변경해 주세요."
                    )
                },
                fontSize = 18.scaledSp(),
                textAlign = TextAlign.Center,
                fontFamily = robotoRegular,
                fontWeight = FontWeight.W500,
                color = colorResource(id = R.color.text_dark_pop),
                modifier = Modifier.width(276.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))


            Box(
                modifier = Modifier
                    .width(276.dp)
                    .background(
                        color = colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        onLogin()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "로그인 하기",
                    fontWeight = FontWeight.W700,
                    fontFamily = robotoBold,
                    fontSize = 17.scaledSp(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(
                            vertical = 10.5.dp,
                            horizontal = 12.dp
                        )
                )
            }

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun ViewEmailSuccess() {
    SuccessFindEmailScreen("dinner@gmail.com", onLogin = {})
}
