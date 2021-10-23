package org.wit.classattendanceapp.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import java.util.*
import org.wit.classattendanceapp.helpers.*

private val logger = KotlinLogging.logger {}

val JSON_FILE = "modules.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<ModuleModel>>() {}.type


fun generateRandomId(): Long {
    return Random().nextLong()
}



class ModuleJSONStore: ModuleStore {
    var modules = mutableListOf<ModuleModel>()
    val users = mutableListOf<StudentModel>()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ModuleModel> {
        return modules
    }
/*
    override fun findOne(id: Long) : ModuleModel? {
        var foundModule: ModuleModel? = modules.find { p -> p.id == id }
        return foundModule
    }
*/
    override fun create(module: ModuleModel) {
        module.id = generateRandomId()
        modules.add(module)
        serialize()
    }
/*
    override fun update(module: ModuleModel) {
        var foundModule = findOne(module.id!!)
        if (foundModule != null) {
            foundModule.title = module.title
            foundModule.moduleCode = module.moduleCode
        }
        serialize()
    }

 */

    override fun delete(module: ModuleModel) {
        modules.remove(module)
        serialize()
    }

    internal fun logAll() {
        modules.forEach { logger.info("${it}") }
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
        //var foundModule: ModuleModel? = modules.find { p -> p.id == id }
        var lectures = module.lectures
        //var lecture = LectureModel(1,"","","","","")

        val iterator = lectures.listIterator()
        for(item in iterator){
            if(item.id == lecture.id){
                item.startTime = lecture.startTime
                item.endTime = lecture.endTime
                item.day = lecture.day
                item.location = lecture.location
                item.cancelMessage = lecture.cancelMessage
            }
        }

    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(modules, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        modules = Gson().fromJson(jsonString, listType)
    }

    override fun findAllUsers(): List<StudentModel> {
        return users
    }

    override fun createUser(student: StudentModel) {
        users.add(student)
    }
}