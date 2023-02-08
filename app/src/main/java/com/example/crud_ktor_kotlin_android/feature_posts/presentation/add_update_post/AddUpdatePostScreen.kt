package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.crud_ktor_kotlin_android.core.util.Constants
import java.io.File
import com.example.crud_ktor_kotlin_android.R
import com.example.crud_ktor_kotlin_android.core.util.ValidationEvent
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post.components.ProfileImage
import com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post.components.TextFieldCustom
import kotlinx.coroutines.launch

@Composable
fun AddUpdatePostScreen(
    viewModel: AddUpdatePostViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    val state = viewModel.state.value
    var file by remember {
        mutableStateOf<File?>(null)
    }
    val imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    //launch gallery
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            scope.launch {
                uri?.let {
                    viewModel.onEvent(AddUpdatePostEvent.OnPostImageUrlChanged(uri))
                    file = File(Constants.getImageFilePath(context, uri)!!)
                    viewModel.onEvent(AddUpdatePostEvent.ImageChanged(file!!))
                }
            }
        }
    //launch  permission dialog
    val launche = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted:
            launcher.launch("image/*")
            Log.d("Permission", "PERMISSION GRANTED")
        } else {
            // Permission Denied:
            Log.d("permission", "PERMISSION DENIED")
        }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    Log.e("State", event.message)
                }
                is ValidationEvent.Error -> {
                    Log.e("State", event.error)
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            ) {

                val image = state.ImageUri?.let {
                    state.ImageUri
                } ?: R.drawable.error_loading

                Box {
                    ProfileImage(
                        image = image,
                        height = 120.dp,
                        width = 120.dp,
                        modifier = Modifier.border(
                            BorderStroke(
                                1.dp,
                                state.fileError
                            ),
                            RectangleShape
                        )
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.align(Alignment.BottomEnd),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bg_camera),
                        contentDescription = "Gallery Image",
                        alignment = Alignment.Center,
                        modifier = Modifier.clickable {

                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                -> {
                                    launcher.launch("image/*")

                                }
                                else -> {
                                    launche.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.camera_ic),
                        contentDescription = "",
                        tint = Color.White,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        TextFieldCustom(
            modifier = Modifier.align(Alignment.End),
            currentState = state.title,  //value
            currentStatePlaceHolder = "title",
            currentStateError = state.titleError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = VisualTransformation.None,
            onValueChange = {
                viewModel.onEvent(AddUpdatePostEvent.TitleChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 0.dp)) {
            Button(
                onClick = {
                    viewModel.onEvent(AddUpdatePostEvent.Submit)
                },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    1.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF000000),
                            Color(0xFFAAAAAA)
                        ),
                    )
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(
                    text = "Add",
                    modifier = Modifier.padding(
                        start = 10.dp,
                        bottom = 2.dp
                    )
                )
            }
        }
    }
}