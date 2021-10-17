package org.wit.classattendanceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.adapters.LectureAdapter
import org.wit.classattendanceapp.databinding.ActivityLectureBinding
import org.wit.classattendanceapp.databinding.ActivityModuleBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.LectureModel
import org.wit.classattendanceapp.models.ModuleModel
import timber.log.Timber
import timber.log.Timber.i

import java.text.SimpleDateFormat
import java.util.*

class LectureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLectureBinding
    var module = ModuleModel()
    var lecture = LectureModel(0,"","","","")
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLectureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLecture.title = title
        setSupportActionBar(binding.toolbarLecture)

        app = application as MainApp

        if (intent.hasExtra("lecture_selected")) {
            val layoutManager = LinearLayoutManager(this)
            lecture = intent.extras?.getParcelable("lecture_selected")!!
            Timber.i("module id is ${module.id}")
            //binding.recyclerView.layoutManager = layoutManager
           // binding.recyclerView.adapter = LectureAdapter(app.modules.findLectures(module.id),this)
        }

        binding.btnSignInPerson.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            var signIn = signIn(module.moduleCode, lecture.day, lecture.startTime, currentDate, true,false, false)

            i("New Sign In: $signIn")
            setResult(RESULT_OK)
            finish()
        }

        binding.btnSignInLive.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            var signIn = signIn(module.moduleCode, lecture.day, lecture.startTime, currentDate, false,true, false)

            i("New Sign In: $signIn")
            setResult(RESULT_OK)
            finish()
        }

        binding.btnSignInPerson.setOnClickListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            var signIn: signIn = signIn(module.moduleCode, lecture.day, lecture.startTime, currentDate, false,false, true)

            i("New Sign In: $signIn")
            setResult(RESULT_OK)
            finish()
        }

    }
}

data class signIn(var moduleCode: String = "", var day:String = "", var startTime: String = "",
                  var signTime: String = "", var inPerson: Boolean = false, var live: Boolean = false,
                  var recording: Boolean = true){}