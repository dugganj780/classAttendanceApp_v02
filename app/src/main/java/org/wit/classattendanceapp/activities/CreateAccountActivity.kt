package org.wit.classattendanceapp.activities

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
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCreateAccount.title = title
        setSupportActionBar(binding.toolbarCreateAccount)

        app = application as MainApp

        binding.btnCreateAccount.setOnClickListener {
            user.firstName = binding.firstName.text.toString()
            user.surname = binding.surname.text.toString()
            user.studentID = binding.studentId.text.toString().toLong()
            user.password = binding.password.text.toString()

            if (user.firstName.isEmpty() || user.surname.isEmpty() || user.password.isEmpty() ) {
                Snackbar.make(it, R.string.data_missing, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                app.students.createUser(user.copy())

            }
            Timber.i("New User Created: $user")
            setResult(RESULT_OK)
            finish()
        }
    }
}