package com.chopperhl.people.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun SingleLineText(
    text: String,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight? = null,
    fontColor: Color = Color.Unspecified
) {
    Text(
        text = text,
        fontSize = fontSize,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontWeight = fontWeight,
        color = fontColor
    )
}