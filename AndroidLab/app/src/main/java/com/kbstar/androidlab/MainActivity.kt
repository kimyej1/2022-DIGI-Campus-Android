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
import com.kbstar.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /*
        ViewBinding 은 tool, code generator 이다 (gradle에 선언 필요)
        view 선언과 view 획득을 위한 findViewById 코드를 자동으로 만들어준다

        layout.xml을 보고..
        activity_main.xml ===> ActivityMainBinding 클래스를 자동으로 만들고 그곳에!
     */

//    lateinit var nameView : EditText
//    lateinit var phoneView : EditText
//    lateinit var saveButton : Button
    var user : User?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding 객체 획득 필요 (최소한의 준비)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_main)

        // findViewById 없이 만들어주는 binding 사용
        binding.saveButton.setOnClickListener {
            val name = binding.nameView.text.toString()
            val phone = binding.editTextPhone.text.toString()
            user = User(name, phone, User.USER);

            Toast.makeText(this@MainActivity, "save OK", Toast.LENGTH_SHORT).show()
        }

//        nameView = findViewById(R.id.nameView)
//        phoneView = findViewById(R.id.editTextPhone)
//        saveButton = findViewById(R.id.saveButton)


        // 요렇게 써도됨 (Single Abstract Method), 주로 이렇게 씀..
//        saveButton.setOnClickListener {
//            val name = nameView.text.toString()
//            val phone = phoneView.text.toString()
//            user = User(name, phone, User.USER);
//
//            Toast.makeText(this@MainActivity, "save OK", Toast.LENGTH_SHORT).show()
//        }
        // 원래 이거
//        saveButton.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                val name = nameView.text.toString()
//                val phone = phoneView.text.toString()
//                user = User(name, phone, User.USER);
//
//                Toast.makeText(this@MainActivity, "save OK", Toast.LENGTH_SHORT).show()
//            }
//        })
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