package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.databinding.ActivityLoginBinding
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber
import timber.log.Timber.i

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var student = StudentModel(0,"","","")
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLogin.title = title
        setSupportActionBar(binding.toolbarLogin)

        app = application as MainApp

        binding.btnLogin.setOnClickListener {
            student.studentID = binding.studentId.text.toString().toLong()
            student.password = binding.password.text.toString()

            val iterator = app.students.findAllUsers().listIterator()
            for(item in iterator){
                i("${item.studentID}")
                i("${item.password}")
                if(item.studentID == student.studentID && item.password==student.password) {
                    student = item
                    i("${student}")
                    val launcherIntent = Intent(this, ModuleListActivity::class.java)
                    launcherIntent.putExtra("student_logged_in", student)
                    startActivityForResult(launcherIntent, 0)

                }

                else{
                    //i("${app.students}")
                    //i("StudentID: ${student.studentID} Password: ${student.password}")
                    i("StudentID: ${student.studentID.javaClass} Password: ${student.password.javaClass}")
                    Snackbar.make(it,"Invalid User Credentials", Snackbar.LENGTH_LONG)
                        .show()
                }


            }


        }
    }
}