package com.chopperhl.people.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    title: String = "People", onNavBack: (() -> Unit)? = null, content: @Composable () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(title, fontWeight = FontWeight.Bold)
        }, modifier = Modifier.shadow(elevation = 4.dp), navigationIcon = {
            if (onNavBack != null) {
                IconButton(onClick = onNavBack) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            }
        })
    }) { padding ->
        Box(
            Modifier
                .padding(paddingValues = padding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}

