package org.wit.classattendanceapp.models
import java.util.*

fun generateARandomId(): Long {
    return Random().nextLong()
}

class ModuleMemStore : ModuleStore {

    val modules = ArrayList<ModuleModel>()
    val users = ArrayList<StudentModel>()


    override fun findAll(): List<ModuleModel> {
        return modules
    }

    override fun create(module: ModuleModel) {
        module.id = generateARandomId()
        modules.add(module)
    }

    override fun delete(module: ModuleModel) {
        modules.remove(module)
    }

    override fun findOne(id: Long) : ModuleModel? {
        var foundModule: ModuleModel? = modules.find { p -> p.id == id }
        return foundModule
    }

    override fun findLectures(id: Long) : List<LectureModel>{
        var foundModule: ModuleModel? = modules.find { p -> p.id == id }
        var lectures = foundModule!!.lectures
        return lectures
    }

    override fun updateLecture(module: ModuleModel, lecture: LectureModel) {
        var foundModule: ModuleModel? = modules.find { p -> p.id == module.id }

        if (foundModule != null) {
            var lectures = foundModule.lectures
            //var lecture = LectureModel(1,"","","","","")

            val iterator = lectures.listIterator()
            for (item in iterator) {
                if (item.id == lecture.id) {
                    item.startTime = lecture.startTime
                    item.endTime = lecture.endTime
                    item.day = lecture.day
                    item.location = lecture.location
                    item.cancelMessage = lecture.cancelMessage
                }
            }
        }

    }

    override fun findAllUsers(): List<StudentModel> {
        return users
    }

    override fun createUser(student: StudentModel) {
        users.add(student)
    }

    override fun updateUser(student: StudentModel) {
        var foundUser: StudentModel? = users.find { p -> p.studentID == student.studentID }
        if (foundUser != null) {
            foundUser.firstName = student.firstName
            foundUser.surname = student.surname
            foundUser.studentID = student.studentID
            foundUser.password = student.password
        }
    }


}