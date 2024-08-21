package com.chopperhl.people.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = Color(0xFFF5004F))
    }
}

@Composable
fun ErrorView(error: String = "Something went wrong!", onRefresh: () -> Unit) {
    Box(

        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            Modifier.clickable(remember { MutableInteractionSource() }, null, onClick = onRefresh),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                Icon(Icons.Filled.Clear, null)
                Text(text = error)
            }
            Text(text = "Tap to refresh")
        }
    }
}

@Composable
fun LoadingMore() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator(color = Color(0xFFF5004F))
    }
}