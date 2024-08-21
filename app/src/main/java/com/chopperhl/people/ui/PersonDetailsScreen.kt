package com.chopperhl.people.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo.api.Optional
import com.chopperhl.people.PersonQuery
import com.chopperhl.people.apolloClient
import com.chopperhl.people.ui.widget.ErrorView
import com.chopperhl.people.ui.widget.LoadingView
import com.chopperhl.people.ui.widget.SingleLineText

import com.chopperhl.people.Constants.UNKNOWN_TEXT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreen(id: String, name: String) {
    var queryId: String by remember { mutableStateOf(id) }
    var person: PersonQuery.Person? by remember { mutableStateOf(null) }
    var error: String? by remember { mutableStateOf(null) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(queryId) {
        val response = apolloClient.query(
            PersonQuery(Optional.present(queryId.trim()))
        ).execute()
        error = response.exception?.message
        person = response.data?.person
    }
    //LoadingView and ErrorView control
    if (person == null) {
        if (error != null) {
            ErrorView {
                //append blank string to trigger refresh
                queryId += " "
                error = null
            }
        } else {
            LoadingView()
        }
    } else {
        //ContentView
        DetailContent(name) {
            showBottomSheet = true
        }
    }
    //BottomSheetDialog
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.height(480.dp),
            containerColor = Color.White,

            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            BottomSheetContent(person)
        }
    }
}

@Composable
fun DetailContent(name: String, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val clickableText = "here"
        val annotatedString = buildAnnotatedString {
            append("Click ")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue, textDecoration = TextDecoration.Underline
                )
            ) {
                pushStringAnnotation(tag = clickableText, annotation = clickableText)
                append(clickableText)
            }
            append(" to view homeworld data for ${name}.")
        }
        ClickableText(text = annotatedString,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            onClick = {
                onClick()
            })

    }
}

@Composable
fun BottomSheetContent(person: PersonQuery.Person?) {
    val name = person?.name ?: UNKNOWN_TEXT
    val homeworld = person?.homeworld?.name ?: UNKNOWN_TEXT
    val mass = person?.mass ?: UNKNOWN_TEXT
    val height = person?.height ?: UNKNOWN_TEXT
    Column(
        Modifier.padding(16.dp)
    ) {
        SingleLineText(
            text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp, fontColor = Color.Black
        )
        SingleLineText(text = "Homeworld: ${homeworld}", fontColor = Color.Black, fontSize = 16.sp)
        SingleLineText(text = "Mass: ${mass}", fontColor = Color.Black, fontSize = 16.sp)
        SingleLineText(text = "Height: ${height}", fontColor = Color.Black, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PersonDetailPreview() {
}
