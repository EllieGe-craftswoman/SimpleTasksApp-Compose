package com.ellies.composeapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TasksScreen(vmf: MyViewModelFactory, onTaskClicked : (Task) -> Unit) {
    val vm: TasksScreenViewModel = viewModel(factory = vmf)
    val tasks by vm.getTasks().observeAsState()
    Column {
        tasks?.let {
            TaskList(it, onTaskClicked)
        }
    }
}

@Composable
fun AddTaskScreen(vmf: MyViewModelFactory, onTaskSaved: () -> Unit) {
    val vm: AddTaskScreenViewModel = viewModel(factory = vmf)
    Column {
        TaskEditor(
            task = Task(name = "", complete = false),
            onTaskChange = {
                vm.addTask(it)
                onTaskSaved()
            }
        )
    }
}
@Composable
fun EditTaskScreen(vmf: MyViewModelFactory, taskId: Long, onTaskSaved: () -> Unit) {
    val vm: EditTaskScreenViewModel = viewModel(factory = vmf)
    val task by vm.getTask(taskId).observeAsState()
    Column {
        task?.let { task ->
            TaskEditor(
                task = task,
                onTaskChange = {
                    vm.editTask(it)
                    onTaskSaved()
                }
            )
        }
    }
}