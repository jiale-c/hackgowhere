package com.example.hackgowhere.Activity;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hackgowhere.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompanyLoginActivity extends AppCompatActivity {
    EditText et_username, et_password;
    TextView reset_password;
    String username, password, email;
    Button loginButton, googleLoginButton, signUpButton;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        et_username = findViewById(R.id.editText_username);
        et_password = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        signUpButton = findViewById(R.id.button_signup);
        reset_password = findViewById(R.id.textView_forgot_password);
        progressBar = findViewById(R.id.login_progressbar);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Users");

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

        //et_username.setFilters(new InputFilter[]{filter});

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Please enter your email below to receive your password reset link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract email and send reset link

                        email = resetMail.getText().toString();
                        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    SignInMethodQueryResult result = task.getResult();
                                    List<String> signInMethods = result.getSignInMethods();
                                    if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getBaseContext(), "Reset Link Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(getBaseContext(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(CompanyLoginActivity.this, "Account does not exists!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyLoginActivity.this, CompanySignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().isEmpty()) {
                    et_username.setError("Email Address is missing");
                    return;
                }

                if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Password is Missing");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(et_username.getText().toString().trim()).matches()) {
                    FirebaseDatabase.getInstance().getReference("UsernameList").
                            child(et_username.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String email_db = snapshot.child("email").getValue(String.class);
                                String password_db = snapshot.child("password").getValue(String.class);
                                LOGIN(email_db, password_db);
                            } else {
                                Toast.makeText(CompanyLoginActivity.this, "Username does not exist in the database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }

                LOGIN(et_username.getText().toString().trim(), et_password.getText().toString().trim());
            }
        });
    }

    private void LOGIN(String username, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (!mAuth.getCurrentUser().isEmailVerified()) {
                        new android.app.AlertDialog.Builder(CompanyLoginActivity.this)
                                .setCancelable(true)
                                .setTitle("Email is not verified")
                                .setMessage("You have not verified your email \n Resend email verification link?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mAuth.signOut();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mAuth.getCurrentUser().sendEmailVerification();
                                        new android.app.AlertDialog.Builder(CompanyLoginActivity.this)
                                                .setCancelable(true)
                                                .setTitle("Link successfully sent")
                                                .setMessage("Verification link sent to " + et_username.getText().toString().trim())
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                       mAuth.signOut();
                                                       progressBar.setVisibility(View.GONE);
                                                    }
                                                })
                                                .create()
                                                .show();
                                    }
                                })
                                .create()
                                .show();
                    } else {
                        startActivity(new Intent(CompanyLoginActivity.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CompanyLoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CompanyLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //    If user is logged in, bring user to dashboard straight
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}