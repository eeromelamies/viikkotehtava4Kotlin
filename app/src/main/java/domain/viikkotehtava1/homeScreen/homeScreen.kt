package domain.viikkotehtava1.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.viikkotehtava1.domain.Task
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import domain.viikkotehtava1.viewModel.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit = {},   // oletus: ei tee mitään
    onAddClick: () -> Unit = {},       // oletus: ei tee mitään
    onNavigateCalendar: () -> Unit = {} // jos käytössä
) {
    val tasks = viewModel.tasks
    var newTaskTitle by remember { mutableStateOf("") }


    viewModel.editingTask?.let { task ->
        DetailScreen(
            task = task,
            onDismiss = { viewModel.closeEditDialog() },
            onSave = { newTitle, newDesc ->
                viewModel.updateTask(task.id, newTitle, newDesc)
            },
            onDelete = {

                viewModel.removeTask(task.id)
                viewModel.closeEditDialog()
            }
        )
    }

    LazyColumn {
        item {
            TopAppBar(
                title = { Text("task list") },
                actions = {
                    IconButton(onClick = onNavigateCalendar) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Go to calendar"
                        )
                    }
                }
            )
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                modifier = Modifier.padding(8.dp),
                label = { Text("New task") },
                singleLine = true,
                )

            Button(
                onClick = {
                    viewModel.addTask(
                        Task(
                            id = tasks.size + 1,
                            title = newTaskTitle,
                            dueDate = "2026-1-21",
                            done = false
                        )
                    )
                    newTaskTitle = ""
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Add task")
            }

            Button(
                onClick = { viewModel.showAll() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Show All")
            }

            Button(
                onClick = { viewModel.filterByDone(false) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Show undone")
            }

            Button(
                onClick = { viewModel.sortByDueDate() },
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = if (viewModel.isSortedByDate) "Sort by ID" else "Sort by due date"
                )
            }
        }


        items(tasks) { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = {
                            viewModel.toggleDone(task.id)
                        }
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = "${task.id}. ${task.title}",
                        )

                        Text(
                            text = task.dueDate
                        )
                    }
                }
            }

            Button(onClick = { viewModel.removeTask(task.id) }) {
                Text("Delete")
            }
            Button(onClick = { viewModel.openEditDialog(task) }) {
                Text("Update task")
            }
        }
    }
}




