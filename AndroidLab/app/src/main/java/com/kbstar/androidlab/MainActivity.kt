package com.kbstar.androidlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var nameView : EditText
    lateinit var phoneView : EditText
    lateinit var saveButton : Button
    var user : User?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameView = findViewById(R.id.nameView)
        phoneView = findViewById(R.id.editTextPhone)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val name = nameView.text.toString()
                val phone = phoneView.text.toString()
                user = User(name, phone, User.USER);

                Toast.makeText(this@MainActivity, "save OK", Toast.LENGTH_SHORT)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_print) {
            user?.run {
                // not null case
                Log.d("", "name : ${user?.name}, phone : ${user?.phone}, role : ${user?.role}")
            } ?: kotlin.run {
                // null case
                Log.d("", "No Data...")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}