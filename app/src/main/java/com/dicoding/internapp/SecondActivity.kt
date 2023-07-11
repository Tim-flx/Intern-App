package com.dicoding.internapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        toolbar = findViewById(R.id.custom_toolbar)

        setSupportActionBar(toolbar)

        val backButton = toolbar.findViewById<ImageView>(R.id.back_button)
        val titleText = toolbar.findViewById<TextView>(R.id.title_text)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        titleText.text = "Second Screen"

        val welcomeTextView = findViewById<TextView>(R.id.welcome_text_view)
        val nameTextView = findViewById<TextView>(R.id.name_text_view)
        val selectedUserNameTextView = findViewById<TextView>(R.id.selected_user_name_text_view)
        val chooseUserButton = findViewById<Button>(R.id.choose_user_button)

        val name = MainActivity.name
        nameTextView.text = name

        val userName = intent.getStringExtra("user_name")
        if (userName != null) {
            selectedUserNameTextView.text = userName
        }

        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}
