package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.databinding.ActivityCreateAccountBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    var user = StudentModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCreateAccount.title = title
        setSupportActionBar(binding.toolbarCreateAccount)

        app = application as MainApp

        if(intent.hasExtra("student_edit")){
            edit = true
            user = intent.extras?.getParcelable("student_edit")!!
            binding.firstName.setText(user.firstName)
            binding.surname.setText(user.surname)
        }

        binding.btnCreateAccount.setOnClickListener {
            user.firstName = binding.firstName.text.toString()
            user.surname = binding.surname.text.toString()
            user.studentID = binding.studentId.text.toString().toLong()
            user.password = binding.password.text.toString()

            if (user.firstName.isEmpty() || user.surname.isEmpty() || user.password.isEmpty() ) {
                Snackbar.make(it, R.string.data_missing, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if(edit){
                    app.students.updateUser(user.copy())
                    val launcherIntent = Intent(this, LaunchActivity::class.java)
                    launcherIntent.removeExtra("student_logged_in")
                    launcherIntent.removeExtra("module_edit")
                    launcherIntent.removeExtra("lecture_selected")
                    startActivityForResult(launcherIntent,0)

                }
                    app.students.createUser(user.copy())
            }

            Timber.i("New User Created: $user")
            setResult(RESULT_OK)
            finish()
        }
    }
}