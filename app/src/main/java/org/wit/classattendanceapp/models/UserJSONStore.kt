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
import timber.log.Timber.i

const val JSON_FILE_USERS = "users.json"
val gsonBuilderUsers = GsonBuilder().setPrettyPrinting().registerTypeAdapter(Uri::class.java,
    UserJSONStore.UriParser()).create()
val listTypeUser: Type = object:TypeToken<java.util.ArrayList<StudentModel>>() {}.type


class UserJSONStore (private val context: Context): UserStore{
    var users = mutableListOf<StudentModel>()

    init {
        if (exists(context, JSON_FILE_USERS)) {
            deserialize()
        }
    }

    override fun findAllUsers(): List<StudentModel> {
        return users
    }

    override fun createUser(student: StudentModel) {
        users.add(student)
        serialize()
        //serializeUsers()
    }

    override fun updateUser(student: StudentModel) {
        var foundUser: StudentModel? = users.find { p -> p.studentID == student.studentID }
        if (foundUser != null) {
            foundUser.firstName = student.firstName
            foundUser.surname = student.surname
            foundUser.studentID = student.studentID
            foundUser.password = student.password
        }
        //serializeUsers()
        serialize()
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_USERS)
        users = gsonBuilderUsers.fromJson(jsonString, listTypeUser)
    }

    private fun serialize() {
        val jsonStringUsers = gsonBuilderUsers.toJson(users, listTypeUser)
        //val jsonStringUsers = gsonBuilder.toJson(users, listType)
        //val jsonString = jsonStringModules.plus(","+ jsonStringUsers)
        write(context, JSON_FILE_USERS, jsonStringUsers)
        //write(context, JSON_FILE,jsonStringUsers)
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