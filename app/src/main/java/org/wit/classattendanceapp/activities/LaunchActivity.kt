package org.wit.classattendanceapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.classattendanceapp.databinding.ActivityLaunchBinding
import org.wit.classattendanceapp.main.MainApp

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLaunch.title = title
        setSupportActionBar(binding.toolbarLaunch)

        app = application as MainApp

        //If existing user, user clicks to go to Login
        binding.btnLogin.setOnClickListener {
            val launcherIntent = Intent(this, LoginActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }

        //If new user, clicks to go to Create Account Screen
        binding.btnCreateAccount.setOnClickListener {
            val launcherIntent = Intent(this, CreateAccountActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }
    }
}