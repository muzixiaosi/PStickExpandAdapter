package com.peng.pstickexpandadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hello, 点击OptionsMenu跳转", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adapter_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_stick:
                startActivity(new Intent(MainActivity.this, SampleStickActivity.class));
                Toast.makeText(this, "action_stick", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_stick_expand:
                startActivity(new Intent(MainActivity.this, SampleStickExpandActivity.class));
                Toast.makeText(this, "action_stick_expand", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_stick_expand_complex:
                startActivity(new Intent(MainActivity.this, SampleStickExpandComplexActivity.class));
                Toast.makeText(this, "action_stick_expand_complex", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
