package org.wit.classattendanceapp.models

interface UserStore {
    fun findAllUsers():List<StudentModel>
    fun createUser(student:StudentModel)
    fun updateUser(student:StudentModel)
}