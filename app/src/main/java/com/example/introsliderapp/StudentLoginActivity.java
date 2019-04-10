package com.example.introsliderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentLoginActivity extends AppCompatActivity {

    Button studentloginButton;
    TextView studentSignUpTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentloginButton = this.findViewById(R.id.button_student_login);
        studentSignUpTextView = this.findViewById(R.id.student_sign_up_textview);


        studentSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this,StudentSignUp.class));
            }
        });


    }



    public void loadForgetPassword(View view) {
    }
}
