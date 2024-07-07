package com.thezayin.lpg.presentation.admin.product_details.presentation.components

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thezayin.core.R
import com.thezayin.lpg.presentation.admin.product_details.presentation.AdminProDetailsViewModel

@Composable
fun ImageComponent(
    oldUri:Uri?,
    imageUri: Uri?,
    viewModel: AdminProDetailsViewModel?
) {
    val imageSelected = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel?.addImage(uri)
            imageSelected.value = true
        }
    )
    Card(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 25.dp),
        colors = CardDefaults.cardColors(
            colorResource(id = com.thezayin.lpg.R.color.semi_transparent)
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(150.dp))
                    .size(150.dp)
                    .background(colorResource(id = R.color.light_purple))
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = if (imageSelected.value) imageUri else oldUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
@Preview
fun ImageComponentPreview() {
    ImageComponent(null, null, null)
}