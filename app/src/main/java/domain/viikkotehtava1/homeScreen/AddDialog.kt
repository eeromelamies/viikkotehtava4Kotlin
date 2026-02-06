import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import domain.viikkotehtava1.domain.Task
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AddDialog(
    onClose: () -> Unit,
    onUpdate: (Task) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("2026-02-05") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Add New Task") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
                TextField(value = date, onValueChange = { date = it }, label = { Text("Due Date") })
            }
        },
        confirmButton = {
            Button(onClick = {

                onUpdate(Task(id = (0..1000).random(), title = title, description = desc, dueDate = date, done = false))
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onClose) { Text("Cancel") }
        }
    )
}