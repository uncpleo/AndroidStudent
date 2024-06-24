package com.example.student_app.network

import com.example.student_app.model.Student
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("estudiantes")
    fun getStudents(): Call<List<Student>>

    @POST("estudiantes")
    fun createStudent(@Body student: Student): Call<Student>

    @PATCH("estudiantes/{id}")
    fun updateStudent(@Path("id") id: String, @Body student: Student): Call<Student>

    @DELETE("estudiantes/{id}")
    fun deleteStudent(@Path("id") id: String): Call<Void>
}