package com.example.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText etNewusername;
    private EditText etNewpassword;
    private Button btnDone;
    private EditText etConfrimnewpassword;
    private EditText etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etNewusername = findViewById(R.id.etNewusername);
        etNewpassword = findViewById(R.id.etNewpassword);
        btnDone = findViewById(R.id.btnDone);
        etConfrimnewpassword = findViewById(R.id.etConfirmnewpassword);
        etEmail = findViewById(R.id.etEmail);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(etNewusername.getText().toString());
        String Newpass = etNewpassword.getText().toString();
        String Confrimpass = etConfrimnewpassword.getText().toString();
        if (Newpass.equals(Confrimpass)) {
            user.setPassword(Newpass);
        }
        else {
            Toast.makeText(SignupActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
        }
        user.setEmail(etEmail.getText().toString());
// Set custom properties
        //user.put("phone", "650-253-0000");
// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "Successful Signup!", Toast.LENGTH_SHORT).show();
                    etConfrimnewpassword.setText("");
                    etEmail.setText("");
                    etNewusername.setText("");
                    etNewpassword.setText("");

                    // Hooray! Let them use the app now.
                    goLoginActivity();
                } else {
                    Log.e(TAG, "Error signing up");
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

            }
        });
    }

    private void goLoginActivity() {
        Log.e(TAG, "Navigating to LoginActivity");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
