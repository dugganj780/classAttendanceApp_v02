package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.databinding.ActivityModuleBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.ModuleModel
import org.wit.classattendanceapp.adapters.LectureAdapter
import org.wit.classattendanceapp.adapters.LectureListener
import org.wit.classattendanceapp.adapters.ModuleAdapter
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
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Class Activity started...")

        if (intent.hasExtra("module_selected")) {
            val layoutManager = LinearLayoutManager(this)
            module = intent.extras?.getParcelable("module_selected")!!
            if (intent.hasExtra("student_logged_in")){
                student = intent.extras?.getParcelable("student_logged_in")!!
            }
            i("module id is ${module.id}")
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = LectureAdapter(app.modules.findLectures(module.id),this)
        }

        /*
        binding.btnAdd.setOnClickListener() {
            module.moduleCode = binding.moduleCode.text.toString()
            module.title = binding.moduleTitle.text.toString()

            if (module.moduleCode.isNotEmpty()){
                app.modules.create(module.copy())
                i("add Button Pressed:  ${module.moduleCode}: ${module.title}")
                setResult(RESULT_OK)
                finish()
            }
            else{
                Snackbar
                    .make(it, "Please Enter a module Code", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
     */

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_module, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
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