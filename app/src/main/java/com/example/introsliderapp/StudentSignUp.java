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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.introsliderapp.Model.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class StudentSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private EditText userNameStudentSignUp,emailStudentSignUp,passwordStudentSignUp,confirmPasswordStudentSignUp;
    private EditText phoneNumberStudentSignUp,instNameStudentSignUp;
    private TextView selectExamTextViewStudentSignUp,statusTextViewStudentSignUp,parentTextViewStudentSignUp,dobStudentSignUp;
    private RadioGroup statusRadioStudentSignUp,parentRadioStudentSignUp;
    private RadioButton yesRadioButton,noRadioButton,justEnteredRadioButton,appearingRadioButton,passedOutRadioButton;
    private Button studentSignUpButton;

    //datepicker variable :
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //LOG variables:
    private static final String TAG = "My tag";


    //database variables:
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        //initialise views
        initViews();

        //initialising database variables:
        initDBObjects();




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        //textView date picker OnCliclListener
        dobStudentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int age = calendar.get(Calendar.MILLISECOND);

                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentSignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog
                ,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.show();

            }
        });

        //initialising object of dateSetListener:
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG,"onDateSetListener :"+dayOfMonth+"/"+month+"/"+year );
                String data = dayOfMonth+"/"+month+"/"+year;
                dobStudentSignUp.setText(data);
            }
        };



        //status radio button onCheckedChangedListener:
        statusRadioStudentSignUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.passed_out_radio_button:
                        statusTextViewStudentSignUp.setText("PASSED OUT");
                        break;
                    case R.id.just_entered_radio_button:
                        statusTextViewStudentSignUp.setText("JUST ENTERED");
                        break;
                    case R.id.appearing_radio_button:
                        statusTextViewStudentSignUp.setText("APPEARING");
                        break;
                }
            }
        });

        //parent radio button onCheckedChangedListener:
        parentRadioStudentSignUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.yes_radio_button:
                        parentTextViewStudentSignUp.setText("YES");
                        break;
                    case R.id.no_radio_button:
                        parentTextViewStudentSignUp.setText("NO");
                        break;
                }
            }
        });

        studentSignUpButton.setOnClickListener(this);
    }

    //listener for Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String exam = parent.getItemAtPosition(position).toString();
        selectExamTextViewStudentSignUp.setText(exam);
        //selectExamTextView.setText(exam);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}

    public void initDBObjects(){



        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users");

    }


    public void initViews(){
        spinner = this.findViewById(R.id.select_exam_spinner_sign_up_student);

        //initialising textview:
        selectExamTextViewStudentSignUp = this.findViewById(R.id.exam_preparing_sign_up_student_textView);
        statusTextViewStudentSignUp = this.findViewById(R.id.status_sign_up_student_textView);
        parentTextViewStudentSignUp = this.findViewById(R.id.parent_account_textView);
        dobStudentSignUp = this.findViewById(R.id.dob_sign_up_student);


        //initialising radio group
        statusRadioStudentSignUp = this.findViewById(R.id.status_radio_group);
        parentRadioStudentSignUp = this.findViewById(R.id.yes_no_radio_group);

        //initialising radio button:
        yesRadioButton = this.findViewById(R.id.yes_radio_button);
        noRadioButton = this.findViewById(R.id.no_radio_button);
        justEnteredRadioButton = this.findViewById(R.id.just_entered_radio_button);
        appearingRadioButton = this.findViewById(R.id.appearing_radio_button);
        passedOutRadioButton = this.findViewById(R.id.passed_out_radio_button);

        //initialising edittext's:
        userNameStudentSignUp = this.findViewById(R.id.user_name_sign_up_student);
        emailStudentSignUp = this.findViewById(R.id.email_address_sign_up_student);
        passwordStudentSignUp = this.findViewById(R.id.password_sign_up_student);
        confirmPasswordStudentSignUp = this.findViewById(R.id.confirm_password_sign_up_student);
        phoneNumberStudentSignUp = this.findViewById(R.id.ph_no_sign_up_student);
        instNameStudentSignUp = this.findViewById(R.id.inst_name_sign_up_student);

        //initialising buttons:
        studentSignUpButton = this.findViewById(R.id.student_sign_up_button);

    }



    @Override
    public void onClick(View v) {
        String userName, email, password, confirmPassword,dob,instName, status,exam;

        boolean parentAccount;
        //Date dob;
        long phNumber;

        userName =  userNameStudentSignUp.getText().toString();
        email = emailStudentSignUp.getText().toString();
        password = passwordStudentSignUp.getText().toString();
        confirmPassword = confirmPasswordStudentSignUp.getText().toString();
        instName = instNameStudentSignUp.getText().toString();
        exam = selectExamTextViewStudentSignUp.getText().toString();
        status = statusTextViewStudentSignUp.getText().toString();
        dob =  dobStudentSignUp.getText().toString();





        try{




            if(userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ||instName.isEmpty() || exam.isEmpty() || status.isEmpty() || dob.isEmpty() || phoneNumberStudentSignUp.getText().toString().isEmpty()){
                throw new SignUpException("fields empty");
            }

            else if(!password.equals(confirmPassword)){
                throw new SignUpException("Password and confirm password do not match");
            }
            else{

                if(parentTextViewStudentSignUp.getText().toString().equals("YES")){
                    parentAccount = true;
                }
                else{
                    parentAccount = false;
                }

                phNumber =Long.parseLong(phoneNumberStudentSignUp.getText().toString());
                final Student student = new Student(userName,password,email,dob,phNumber,instName,status,exam,parentAccount);
                final DatabaseReference emailRef = mRef.child("student");

                emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(student.getEmailAddressStudent()).exists()){
                                Toast.makeText(StudentSignUp.this, "USER EXISTS< EMAIL MUST BE UNIQUE", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                emailRef.child(student.getEmailAddressStudent()).setValue(student);
                                Toast.makeText(StudentSignUp.this, "USER REGESTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                 startActivity(new Intent(StudentSignUp.this, PhoneEmailVerification.class));


            }
        }catch(SignUpException e){

                Toast.makeText(StudentSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        catch(NumberFormatException e) {
            Toast.makeText(StudentSignUp.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        finally {
            userNameStudentSignUp.setText("");
            emailStudentSignUp.setText("");
            passwordStudentSignUp.setText("");
            confirmPasswordStudentSignUp.setText("");
            instNameStudentSignUp.setText("");
            selectExamTextViewStudentSignUp.setText("The exam you are preparing for is...");
            statusTextViewStudentSignUp.setText("Enter your Status...");
            dobStudentSignUp.setText("");
            phoneNumberStudentSignUp.setText("");
            parentTextViewStudentSignUp.setText("Does your parent have an account here?");
            parentRadioStudentSignUp.clearCheck();
            statusRadioStudentSignUp.clearCheck();

        }


    }
}
