package org.wit.classattendanceapp.models

class ModuleMemStore : ModuleStore {

    val modules = ArrayList<ModuleModel>()

    override fun findAll(): List<ModuleModel> {
        return modules
    }

    override fun create(module: ModuleModel) {
        modules.add(module)
    }

    override fun delete(module: ModuleModel) {
        modules.remove(module)
    }
}