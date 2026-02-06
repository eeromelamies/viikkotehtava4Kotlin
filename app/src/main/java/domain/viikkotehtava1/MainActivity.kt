package domain.viikkotehtava1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import domain.viikkotehtava1.homeScreen.CalendarScreen
import domain.viikkotehtava1.homeScreen.HomeScreen
import domain.viikkotehtava1.navigation.ROUTE_CALENDAR
import domain.viikkotehtava1.navigation.ROUTE_HOME
import domain.viikkotehtava1.ui.theme.Viikkotehtava1Theme
import domain.viikkotehtava1.viewModel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Viikkotehtava1Theme {
                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            viewModel = viewModel,
                            onTaskClick = { id ->

                                val task = viewModel.tasks.find { it.id == id }
                                if (task != null) viewModel.openEditDialog(task)
                            },
                            onAddClick = {
                                viewModel.isAddTaskDialogOpen = true
                            },
                            onNavigateCalendar = {
                                navController.navigate(ROUTE_CALENDAR)
                            }
                        )
                    }

                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            viewModel = viewModel,
                            onNavigateHome = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}