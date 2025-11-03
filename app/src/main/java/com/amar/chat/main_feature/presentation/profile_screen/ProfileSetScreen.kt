package com.amar.chat.main_feature.presentation.profile_screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.amar.chat.R
import com.amar.chat.main_feature.presentation._view_model.PhoneAuthViewModel
import com.amar.chat.main_feature.presentation.profile_screen.components.ProfileSetButton
import com.amar.chat.main_feature.presentation.profile_screen.components.ProfileTextField
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showSystemUi = true)
fun ProfileSetScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit = {}
) {

    val viewModel: PhoneAuthViewModel = koinViewModel()

    val name = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("") }
    val bitmapImage = remember { mutableStateOf<Bitmap?>(null) }
    val profileImageUri = remember { mutableStateOf<Uri?>(null) }

    val userId = Firebase.auth.currentUser?.uid ?: ""
    val phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: ""

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            profileImageUri.value = uri
            uri?.let {
                bitmapImage.value = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
            }
        },
    )


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(92.dp))

        Box(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
        ) {
            if (bitmapImage.value != null) {
                Image(
                    bitmap = bitmapImage.value!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else if (profileImageUri.value != null) {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri.value),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.img_male),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "phoneNumber",
        )

        Spacer(Modifier.height(16.dp))

        ProfileTextField(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth(),
            label = "Enter Name",
            text = name.value,
            onTextChange = {
                name.value = it
            }
        )

        Spacer(Modifier.height(6.dp))

        ProfileTextField(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth(),
            label = "Enter Status",
            text = status.value,
            onTextChange = {
                status.value = it
            }
        )

        Spacer(Modifier.height(16.dp))

        ProfileSetButton(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth()
        ) {
            viewModel.saveProfile(
                userId = userId,
                phoneNumber = phoneNumber,
                name = name.value,
                about = status.value,
                image = bitmapImage.value
            )
            onNavigate()
        }

    }

}