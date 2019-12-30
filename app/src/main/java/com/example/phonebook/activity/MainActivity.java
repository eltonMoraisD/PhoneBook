package com.example.phonebook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phonebook.R;

public class MainActivity extends AppCompatActivity {
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContinue = findViewById(R.id.buttonContinue);

    }

    public void abrirListaConctact(View view){
        startActivity(new Intent(this,ContactActivity.class));
    }
}
