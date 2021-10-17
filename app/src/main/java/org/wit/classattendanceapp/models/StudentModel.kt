package org.wit.classattendanceapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(var studentID: Long =0, var firstName: String = "", var surname: String = "",
                        var password: String =""): Parcelable
