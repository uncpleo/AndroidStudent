package com.example.student_app.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student_app.model.Student
import com.example.student_app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun UpdateStudentForm(student: Student, onStudentUpdated: (Student) -> Unit, onError: (String) -> Unit) {
    var nombre by remember { mutableStateOf(student.nombre) }
    var ciclo by remember { mutableStateOf(student.ciclo) }
    var facultad by remember { mutableStateOf(student.facultad) }
    var codigoEstudiante by remember { mutableStateOf(student.codigoEstudiante) }
    var estado by remember { mutableStateOf(student.estado) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        TextField(
            value = ciclo,
            onValueChange = { ciclo = it },
            label = { Text("Ciclo") }
        )
        TextField(
            value = facultad,
            onValueChange = { facultad = it },
            label = { Text("Facultad") }
        )
        TextField(
            value = codigoEstudiante,
            onValueChange = { codigoEstudiante = it },
            label = { Text("Código Estudiante") }
        )
        TextField(
            value = estado,
            onValueChange = { estado = it },
            label = { Text("Estado") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val updatedStudent = Student(student.id, nombre, ciclo, facultad, codigoEstudiante, estado)
            ApiClient.apiService.updateStudent(student.id!!, updatedStudent).enqueue(object : Callback<Student> {
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if (response.isSuccessful) {
                        onStudentUpdated(response.body()!!)
                    } else {
                        onError("Error al actualizar el estudiante")
                    }
                }
                override fun onFailure(call: Call<Student>, t: Throwable) {
                    onError(t.message ?: "Error desconocido")
                }
            })
        }) {
            Text("Actualizar Estudiante")
        }
    }
}
