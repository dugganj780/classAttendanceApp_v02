package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.databinding.ActivityModuleBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.ModuleModel
import org.wit.classattendanceapp.adapters.LectureAdapter
import org.wit.classattendanceapp.adapters.LectureListener
import org.wit.classattendanceapp.models.LectureModel
import org.wit.classattendanceapp.models.StudentModel
import timber.log.Timber.i

class ModuleActivity : AppCompatActivity(), LectureListener{

    private lateinit var binding: ActivityModuleBinding
    var module = ModuleModel()
    var student = StudentModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        app = application as MainApp

        i("Class Activity started...")

        //Uses Intent to display lectures associated with Module
        if (intent.hasExtra("module_selected")) {
            val layoutManager = LinearLayoutManager(this)
            module = intent.extras?.getParcelable("module_selected")!!
            if (intent.hasExtra("student_logged_in")){
                student = intent.extras?.getParcelable("student_logged_in")!!
            }
            i("module id is ${module.id}")
            binding.toolbarAdd.title = module.moduleCode
            setSupportActionBar(binding.toolbarAdd)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = LectureAdapter(app.modules.findLectures(module.id),this)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

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

    override fun onLectureClick(lecture: LectureModel){
        val launcherIntent = Intent(this, LectureActivity::class.java)
        launcherIntent.putExtra("lecture_selected", lecture)
        launcherIntent.putExtra("module_selected", module)
        launcherIntent.putExtra("student_logged_in", student)
        startActivityForResult(launcherIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}