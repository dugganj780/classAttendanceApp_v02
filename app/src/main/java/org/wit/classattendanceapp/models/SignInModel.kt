package org.wit.classattendanceapp.models

data class SignInModel(var studentID: Long = 0, var firstName:String ="", var surname:String ="", var moduleCode: String = "", var day:String = "", var startTime: String = "",
                  var signTime: String = "", var inPerson: Boolean = false, var live: Boolean = false,
                  var recording: Boolean = true)