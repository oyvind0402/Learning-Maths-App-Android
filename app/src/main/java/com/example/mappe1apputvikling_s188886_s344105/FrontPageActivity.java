package com.example.mappe1apputvikling_s188886_s344105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FrontPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button button = findViewById(R.id.start_spill);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Spillet.class);
            startActivity(intent);
        });
    }

    public void visPreferanser(View v) {
        Intent intent = new Intent(this, Preferanser.class);
        startActivity(intent);
    }
}