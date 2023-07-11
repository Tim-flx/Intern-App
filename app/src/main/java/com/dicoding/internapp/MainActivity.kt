package com.dicoding.internapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.name_edit_text)
        val sentenceEditText = findViewById<EditText>(R.id.sentence_edit_text)
        val checkButton = findViewById<Button>(R.id.check_button)
        val nextButton = findViewById<Button>(R.id.next_button)

        checkButton.setOnClickListener {
            val sentence = sentenceEditText.text.toString()
            val message = if (isPalindrome(sentence)) "isPalindrome" else "not palindrome"
            AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }

        nextButton.setOnClickListener {
            name = nameEditText.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    fun isPalindrome(str: String): Boolean {
        val reversed = str.reversed()
        return str.equals(reversed, ignoreCase = true)
    }

    companion object {
        var name: String? = null
    }
}

