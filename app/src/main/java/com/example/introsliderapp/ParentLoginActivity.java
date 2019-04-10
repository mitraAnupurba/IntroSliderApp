package com.example.introsliderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParentLoginActivity extends AppCompatActivity {

    Button  parentSignUpButton;
    TextView parentSignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        parentSignUpButton = findViewById(R.id.parent_sign_up_button);
        parentSignUpTextView = findViewById(R.id.parent_sign_up_text_view);

        parentSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLoginActivity.this,ParentSignUp.class));
            }
        });
    }

    public void loadForgetPassword(View view) {
    }


}
