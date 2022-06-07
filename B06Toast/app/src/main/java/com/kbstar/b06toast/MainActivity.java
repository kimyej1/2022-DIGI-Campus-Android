package com.kbstar.b06toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private EditText editText2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int x = Integer.parseInt( editText.getText().toString() );
                    int y = Integer.parseInt( editText2.getText().toString() );

                    Toast toast = Toast.makeText(MainActivity.this, "Toast Test", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.LEFT, x, y);  // x, y 정보를 그래비티에 반영!
                    toast.show();

                } catch (NumberFormatException e) {

                }
            }
        });
    }
}