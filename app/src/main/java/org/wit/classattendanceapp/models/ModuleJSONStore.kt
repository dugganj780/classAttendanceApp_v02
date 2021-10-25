package org.wit.classattendanceapp.models

import android.content.Context
import android.net.Uri

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import mu.KotlinLogging
import java.util.*
import java.lang.reflect.Type
import org.wit.classattendanceapp.helpers.*

const val JSON_FILE = "modules.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java,
    ModuleJSONStore.UriParser()).create()
val listType: Type = object:TypeToken<java.util.ArrayList<ModuleModel>>() {}.type


fun generateRandomId(): Long {
    return Random().nextLong()
}



class ModuleJSONStore(private val context: Context): ModuleStore {
    var modules = mutableListOf<ModuleModel>()
    var users = mutableListOf<StudentModel>()

    init {
        if (exists(context, JSON_FILE)) {
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
        //serializeUsers()
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
        modules.forEach { Timber.i("${it}") }
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
        serialize()
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        modules = gsonBuilder.fromJson(jsonString, listType)
        users = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun serialize() {
        val jsonStringModules = gsonBuilder.toJson(modules, listType)
        //val jsonStringUsers = gsonBuilder.toJson(users, listType)
        //val jsonString = jsonStringModules.plus(","+ jsonStringUsers)
        write(context, JSON_FILE, jsonStringModules)
        //write(context, JSON_FILE,jsonStringUsers)
    }

    private fun deserializeUsers() {
        val jsonString = read(context, JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun serializeUsers() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }


    class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Uri {
            return Uri.parse(json?.asString)
        }

        override fun serialize(
            src: Uri?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.toString())
        }
    }
}