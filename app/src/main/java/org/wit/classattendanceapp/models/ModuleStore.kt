package org.wit.classattendanceapp.models

interface ModuleStore {
    fun findAll(): List<ModuleModel>
    fun create(module: ModuleModel)
    fun delete(module: ModuleModel)
}