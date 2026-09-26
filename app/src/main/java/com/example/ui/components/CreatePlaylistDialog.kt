package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicCardElevated
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary

@Composable
fun CreatePlaylistDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Buat Playlist Baru",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppleMusicTextPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Playlist", color = AppleMusicTextSecondary) },
                    placeholder = { Text("Contoh: Lagu Perjalanan Malam", color = AppleMusicTextSecondary) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppleMusicCardElevated,
                        unfocusedContainerColor = AppleMusicCardElevated,
                        focusedBorderColor = AppleMusicRed,
                        unfocusedBorderColor = AppleMusicBorder,
                        focusedTextColor = AppleMusicTextPrimary,
                        unfocusedTextColor = AppleMusicTextPrimary,
                        cursorColor = AppleMusicRed
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi (Opsional)", color = AppleMusicTextSecondary) },
                    placeholder = { Text("Catatan suasana hati atau genre...", color = AppleMusicTextSecondary) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppleMusicCardElevated,
                        unfocusedContainerColor = AppleMusicCardElevated,
                        focusedBorderColor = AppleMusicRed,
                        unfocusedBorderColor = AppleMusicBorder,
                        focusedTextColor = AppleMusicTextPrimary,
                        unfocusedTextColor = AppleMusicTextPrimary,
                        cursorColor = AppleMusicRed
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name.trim(), description.trim())
                    }
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppleMusicRed,
                    contentColor = Color.White,
                    disabledContainerColor = AppleMusicCardElevated,
                    disabledContentColor = AppleMusicTextSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Buat", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = AppleMusicTextSecondary)
            }
        },
        containerColor = AppleMusicCard,
        shape = RoundedCornerShape(20.dp)
    )
}
