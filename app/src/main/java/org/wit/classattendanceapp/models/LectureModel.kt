package org.wit.classattendanceapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureModel(var id: Int, var startTime: String, var endTime: String, var day: String, var location: String):Parcelable {
}
