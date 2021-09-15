package com.example.mappe1apputvikling_s188886_s344105;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FrontPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);
    }

    public void visPreferanser(View v) {
        Intent intent = new Intent(this, Preferanser.class);
        startActivity(intent);
    }
}