package com.example.matchwich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MatchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);


        matchButtonFuntionality();
        passButtonFunctionality();

        changeViewsButton();

    }

    private void passButtonFunctionality() {
        Button pass = findViewById(R.id.passButton);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass match
            }
        });
    }

    private void matchButtonFuntionality() {
        Button match = findViewById(R.id.matchButton);
        match.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MatchingActivity.this,"You have a new Match!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changeViewsButton() {
        Button changePage = findViewById(R.id.viewPageButton);
        changePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentView = ViewingActivity.makeIntent(MatchingActivity.this);
                startActivity(intentView);
            }
        });
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MatchingActivity.class);
    }



}
