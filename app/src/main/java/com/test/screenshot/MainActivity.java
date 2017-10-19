package com.test.screenshot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = ScreenShotUtils.shotBitmap(MainActivity.this);
                if (result) {
                    Toast.makeText(MainActivity.this, "截图成功.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "截图失败.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
