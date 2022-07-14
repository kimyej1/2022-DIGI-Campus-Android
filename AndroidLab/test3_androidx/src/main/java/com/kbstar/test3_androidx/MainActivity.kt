package com.kbstar.test3_androidx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.google.android.material.tabs.TabLayoutMediator
import com.kbstar.test3_androidx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    // 뷰페이져를 위한 어댑터
    class MyFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        val fragments: List<Fragment>
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun onBindViewHolder(
            holder: FragmentViewHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar의 내용이 개발자 view인 toolbar에 적용돼라
        setSupportActionBar(binding.toolbar)

        // fragment
//        val fragment = OneFragment()
//        val manager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.add(R.id.fragment_content, fragment)
//        transaction.commit()
        binding.viewpager.adapter = MyFragmentAdapter(this)

        // ------ TabLayout ------
        // 뷰페이저 연동 준비
        TabLayoutMediator(binding.tabs, binding.viewpager) {
            tab, position -> tab.text = "Tab${position}"
        }.attach()

        // toggle 생성
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_open, R.string.drawer_close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 유저가 손으로 끌어서 열거나 닫거나, 토글로 열거나 닫거나 -> 둘이 상호 연동
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /*
        메뉴 이벤트 처리하는 함수
            toggle이 개발자가 메뉴로 추가한 건 아니지만, action bar에 아이콘으로 나오는 것
            -> 내부적으로 메뉴 취급
            -> 유저가 클릭하면 메뉴이벤트를 탄다
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // toggle이 눌렸다면 일반적인 메뉴 이벤트를 타지 않도록!!!!
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}