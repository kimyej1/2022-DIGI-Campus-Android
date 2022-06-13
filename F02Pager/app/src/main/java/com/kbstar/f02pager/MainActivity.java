package com.kbstar.f02pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*
    Adapter Class
        interface 구현하려면(ex. window listener) 내가 사용 안할 코드들까지 다 implement 해야해서
        불필요한 코드가 많아질 수 있다. (모든게 abstract 인게 interface)
        이럴 때 필요한 이벤트만 처리하기 위해 Adapter Class를 구현한다. (inner class로 만듬)
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Button button;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);    // 재가동(resume) 할때만 이 페이지를 보여줘

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        adapter.addItem(fragment1);
        adapter.addItem(fragment2);
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. 특정 페이지로 이동
//                pager.setCurrentItem(1);

                // 2. 다음 페이지로 이동 (마지막페이지면 첫페이지로)
                currentPage = (pager.getCurrentItem() + 1) % pager.getOffscreenPageLimit();
                    // pager.getOffscreenPageLimit 대신 adapter.getCount() 써도 됨
                pager.setCurrentItem(currentPage);
            }
        });
    }

//    abstract class FragmentPagerAdapter {
//        abstract public void getCount();
//    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            this.items = items;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {    // CharSequence = String
            return "Page # " + position;
        }
    }

}