package com.ellies.composeapplication

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainNavigation(vmf: MyViewModelFactory) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "tasks",
    ) {

        composable("tasks") {
            MyScaffold(
                title = "Tasks!",
                fabIcon = Icons.Default.Add,
                onFabClicked = { navController.navigate("tasks/add") }) {
                TasksScreen(
                    vmf = vmf,
                    onTaskClicked = {
                        navController.navigate("tasks/${it.id}")
                    }
                )
            }
        }
        composable("tasks/add") {
            MyScaffold(title = "AddTasks") {
                AddTaskScreen(vmf) {
                    navController.navigate("tasks")
                }
            }
        }
        composable(
            "tasks/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.LongType }
            )
        ) {
            it.arguments?.let { bundle ->
                val taskId = bundle.getLong("taskId")
                MyScaffold("Edit Task") {
                    EditTaskScreen(vmf, taskId = taskId) {
                        navController.navigate("tasks")
                    }
                }
            }
        }
    }
}

@Composable
fun MyScaffold(
    title: String,
    fabIcon: ImageVector? = null,
    onFabClicked: () -> Unit = {},
    content: @Composable () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(title)
            })
        },
        floatingActionButton = {
            fabIcon?.let {
                FloatingActionButton(onClick = onFabClicked) {
                    Icon(it, "Fab icon")
                }
            }
        }
    ) {
        content()
    }
}