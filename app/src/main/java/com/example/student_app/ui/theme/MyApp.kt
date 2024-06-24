package com.example.student_app.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student_app.model.Student
import com.example.student_app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

@Composable
fun MyApp() {
    var students by remember { mutableStateOf(listOf<Student>()) }
    var showCreateForm by remember { mutableStateOf(false) }
    var showUpdateForm by remember { mutableStateOf<Student?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    fun loadStudents() {
        ApiClient.apiService.getStudents().enqueue(object : Callback<List<Student>> {
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                if (response.isSuccessful) {
                    students = response.body()!!
                } else {
                    errorMessage = "Error al cargar los estudiantes"
                }
            }

            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                errorMessage = t.message ?: "Error desconocido"
            }
        })
    }

    LaunchedEffect(Unit) {
        loadStudents()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Estudiantes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateForm = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Estudiante")
            }
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colors.error)
                }
                if (showCreateForm) {
                    CreateStudentForm(onStudentCreated = { student ->
                        students = students + student
                        showCreateForm = false
                        loadStudents()
                    }, onError = { errorMessage = it })
                } else if (showUpdateForm != null) {
                    UpdateStudentForm(showUpdateForm!!, onStudentUpdated = { updatedStudent ->
                        students = students.map { if (it.id == updatedStudent.id) updatedStudent else it }
                        showUpdateForm = null
                        loadStudents()
                    }, onError = { errorMessage = it })
                } else {
                    LazyColumn {
                        items(students) { student ->
                            StudentRow(student, onClick = { showUpdateForm = it })
                        }
                    }
                }
            }
        }
    )
}
