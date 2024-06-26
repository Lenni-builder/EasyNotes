package com.kin.easynotes.presentation.screens.edit.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.kin.easynotes.presentation.screens.edit.model.EditViewModel

@Composable
fun TextFormattingToolbar(viewModel: EditViewModel) {
    val context = LocalContext.current
    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = { viewModel.updateNoteNameDescription(viewModel.noteDescriptionState.value + "\n- " ) }) {
            Icon(Icons.AutoMirrored.Rounded.FormatListBulleted, contentDescription = "bullet")
        }
        IconButton(onClick = { viewModel.updateNoteNameDescription(viewModel.noteDescriptionState.value + "\n```\n```") }) {
            Icon(Icons.Rounded.FormatQuote, contentDescription = "Quote")
        }
        IconButton(onClick = { viewModel.updateNoteNameDescription(viewModel.noteDescriptionState.value + "\n#") }) {
            Icon(Icons.Rounded.FormatSize, contentDescription = "Size")
        }
        ImagePicker { photoUri ->
            context.contentResolver.takePersistableUriPermission(photoUri, flag)
            viewModel.updateNoteNameDescription(viewModel.noteDescriptionState.value + "!($photoUri)\n")
        }
    }
}

@Composable
fun ImagePicker(onImageSelected: (Uri) -> Unit) {
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        photoUri = uri
        if (uri != null) {
            onImageSelected(uri)
        }
    }
    IconButton(onClick = { launcher.launch("image/*") }) {
        Icon(Icons.Rounded.Image, contentDescription = "Size")
    }
}
