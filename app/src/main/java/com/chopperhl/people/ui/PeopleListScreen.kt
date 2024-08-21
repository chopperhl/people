package com.chopperhl.people.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.chopperhl.people.AllPeopleQuery
import com.chopperhl.people.Constants.PAGE_SIZE
import com.chopperhl.people.Constants.UNKNOWN_TEXT
import com.chopperhl.people.apolloClient
import com.chopperhl.people.ui.widget.ErrorView
import com.chopperhl.people.ui.widget.LoadingMore
import com.chopperhl.people.ui.widget.LoadingView
import com.chopperhl.people.ui.widget.SingleLineText



@Composable
fun PeopleListScreen(onClick: (id: String, name: String) -> Unit) {
    var cursor: String? by remember { mutableStateOf(null) }
    var error: String? by remember { mutableStateOf(null) }
    var response: ApolloResponse<AllPeopleQuery.Data>? by remember { mutableStateOf(null) }
    var dataList by remember { mutableStateOf(emptyList<AllPeopleQuery.Person>()) }

    LaunchedEffect(cursor) {

        response = apolloClient.query(
            AllPeopleQuery(
                first = Optional.present(PAGE_SIZE), Optional.present(cursor?.trim())
            )
        ).execute()
        error = response?.exception?.message
        dataList = dataList + response?.data?.allPeople?.people?.filterNotNull().orEmpty()
    }
    //LoadingView and ErrorView control
    if (dataList.isEmpty()) {
        if (error != null) {
            ErrorView {
                //append blank string to trigger refresh
                cursor += " "
                error = null
            }
        } else {
            LoadingView()
        }
    } else {
        //ContentView
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            val hasMore = dataList.size < (response?.data?.allPeople?.totalCount ?: 0)
            items(dataList) { person ->
                PersonItem(person = person, onClick)
                //show LoadingMoreView
                val last = dataList.indexOf(person) == dataList.size - 1
                if (last && hasMore) {
                    LoadingMore()
                }
            }
            if (hasMore) {
                cursor = response?.data?.allPeople?.pageInfo?.endCursor
            }
        }
    }
}

@Composable
fun PersonItem(person: AllPeopleQuery.Person, onClick: (id: String, name: String) -> Unit) {
    val height = person.height?.toString() ?: UNKNOWN_TEXT
    val mass = person.mass?.toString() ?: UNKNOWN_TEXT
    val name = person.name ?: UNKNOWN_TEXT
    Column(modifier = Modifier.clickable { onClick(person.id, person.name!!) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                SingleLineText(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                SingleLineText(text = "Height: $height")
                SingleLineText(text = "Mass: $mass")
            }
            IconButton(onClick = { onClick(person.id, person.name!!) }) {
                Icon(Icons.Filled.KeyboardArrowRight, null)
            }

        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun PeopleListPreview() {
    PeopleListScreen { _, _ ->

    }
}
