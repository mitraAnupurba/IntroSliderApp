package com.example.introsliderapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.introsliderapp.Model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {

    Button studentloginButton;
    TextView studentSignUpTextView;
    EditText studentLoginEmailAddress, studentLoginPassword;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);


        initViews();
        initDbObjects();

        studentloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(studentLoginEmailAddress.getText().toString(),studentLoginPassword.getText().toString());
            }
        });


        studentSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this,StudentSignUp.class));
            }
        });


    }

    private void initViews() {
        studentloginButton = this.findViewById(R.id.button_student_login);
        studentSignUpTextView = this.findViewById(R.id.student_sign_up_textview);
        studentLoginEmailAddress = this.findViewById(R.id.student_login_email_address_editText);
        studentLoginPassword = this.findViewById(R.id.student_login_password_editText);

    }

    private void initDbObjects(){
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child("student");
    }

   private void signIn(final String emailAddress, final String password){



        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(emailAddress).exists()){
                    if(!emailAddress.isEmpty()){
                        Student login = dataSnapshot.child(emailAddress).getValue(Student.class);
                        if(login.getPasswordStudent().equals(password)) {
                            Toast.makeText(StudentLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentLoginActivity.this,StudentAccount.class));
                        }
                        else{
                            Toast.makeText(StudentLoginActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(StudentLoginActivity.this,"Please enter Email Id",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(StudentLoginActivity.this,"User with this email doesnot exist, sign up first",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void loadForgetPassword(View view) {
    }
}
