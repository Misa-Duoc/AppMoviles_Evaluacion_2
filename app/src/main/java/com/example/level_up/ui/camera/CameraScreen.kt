package com.example.level_up.ui.camera

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.level_up.utils.QrScanner
import com.example.level_up.utils.CameraPermissionHelper

@Composable
fun CameraScreen() {
    val context = LocalContext.current

    // estado del permiso
    var hasCameraPermission by remember {
        mutableStateOf(CameraPermissionHelper.hasCameraPermission(context))
    }

    // launcher para pedir el permiso
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
        if (!granted) {
            Toast.makeText(context, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show()
        }
    }

    // estado del resultado del QR
    var qrContent by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!hasCameraPermission) {
            Text(
                text = "Permiso de cámara requerido",
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { requestPermissionLauncher.launch(Manifest.permission.CAMERA) }
            ) {
                Text("Conceder permiso")
            }
        } else {
            // si hay permiso, mostramos el escáner que ya tienes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                QrScanner(
                    onQrCodeScanned = { content ->
                        qrContent = content
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text("Escanea un código QR dentro del recuadro")

            qrContent?.let {
                Text(text = "Leído: $it")
            }
        }
    }
}
