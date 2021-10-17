package org.wit.classattendanceapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class ModuleModel(var id: Long = 0, var moduleCode: String ="", var title: String="",
                       var lectures: List<LectureModel> = mutableListOf(LectureModel(0,"","","",""))): Parcelable
