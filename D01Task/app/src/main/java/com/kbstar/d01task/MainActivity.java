package com.kbstar.d01task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast("onCreate()");

        button = findViewById(R.id.button);
        textName = findViewById(R.id.textName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Flag 없이 나 자신만 계속 띄움(back 눌러보면 stack에 쌓인거 볼 수 있음)
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                /*
                    AndroidManifest.xml 에서 launch Mode = single Top 으로 해주면
                    여러번 뜨지 않음(중복이라.. back 해보면 뒤로 안가고 바로 꺼짐)
                 */

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                /*
                    새 창 띄워보면 원래 창(Main Activity)은 pause -> stop 됨
                    새 창에서 back 눌러서 돌아오면 Main은 Start -> Resume
                 */
                startActivity(intent);
            }
        });
    }

    /*
        ◼︎ Turn On Activity
            Create -> Start -> Resume
        ︎◼︎︎︎ ︎Pause (start new Activity etc..)
            Pause -> Resume
        ◼︎ Turn Off Activity
            Pause -> Stop -> Destroy

        * Stop : 화면에서 가려지는거(사용자에게 더이상 보이지 않음)
        * Resume : 다른 액티비티를 시작하려고 할 때 호출
        -> 둘 다 그냥 멈추는거...라고 이해해도 됨!

        * Program : 실행파일
        * Process : 돌고있는(실행중인) 프로그램
     */

    @Override
    protected void onStart() {
        super.onStart();
        showToast("onStart()");

        saveState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("onDestroy()");

        clearState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("onResume()");

        restoreState();
    }

    private void saveState() {
        SharedPreferences sp = getSharedPreferences("sharedKey", Activity.MODE_PRIVATE);
        // MODE_PRIVATE : 내가 필요한 프로세스끼리만 공유하고(앱 내에서만 공유), 다른 프로세스는 못보게 하겠다.

//        if(sp != null && sp.contains("name")) {           // ◼︎ 있든 없든 무조건 저장!
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", textName.getText().toString());
            // name 에다가 textName 값 저장해줘~
            editor.commit();
            // commit : transaction(all or nothing), 중간에 에러나면 0으로 원복하고, 아니면 끝까지 해라~
//        }
    }

    private void restoreState() {
        SharedPreferences sp = getSharedPreferences("sharedKey", Activity.MODE_PRIVATE);

        if(sp != null && sp.contains("name")) {             // ◼︎ 데이터가 있으면 복구!
//            SharedPreferences.Editor editor = sp.edit();  // write 할게 아니니까 edit 불필요(read only)
            String name = sp.getString("name", "");
            /*
                .getString(String key, Set<String> defaultValues)
                    : String을 찾는데 없어서 디폴트로 null 이런게 들어오면 오류날 수도 있으니까,
                      default값으로 "" 빈 값을 주기로 함.
             */
            textName.setText(name);
            showToast("restored Name = " + name);
        }
    }

    private void clearState() {
        SharedPreferences sp = getSharedPreferences("sharedKey", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}