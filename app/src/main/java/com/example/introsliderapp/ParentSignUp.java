package com.example.introsliderapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.introsliderapp.Model.Parent;
import com.example.introsliderapp.Model.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class ParentSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private EditText userNameParentSignUp,emailParentSignUp,passwordParentSignUp,confirmPasswordParentSignUp;
    private EditText phoneNumberParentSignUp,instNameParentSignUp;
    private TextView selectExamTextViewParentSignUp,childTextViewParentSignUp,dobParentSignUp;
    private RadioGroup childRadioParentSignUp;
    private Button parentSignUpButton;

    //datepicker variable :
    private DatePickerDialog.OnDateSetListener dateSetListenerParent;

    //LOG variables:
    private static final String TAG = "My tag";


    //database variables:
    private FirebaseDatabase mDatabaseParent;
    private DatabaseReference mRefParent;
    private ChildEventListener childEventListenerParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_sign_up);

        //initialise views
        initViews();

        //initialising database variables:
        initDBObjects();
        mRefParent.addChildEventListener(childEventListenerParent);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        //textView date picker OnCliclListener
        dobParentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int age = calendar.get(Calendar.MILLISECOND);
                Log.d(TAG, "age:"+String.valueOf(age));

                DatePickerDialog datePickerDialog = new DatePickerDialog(ParentSignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog
                        ,dateSetListenerParent,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.show();

            }
        });

        //initialising object of dateSetListener:
        dateSetListenerParent = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG,"onDateSetListener :"+dayOfMonth+"/"+month+"/"+year );
                String data = dayOfMonth+"/"+month+"/"+year;
                dobParentSignUp.setText(data);
            }
        };




        //parent radio button onCheckedChangedListener:
        childRadioParentSignUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.yes_radio_button:
                        childTextViewParentSignUp.setText("YES");
                        break;
                    case R.id.no_radio_button:
                        childTextViewParentSignUp.setText("NO");
                        break;
                }
            }
        });

        parentSignUpButton.setOnClickListener(this);
    }

    //listener for Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String exam = parent.getItemAtPosition(position).toString();
        selectExamTextViewParentSignUp.setText(exam);
        //selectExamTextView.setText(exam);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}

    public void initDBObjects(){


        mDatabaseParent = FirebaseDatabase.getInstance();
        mRefParent = mDatabaseParent.getReference("Users");
        childEventListenerParent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Parent parent = dataSnapshot.getValue(Parent.class);

                Log.d(TAG,"onChildAdded : Name "+ dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }


    public void initViews(){

        spinner = this.findViewById(R.id.select_exam_spinner_sign_up_parent);

        //initialising textview:
        selectExamTextViewParentSignUp = this.findViewById(R.id.exam_preparing_sign_up_parent_textView);
        childTextViewParentSignUp = this.findViewById(R.id.child_account_textView);
        dobParentSignUp = this.findViewById(R.id.dob_sign_up_parent);


        //initialising radio group

        childRadioParentSignUp = this.findViewById(R.id.yes_no_radio_group_parent);

        //initialising edittext's:
        userNameParentSignUp = this.findViewById(R.id.user_name_sign_up_parent);
        emailParentSignUp = this.findViewById(R.id.email_address_sign_up_parent);
        passwordParentSignUp = this.findViewById(R.id.password_sign_up_parent);
        confirmPasswordParentSignUp = this.findViewById(R.id.confirm_password_sign_up_parent);
        phoneNumberParentSignUp = this.findViewById(R.id.ph_no_sign_up_parent);
        instNameParentSignUp = this.findViewById(R.id.inst_name_sign_up_parent);

        //initialising buttons:
        parentSignUpButton = this.findViewById(R.id.parent_sign_up_button);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefParent.removeEventListener(childEventListenerParent);
    }

    @Override
    public void onClick(View v) {
        String userName, email, password, confirmPassword,dob,instName, status,exam;
        String key;
        boolean parentAccount;
        //Date dob;
        long phNumber;

        userName =  userNameParentSignUp.getText().toString();
        email = emailParentSignUp.getText().toString();
        password = passwordParentSignUp.getText().toString();
        confirmPassword = confirmPasswordParentSignUp.getText().toString();
        instName = instNameParentSignUp.getText().toString();
        exam = selectExamTextViewParentSignUp.getText().toString();
        dob =  dobParentSignUp.getText().toString();




        try{

            phNumber =Long.parseLong(phoneNumberParentSignUp.getText().toString());
            if(userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                    ||instName.isEmpty() || exam.isEmpty()  || dob.isEmpty() || phoneNumberParentSignUp.getText().toString().isEmpty()){
                throw new SignUpException("fields empty");
            }
            else if(!password.equals(confirmPassword)){
                throw new SignUpException("Password and confirm password do not match");
            }
            else{

                if(childTextViewParentSignUp.getText().toString().equals("YES")){
                    parentAccount = true;
                }
                else{
                    parentAccount = false;
                }
                Parent parent = new Parent(userName,password,email,dob,phNumber,instName,exam,parentAccount);

                //connecting with database:
                key = mRefParent.push().getKey();
                Log.d(TAG,key);
                mRefParent.child("parent").child(key).setValue(parent);
                startActivity(new Intent(ParentSignUp.this,PhoneEmailVerification.class));

            }
        }catch(SignUpException e){
            if(!password.equals(confirmPassword)) {
                Toast.makeText(ParentSignUp.this, "Pasword and cnfrm password dont match", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ParentSignUp.this, "fields empty", Toast.LENGTH_SHORT).show();
            }
        }
        catch(NumberFormatException e) {
            Toast.makeText(ParentSignUp.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        finally {
            userNameParentSignUp.setText("");
            emailParentSignUp.setText("");
            passwordParentSignUp.setText("");
            confirmPasswordParentSignUp.setText("");
            instNameParentSignUp.setText("");
            selectExamTextViewParentSignUp.setText("The exam you are preparing for is...");
            dobParentSignUp.setText("");
            phoneNumberParentSignUp.setText("");
            childTextViewParentSignUp.setText("Does your parent have an account here?");
        }


    }
}
