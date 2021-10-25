package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.databinding.ActivityAttendanceBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.ModuleModel
import org.wit.classattendanceapp.adapters.SignInAdapter
import org.wit.classattendanceapp.models.LectureModel
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber.i

class AttendanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAttendanceBinding
    var module = ModuleModel()
    var lecture = LectureModel(0,"","","","","")
    var student = StudentModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = module.moduleCode
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Class Activity started...")

        //Populates the sign-ins associated with a module and lecture
        if (intent.hasExtra("module_selected")) {
            val layoutManager = LinearLayoutManager(this)
            module = intent.extras?.getParcelable("module_selected")!!
            lecture = intent.extras?.getParcelable("lecture_selected")!!
            student = intent.extras?.getParcelable("student_logged_in")!!
            var attendance = app.attendance.moduleSignIns(module,lecture)

            i("module id is ${module.id}")
            i("$attendance")
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = SignInAdapter(attendance)

        }
    }

    //Ensure new sign-ins show in list
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Creates Main Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Controls main menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.all_modules -> {
                val launcherIntent = Intent(this, ModuleListActivity::class.java)
                launcherIntent.putExtra("student_logged_in", student)
                startActivityForResult(launcherIntent,0)
            }
            R.id.my_modules -> {
                val launcherIntent = Intent(this, ModuleListActivity::class.java)
                launcherIntent.putExtra("student_logged_in", student)
                startActivityForResult(launcherIntent,0)
            }
            R.id.my_account -> {
                val launcherIntent = Intent(this, CreateAccountActivity::class.java)
                launcherIntent.putExtra("student_edit", student)
                startActivityForResult(launcherIntent,0)
            }
            R.id.logout -> {
                val launcherIntent = Intent(this, LaunchActivity::class.java)
                launcherIntent.removeExtra("student_logged_in")
                launcherIntent.removeExtra("module_selected")
                launcherIntent.removeExtra("lecture_selected")

                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}