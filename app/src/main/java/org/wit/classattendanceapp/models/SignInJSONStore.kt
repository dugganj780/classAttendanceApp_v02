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

const val JSON_FILE_SIGNIN = "attendance.json"
val gsonBuilderSignIn = GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java,
    SignInJSONStore.UriParser()).create()
val listTypeSignIn: Type = object:TypeToken<java.util.ArrayList<SignInModel>>() {}.type


class SignInJSONStore(private val context: Context): SignInStore {
    var modules = mutableListOf<ModuleModel>()
    var attendance = mutableListOf<SignInModel>()

    init {
        if (exists(context, JSON_FILE_SIGNIN)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SignInModel> {
        return attendance
    }
    /*
        override fun findOne(id: Long) : ModuleModel? {
            var foundModule: ModuleModel? = modules.find { p -> p.id == id }
            return foundModule
        }
    */
    override fun create(signIn: SignInModel) {
        attendance.add(signIn)
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
/*
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
 */

    override fun moduleSignIns(module: ModuleModel): MutableList<SignInModel> {
        var foundModule: ModuleModel? = modules.find { p -> p.id == module.id }
        var moduleAttendance = mutableListOf<SignInModel>()

        if (foundModule != null) {

            //var lecture = LectureModel(1,"","","","","")

            val iterator = attendance.listIterator()
            for (item in iterator) {
                if (item.moduleCode == foundModule.moduleCode) {
                    moduleAttendance.add(item.copy())
                }
            }
        }
        return moduleAttendance
    }

    private fun deserialize() {
        val jsonStringSignIn = read(context, JSON_FILE_SIGNIN)
        attendance = gsonBuilderSignIn.fromJson(jsonStringSignIn, listTypeSignIn)
        //users = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun serialize() {
        val jsonStringSignIn = gsonBuilderSignIn.toJson(attendance, listTypeSignIn)
        //val jsonStringUsers = gsonBuilder.toJson(users, listType)
        //val jsonString = jsonStringModules.plus(","+ jsonStringUsers)
        write(context, JSON_FILE_SIGNIN, jsonStringSignIn)
        //write(context, JSON_FILE,jsonStringUsers)
    }
/*
    private fun deserializeUsers() {
        val jsonString = read(context, JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun serializeUsers() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

 */


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