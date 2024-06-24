package com.example.student_app.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student_app.model.Student

@Composable
fun StudentRow(student: Student, onClick: (Student) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(student) }
    ) {
        Text(text = "Nombre: ${student.nombre}", style = MaterialTheme.typography.body1)
        Text(text = "Ciclo: ${student.ciclo}", style = MaterialTheme.typography.body2)
        Text(text = "Facultad: ${student.facultad}", style = MaterialTheme.typography.body2)
        Text(text = "Estado: ${student.estado}", style = MaterialTheme.typography.body2)
        Spacer(modifier = Modifier.height(8.dp))
    }
}