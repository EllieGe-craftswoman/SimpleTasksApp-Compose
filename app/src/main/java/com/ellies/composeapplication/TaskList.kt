package com.ellies.composeapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskList(tasks: List<Task>, onTaskClicked: (Task) -> Unit) {
    LazyColumn {
        items(tasks.size) {
            val task = tasks[it]
            Row (
                modifier = Modifier.fillMaxWidth().clickable {
                    onTaskClicked(task)
                },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    text = task.name
                )
                Text(if(task.complete) "  ✔️" else "")
            }
        }
    }
}

@Composable
fun OldTaskList(tasks: List<Task>) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        for (task in tasks) {
            Row{
                Text(task.name)
                Text(if(task.complete) "  ✔️" else "")
            }
        }
    }
}