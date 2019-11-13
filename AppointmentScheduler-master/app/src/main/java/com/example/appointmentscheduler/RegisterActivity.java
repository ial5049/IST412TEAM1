package com.example.appointmentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity  {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    Button registerButton;

    String firstNameValue;
    String lastNameValue;
    String emailValue;
    String passwordValue;
    String confirmPasswordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameInput = findViewById(R.id.registerFirstName);
        lastNameInput = findViewById(R.id.registerLastName);
        emailInput = findViewById(R.id.registerEmail);
        passwordInput = findViewById(R.id.registerPassword);
        confirmPasswordInput = findViewById(R.id.registerConfirmPassword);
        registerButton = findViewById(R.id.registerButton);



//        registerButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                RegisterUser registerUser = new RegisterUser();
//                registerUser.execute("");
//            }
//        });

    }

    public void startAsyncTask(View v) {

    }

    public class RegisterUser extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {




            return "Test";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }


}
