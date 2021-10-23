package org.wit.classattendanceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.adapters.LectureAdapter
import org.wit.classattendanceapp.databinding.ActivityLectureAdminBinding
import org.wit.classattendanceapp.databinding.ActivityLectureBinding
import org.wit.classattendanceapp.databinding.ActivityModuleBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.LectureModel
import org.wit.classattendanceapp.models.ModuleModel
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber
import timber.log.Timber.i

import java.text.SimpleDateFormat
import java.util.*

class LectureActivity : AppCompatActivity() {


    var module = ModuleModel()
    var lecture = LectureModel(0,"","","","")
    var student = StudentModel()
    var attendance = ArrayList<signIn>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra("lecture_selected")) {
            val layoutManager = LinearLayoutManager(this)
            lecture = intent.extras?.getParcelable("lecture_selected")!!
            module = intent.extras?.getParcelable("module_selected")!!
            student = intent.extras?.getParcelable("student_logged_in")!!

            Timber.i("module id is ${module.id}")
            //binding.recyclerView.layoutManager = layoutManager
            // binding.recyclerView.adapter = LectureAdapter(app.modules.findLectures(module.id),this)
        }

        if (!student.isAdmin) {
            lateinit var binding: ActivityLectureBinding
            binding = ActivityLectureBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.toolbarLecture.title = title
            setSupportActionBar(binding.toolbarLecture)

            app = application as MainApp



            binding.btnSignInPerson.setOnClickListener {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                var signIn = signIn(
                    student.studentID,
                    student.firstName,
                    student.surname,
                    module.moduleCode,
                    lecture.day,
                    lecture.startTime,
                    currentDate,
                    true,
                    false,
                    false
                )

                i("New Sign In: $signIn")
                attendance.add(signIn.copy())
                setResult(RESULT_OK)
                finish()
            }

            binding.btnSignInLive.setOnClickListener {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                var signIn = signIn(
                    student.studentID,
                    student.firstName,
                    student.surname,
                    module.moduleCode,
                    lecture.day,
                    lecture.startTime,
                    currentDate,
                    false,
                    true,
                    false
                )

                i("New Sign In: $signIn")
                attendance.add(signIn.copy())
                setResult(RESULT_OK)
                finish()
            }

            binding.btnSignInRecording.setOnClickListener {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                var signIn: signIn = signIn(
                    student.studentID,
                    student.firstName,
                    student.surname,
                    module.moduleCode,
                    lecture.day,
                    lecture.startTime,
                    currentDate,
                    false,
                    false,
                    true
                )

                i("New Sign In: $signIn")
                attendance.add(signIn.copy())
                setResult(RESULT_OK)
                finish()
            }

        }

        else{

            lateinit var binding: ActivityLectureAdminBinding
            binding = ActivityLectureAdminBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.toolbarLecture.title = title
            setSupportActionBar(binding.toolbarLecture)

            app = application as MainApp

            binding.btnCancelLecture.setOnClickListener {
                lecture.cancelMessage = binding.cancelMessage.text.toString()
                app.modules.updateLecture(module,lecture)
                i("$lecture")
                i("$module")
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}

data class signIn(var studentID: Long = 0, var firstName:String ="", var surname:String ="", var moduleCode: String = "", var day:String = "", var startTime: String = "",
                  var signTime: String = "", var inPerson: Boolean = false, var live: Boolean = false,
                  var recording: Boolean = true){}