package com.chopperhl.people.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chopperhl.people.NavigationArguments.KEY_PERSON_ID
import com.chopperhl.people.NavigationArguments.KEY_PERSON_NAME
import com.chopperhl.people.NavigationDestinations
import com.chopperhl.people.ui.theme.PeopleTheme
import com.chopperhl.people.ui.widget.CommonAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeopleTheme {
                MainNavHost()
            }
        }
    }
}


@Composable
private fun MainNavHost() {
    val navController = rememberNavController()
    val duration = 800
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }.toInt()
    NavHost(navController,
        modifier = Modifier.fillMaxSize()/*.background(Brush.verticalGradient(listOf(Color(0xFF1E1266), Color.Black)))*/,
        startDestination = NavigationDestinations.PEOPLE_LIST,
        enterTransition = {
            val isNavBack = this.targetState.destination.route == NavigationDestinations.PEOPLE_LIST
            val offset = if (isNavBack) -screenWidth else screenWidth
            slideInHorizontally(animationSpec = tween(duration),
                initialOffsetX = { offset }) + fadeIn(animationSpec = tween(duration))
        },
        exitTransition = {
            val isNavBack = this.targetState.destination.route == NavigationDestinations.PEOPLE_LIST
            val offset = if (isNavBack) screenWidth else -screenWidth
            slideOutHorizontally(animationSpec = tween(duration),
                targetOffsetX = { offset }) + fadeOut(animationSpec = tween(duration))
        }) {
        composable(route = NavigationDestinations.PEOPLE_LIST) {
            CommonAppBar {
                PeopleListScreen { id, name ->
                    navController.navigate("${NavigationDestinations.PERSON_DETAILS}/${id}/${name}")
                }
            }
        }
        composable(route = "${NavigationDestinations.PERSON_DETAILS}/{${KEY_PERSON_ID}}/{${KEY_PERSON_NAME}}") { stackEntry ->
            val id = stackEntry.arguments?.getString(KEY_PERSON_ID, "unknown")!!
            val name = stackEntry.arguments?.getString(KEY_PERSON_NAME, "unknown")!!
            CommonAppBar(name, onNavBack = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            }) {
                PersonDetailsScreen(id, name)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainNavHost()
}