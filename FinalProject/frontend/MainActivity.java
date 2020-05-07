package com.example.matchwich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createAccountButton();
    }

    private void createAccountButton() {
        Button creationButton = findViewById(R.id.createAccountButton);
        creationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Account Created!",Toast.LENGTH_SHORT).show();

                Intent intent = MatchingActivity.makeIntent(MainActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }
}
