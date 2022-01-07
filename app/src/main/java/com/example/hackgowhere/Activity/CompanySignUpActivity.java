package com.example.hackgowhere.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hackgowhere.Model.User;
import com.example.hackgowhere.Model.UserNameLogin;
import com.example.hackgowhere.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class CompanySignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signUp;
    private EditText editTextEmail, editTextCompanyName, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_sign_up);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        signUp = (Button) findViewById(R.id.signup);
        signUp.setOnClickListener(this);

        editTextCompanyName = (EditText) findViewById(R.id.org_name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //restrict username to alphanumeric characters only
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signup:
                signUp();
                break;
        }
    }

    private void signUp() {
        String username = editTextCompanyName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email address");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            editTextPassword.setError("Password must contain minimum 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("UsernameList").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editTextEmail.setError("Username already taken");
                    editTextEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(email, username, password, mAuth.getCurrentUser().getUid(), "offline", "default", true);
                                        ref.child(mAuth.getCurrentUser().getUid()).setValue(user);
                                        //FirebaseDatabase.getInstance().getReference("UsernameList").child(username).setValue(new UserNameLogin(email,password));

                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(CompanySignUpActivity.this, "User has been registered successfully." +
                                                            "Please check your email for your verification link", Toast.LENGTH_LONG).show();
                                                    mAuth.signOut();
                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity(new Intent(CompanySignUpActivity.this, LoginActivity.class));
                                                } else {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(CompanySignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(CompanySignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}