package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.adapters.ModuleAdapter
import org.wit.classattendanceapp.databinding.ActivityModuleSelectionBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.adapters.ModuleSelectionAdapter
import org.wit.classattendanceapp.adapters.ModuleSelectionListener
import org.wit.classattendanceapp.models.ModuleModel
import org.wit.classattendanceapp.models.StudentModel

class ModuleSelectionActivity : AppCompatActivity(), ModuleSelectionListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityModuleSelectionBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var student = StudentModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityModuleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "All Modules"
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        if (intent.hasExtra("student_logged_in")){
            student = intent.extras?.getParcelable("student_logged_in")!!
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ModuleSelectionAdapter(app.modules.findAll(),this)
        loadModules()
        registerRefreshCallback()

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

    override fun onModuleCheck(view: View){
        val launcherIntent = Intent(this, ModuleActivity::class.java)
        launcherIntent.putExtra("module_checked", module)
        launcherIntent.putExtra("student_logged_in", student)
        startActivityForResult(launcherIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadModules(){
        showModules(app.modules.findAll())
    }

    fun showModules (modules: List<ModuleModel>) {
        binding.recyclerView.adapter = ModuleSelectionAdapter(modules, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadModules() }
    }
}
