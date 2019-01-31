package com.example.nalepsky.engineers_thesis_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button selectorButton;
    Button aboutButton;
    Button savedSelectorsButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        selectorButton = (Button) findViewById(R.id.create_army_list_button);
        aboutButton = (Button) findViewById(R.id.about_button);
        savedSelectorsButton = (Button) findViewById(R.id.saved_selectors_button);
        exitButton = (Button) findViewById(R.id.exit_button);


        selectorButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChooseSelectorActivity.class);
                startActivity(i);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
            }
        });
        savedSelectorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SavedSelectorsActivity.class);
                startActivity(i);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });
    }
}
