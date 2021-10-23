package org.wit.classattendanceapp.models

interface ModuleStore {
    fun findAll(): List<ModuleModel>
    fun create(module: ModuleModel)
    fun delete(module: ModuleModel)
    fun findOne(id: Long): ModuleModel?
    fun findLectures(id:Long): List<LectureModel>
    fun updateLecture(module: ModuleModel, lecture: LectureModel)
    fun findAllUsers():List<StudentModel>
    fun createUser(student:StudentModel)
    fun updateUser(student:StudentModel)
}