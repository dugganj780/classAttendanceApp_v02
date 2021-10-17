package org.wit.classattendanceapp.models
import java.util.*

fun generateARandomId(): Long {
    return Random().nextLong()
}

class ModuleMemStore : ModuleStore {

    val modules = ArrayList<ModuleModel>()

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
/*
    override fun findOneLecture(id: Long, lectureId: Int) : LectureModel {
        var foundModule: ModuleModel? = modules.find { p -> p.id == id }
        var lectures = foundModule!!.lectures
        var lecture = LectureModel(1,"","","","")

        val iterator = lectures.listIterator()
        for(item in iterator){
            if(item.id == lectureId){
                lecture = item
            }
        }
        return lecture
    }

 */
}