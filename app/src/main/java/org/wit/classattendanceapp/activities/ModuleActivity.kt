package org.wit.classattendanceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.classattendanceapp.R
import org.wit.classattendanceapp.databinding.ActivityMainBinding
import org.wit.classattendanceapp.main.MainApp
import org.wit.classattendanceapp.models.ModuleModel
import timber.log.Timber
import timber.log.Timber.i

class ModuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var module = ModuleModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Class Activity started...")

        if (intent.hasExtra("module_edit")) {
            module = intent.extras?.getParcelable("module_edit")!!
            binding.moduleTitle.setText(module.title)
            binding.moduleCode.setText(module.moduleCode)
        }

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
}