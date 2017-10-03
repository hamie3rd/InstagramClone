package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    Button buttonLogin;
    Boolean buttonSaysLogin;
    TextView signupSwitch;
    EditText editTextPassword;

    public void onLogin (View view){
        EditText editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            //this checks if the user is logging in or signing up//////////////////////////////////////////////////////////
            if (buttonSaysLogin) {
                //This checks for correct login information, if right, sign in, if incorrect, throws an error message//////////
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null){
                            Toast.makeText(MainActivity.this, "Login Successful \n Welcome " + username, Toast.LENGTH_SHORT).show();
                            loginSuccessful();
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //The end of the Login process////////////////////////////////////////////////////////////////////////////////
            } else {
                //This checks the username, if a new username, create one, if not, it says the username already exists/////////////
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(MainActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                            loginSuccessful();
                        } else {
                            Toast.makeText(MainActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                            Log.i("Signin Error", e.getMessage());
                        }
                    }
                });
                //End of making a new username/password sign up///////////////////////////////////////////////////////////////////////
            }
        } else {
            Toast.makeText(MainActivity.this, "A username and password are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void switchLoginToSignup (View view){
        if (buttonSaysLogin){
            buttonLogin.setText("Sign Up");
            signupSwitch.setText("Or Login");
            buttonSaysLogin = false;
        } else {
            buttonLogin.setText("Login");
            signupSwitch.setText("Or Sign Up");
            buttonSaysLogin = true;
        }
    }

    public void loginSuccessful(){
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        signupSwitch = (TextView)findViewById(R.id.textViewLoginSwitch);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        ConstraintLayout contraintlayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        ImageView imageViewLogo = (ImageView)findViewById(R.id.imageViewLogo);
        buttonSaysLogin = true;

        editTextPassword.setOnKeyListener(this);
        contraintlayout.setOnClickListener(this);
        imageViewLogo.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            loginSuccessful();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            onLogin(v);
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.constraintLayout || v.getId() == R.id.imageViewLogo){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
