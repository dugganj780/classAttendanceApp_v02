package org.wit.classattendanceapp.main

import android.app.Application
import org.wit.classattendanceapp.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var students: UserStore
    lateinit var modules: ModuleStore
    lateinit var attendance: SignInStore
/*
    //Creating Users, Modules and Lectures for base app
    var john = StudentModel(1234567,"John","Lennon","thebeatles")
    var paul = StudentModel(7654321,"Paul","McCartney","thebeatles")
    var ringo = StudentModel(4561237,"Ringo","Starr","yellowsubmarine")
    var george = StudentModel(7123456,"George","Harrison","thebeatles")
    var admin = StudentModel(1,"Admin","Admin","test",true)

    val lecture01 = LectureModel(1,"14:00","13:00","Monday","WGB_G03")
    val lecture02 = LectureModel(2,"09:00","10:00","Tuesday","WGB_G03")
    val lecture03 = LectureModel(3,"11:00","12:00","Tuesday","WGB_G15")
    val lecture04 = LectureModel(4,"16:00","17:00","Wednesday","WGB_G02")
    val lecture05 = LectureModel(5,"14:00","15:00","Thursday","WGB_G05")
    val lecture06 = LectureModel(6,"12:00","13:00","Friday","WGB_1.10")

    var list01 = mutableListOf(lecture01,lecture03)
    var list02 = mutableListOf(lecture02,lecture06)
    var list03 = mutableListOf(lecture04,lecture05)
*/
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Class App started")

        modules = ModuleJSONStore(applicationContext)
        students = UserJSONStore(applicationContext)
        attendance = SignInJSONStore(applicationContext)
/*
        students.createUser(john.copy())
        students.createUser(paul.copy())
        students.createUser(ringo.copy())
        students.createUser(george.copy())
        students.createUser(admin.copy())

        modules.create(ModuleModel(1,"CS6312","Mobile Devices and Systems", list01))
        modules.create(ModuleModel(2,"CS6321","Model-Based Software Development", list02))
        modules.create(ModuleModel(3,"CS6322","Optimisation", list03))
*/
    }
}