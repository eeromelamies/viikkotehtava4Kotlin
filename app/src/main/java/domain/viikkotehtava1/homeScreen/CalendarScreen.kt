package domain.viikkotehtava1.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.viikkotehtava1.domain.Task
import domain.viikkotehtava1.viewModel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onNavigateHome: () -> Unit
) {

    val tasks = viewModel.tasks
    val editingTask = viewModel.editingTask


    val grouped = tasks.groupBy { it.dueDate }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = onNavigateHome) {
                        Icon(Icons.Filled.List, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(horizontal = 16.dp)) {
            grouped.forEach { (date, tasksOfDay) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                items(tasksOfDay) { task ->
                    CalendarTaskCard(
                        task = task,
                        onTaskClick = { viewModel.openEditDialog(task) }
                    )
                }
            }
        }
    }


    editingTask?.let { task ->
        DetailScreen(
            task = task,
            onDismiss = { viewModel.closeEditDialog() },
            onSave = { title, desc ->
                viewModel.updateTask(task.id, title, desc)
            },
            onDelete = {
                viewModel.removeTask(task.id)
                viewModel.closeEditDialog()
            }
        )
    }
}

@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onTaskClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            Text(task.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}