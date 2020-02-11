package com.tpappsmoviles.serviapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


import com.tpappsmoviles.serviapp.R;


public class Login extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
          // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

            final EditText usernameEditText = findViewById(R.id.username);
            final EditText passwordEditText = findViewById(R.id.password);
            final Button loginButton = findViewById(R.id.login);
            final ProgressBar loadingProgressBar = findViewById(R.id.loading);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    Intent i1 = new Intent(v.getContext(),MainActivity.class);
                    startActivity(i1);
                }
            });
        }


    }
