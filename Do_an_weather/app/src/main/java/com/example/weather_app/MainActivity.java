package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    static final int RC_SIGN_IN = 9001;
    static GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    RelativeLayout googleBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleBtn = findViewById(R.id.RLSignUpGoogle);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.my_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn()
    {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception e = task.getException();
            if (task.isSuccessful())
            {
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                    Log.d("LoginApp", "Login Success");
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
                catch (ApiException a) {
                    String errorMessage;
                    switch (a.getStatusCode()) {
                        case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                            errorMessage = "Đăng nhập đã bị hủy bỏ.";
                            break;
                        case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                            errorMessage = "Lỗi đăng nhập. Vui lòng thử lại sau.";
                            break;
                        case GoogleSignInStatusCodes.NETWORK_ERROR:
                            errorMessage = "Lỗi mạng. Vui lòng kiểm tra kết nối internet.";
                            break;
                        default:
                            errorMessage = "Lỗi đăng nhập không xác định.";
                    }

                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Log.w("SignInActivity", e.toString());
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}