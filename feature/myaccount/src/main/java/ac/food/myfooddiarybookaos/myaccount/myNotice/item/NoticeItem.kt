package ac.food.myfooddiarybookaos.myaccount.myNotice.item

import ac.food.myfooddiarybookaos.core.data.R
import ac.food.myfooddiarybookaos.data.TextBox
import ac.food.myfooddiarybookaos.data.component.coloredInnerShadow
import ac.food.myfooddiarybookaos.data.robotoBold
import ac.food.myfooddiarybookaos.data.robotoRegular
import ac.food.myfooddiarybookaos.data.utils.scaledSp
import ac.food.myfooddiarybookaos.model.my.Notice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NoticeItem(notice: Notice) {

    val openState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                openState.value = !openState.value
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.wrapContentWidth()) {
                TextBox(
                    fontFamily = robotoBold,
                    fontWeight = 500,
                    text = notice.title,
                    fontSize = 16.scaledSp(),
                    color = colorResource(id = R.color.notice_text_color),
                    lineHeight = 24.scaledSp()
                )
                TextBox(
                    fontFamily = robotoRegular,
                    fontWeight = 500,
                    text = notice.noticeAt,
                    fontSize = 12.scaledSp(),
                    color = colorResource(id = R.color.notice_date_color),
                    lineHeight = 18.scaledSp()
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_down_small_line),
                    modifier = Modifier.rotate(if (openState.value) 180f else 0f),
                    contentDescription = null
                )
            }
        }

        if (openState.value) {
            Text(
                text = notice.content,
                color = colorResource(id = R.color.notice_date_color),
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.W500,
                fontFamily = robotoRegular,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 16.dp
                )
                    .align(Alignment.Start)
            )
        }

        Divider(
            modifier = Modifier
                .height(1.dp)
                .coloredInnerShadow(
                    color = colorResource(id = R.color.black_10),
                    offsetY = 1.dp,
                    blurRadius = 4.dp
                )
        )
    }


}

@Preview
@Composable
fun PreviewNotice() {
    NoticeItem(
        notice = Notice(
            0,
            noticeAt = "2023-09-06",
            title = "공지 테스트",
            content = "공지 테스트 입니다 확인하세요",
            available = true
        )
    )

}
