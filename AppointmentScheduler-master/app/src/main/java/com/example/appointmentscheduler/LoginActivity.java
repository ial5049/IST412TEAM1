package com.example.appointmentscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;
    Button loginButton;

    String enteredEmail;
    String enteredPassword;

    List<String> emailAndPasswordResults = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.loginEmail);
        passwordInput = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        emailInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enteredEmail = emailInput.getText().toString();
                enteredPassword = passwordInput.getText().toString();

                startAsyncTask(enteredEmail);

            }
        });
    }

    /**
     * Switch to register activity
     * @param view
     */
    public void switchToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Watch for user input in email and password text boxes
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    /**
     * Enable/disable login button
     */
    private void checkFieldsForEmptyValues(){

        String emailField = emailInput.getText().toString();
        String passwordField = passwordInput.getText().toString();

        if(emailField.equals("")|| passwordField.equals("")){
            loginButton.setEnabled(false);
        } else {
            loginButton.setEnabled(true);
        }
    }

    /**
     * Triggers Async Task for login process
     * @param userEmail Email to query credentials against
     */
    public void startAsyncTask(String userEmail) {
        LoginAsyncTask task = new LoginAsyncTask(this);
        task.execute(userEmail);
    }

    /**
     * Async Task for processing login credentials.
     * Launches Patient dashboard if valid, displays toast if invalid.
     */
    public static class LoginAsyncTask extends AsyncTask<String, Void, List<String>> {

        private WeakReference<LoginActivity> loginActivityWeakReference;

        LoginAsyncTask(LoginActivity loginActivity) {
            loginActivityWeakReference = new WeakReference<>(loginActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<String> doInBackground(String... strings) {

            List<String> queryResults = new ArrayList<>();

            String connectionUrl = "jdbc:jtds:sqlserver://psuabist412team1.database.windows.net:1433/AppointmentScheduler;"
                    + "database=AppointmentScheduler;"
                    + "user=ist412team1;"
                    + "password=psuabington1!;"
                    + "encrypt=false;"
                    + "trustServerCertificate=false;"
                    + "hostNameInCertificate=*.database.windows.net;"
                    + "loginTimeout=30;";

            ResultSet resultSet = null;

            try {
                Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();


                String selectSql = "SELECT * FROM User_Logins WHERE email='" + strings[0] + "';";
                resultSet = statement.executeQuery(selectSql);

                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    queryResults.add(email);
                    queryResults.add(password);
                }
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return queryResults;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            LoginActivity loginActivity = loginActivityWeakReference.get();
            if (loginActivity == null || loginActivity.isFinishing()) {
                return;
            }

            if (strings.size() == 0) {
                Toast.makeText(loginActivity.getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_LONG).show();
                return;
            }

            loginActivity.emailAndPasswordResults.addAll(strings);

//            if (loginActivity.emailAndPasswordResults.get(0).equals(loginActivity.enteredEmail)) {
                if (loginActivity.emailAndPasswordResults.get(1).equals(loginActivity.enteredPassword)) {
                    Intent intent = new Intent(loginActivity.getApplicationContext(), PatientDashboardActivity.class);
                    loginActivity.startActivity(intent);
                } else {
//            } else {
                Toast.makeText(loginActivity.getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_LONG).show();
            }
        }
    }
}