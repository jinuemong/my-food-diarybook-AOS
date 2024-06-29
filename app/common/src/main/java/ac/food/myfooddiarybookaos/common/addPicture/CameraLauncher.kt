package ac.food.myfooddiarybookaos.common.addPicture

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect

@Composable
fun TakePhotoFromCameraLauncher(callback: (Bitmap?) -> Unit) {
    val takePhotoFromCameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { takePhoto ->
        callback(takePhoto)
    }
    LaunchedEffect(Unit) {
        takePhotoFromCameraLauncher.launch(null)
    }
}
