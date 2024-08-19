package com.thezayin.usercart.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thezayin.core.R
import com.thezayin.entities.CartModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CartProductInfo(
    product: CartModel?,
    onIncrement: (CartModel) -> Unit = {},
    onDecrement: (CartModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(top = 5.sdp, bottom = 5.sdp)
            .clip(shape = RoundedCornerShape(10.sdp))
            .background(color = colorResource(id = R.color.semi_transparent))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.sdp)
                .fillMaxWidth()
                .heightIn(min = 140.sdp, max = 400.sdp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = product?.imageUri ?: R.drawable.ic_mail,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.sdp))
                        .size(60.sdp)
                        .background(color = Color.White),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    Text(
                        text = product?.name ?: "Product Name",
                        color = colorResource(id = R.color.black),
                        fontSize = 12.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    )
                    Text(
                        text = product?.description ?: "Product Description",
                        color = colorResource(id = R.color.black),
                        fontSize = 9.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_regular)),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.sdp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable {
                                onDecrement(product!!)
                            }
                    )
                    Text(
                        text = " ${product?.quantity} ",
                        color = colorResource(id = R.color.black),
                        fontSize = 12.ssp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable {
                                onIncrement(product!!)
                            })
                }
            }
        }
    }
}

@Composable
@Preview
fun CartProductInfoPreview() {
    CartProductInfo(product = null)
}