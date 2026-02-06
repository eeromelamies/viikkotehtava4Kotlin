package domain.viikkotehtava1.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import domain.viikkotehtava1.domain.Task
import domain.viikkotehtava1.domain.mockTodos
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TaskViewModel : ViewModel() {
    var isSortedByDate by mutableStateOf(false)
    var tasks by mutableStateOf(listOf<Task>())
        private set
    var isAddTaskDialogOpen by mutableStateOf(false)

    var editingTask by mutableStateOf<Task?>(null)
        private set

    private var allTasks: List<Task> = mockTodos

    init {
        tasks = allTasks
    }

    fun openEditDialog(task: Task) {
        editingTask = task
    }

    fun closeEditDialog() {
        editingTask = null
    }

    fun addTask(task: Task) {
        allTasks = allTasks + task
        tasks = allTasks
    }

    fun removeTask(id: Int) {
        allTasks = allTasks.filter { it.id != id }
        tasks = tasks.filter { it.id != id }
    }

    fun toggleDone(id: Int) {
        val updateLambda = { task: Task ->
            if (task.id == id) task.copy(done = !task.done) else task
        }
        allTasks = allTasks.map(updateLambda)
        tasks = tasks.map(updateLambda)
    }

    fun updateTask(id: Int, newTitle: String, newDescription: String) {
        val updateLambda = { t: Task ->
            if (t.id == id) t.copy(title = newTitle, description = newDescription) else t
        }
        allTasks = allTasks.map(updateLambda)
        tasks = tasks.map(updateLambda)
        closeEditDialog()
    }

    fun filterByDone(done: Boolean) {
        tasks = allTasks.filter { it.done == done }
    }

    fun showAll() {
        tasks = allTasks
    }

    fun sortByDueDate() {
        isSortedByDate = !isSortedByDate
        tasks = if (isSortedByDate) {
            allTasks.sortedBy { it.dueDate }
        } else {
            allTasks.sortedBy { it.id }
        }
    }
}