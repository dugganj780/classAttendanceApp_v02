package org.wit.classattendanceapp.main

import android.app.Application
import org.wit.classattendanceapp.models.ModuleJSONStore
import org.wit.classattendanceapp.models.ModuleMemStore
import org.wit.classattendanceapp.models.ModuleModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val modules = ModuleMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Class App started")

        modules.create(ModuleModel(1,"CS6312","Mobile Devices and Systems"))
        modules.create(ModuleModel(2,"CS6321","Model-Based Software Development"))
        modules.create(ModuleModel(3,"CS6322","Optimisation"))

    }
}