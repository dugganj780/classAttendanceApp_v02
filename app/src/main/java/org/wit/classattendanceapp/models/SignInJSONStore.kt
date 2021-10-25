package org.wit.classattendanceapp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.util.*
import java.lang.reflect.Type
import org.wit.classattendanceapp.helpers.*
import timber.log.Timber.i

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

    override fun create(signIn: SignInModel) {
        attendance.add(signIn)
        serialize()
    }

    override fun moduleSignIns(module: ModuleModel,lecture: LectureModel): MutableList<SignInModel> {
        i("$module")
        var foundModule: ModuleModel? = modules.find { p -> p.id == module.id }
        var moduleAttendance = mutableListOf<SignInModel>()
        var moduleAttendanceByDay = mutableListOf<SignInModel>()
        i("$foundModule")

        if (foundModule != null) {

            //var lecture = LectureModel(1,"","","","","")

            val iterator = attendance.listIterator()
            for (item in iterator) {
                if (item.moduleCode == foundModule.moduleCode) {
                            i("$foundModule")
                            moduleAttendance.add(item.copy())
                        }
                    }
            val dayIterator = moduleAttendance.listIterator()
            for(foundLecture in dayIterator) {
                if (foundLecture.day == lecture.day) {
                    i("$foundLecture")
                    moduleAttendanceByDay.add(foundLecture.copy())
                }
            }

        }
        i("$moduleAttendance")
        return moduleAttendanceByDay
    }

    private fun deserialize() {
        val jsonStringSignIn = read(context, JSON_FILE_SIGNIN)
        val jsonString = read(context, JSON_FILE)
        attendance = gsonBuilderSignIn.fromJson(jsonStringSignIn, listTypeSignIn)
        modules = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun serialize() {
        val jsonStringSignIn = gsonBuilderSignIn.toJson(attendance, listTypeSignIn)
        write(context, JSON_FILE_SIGNIN, jsonStringSignIn)
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