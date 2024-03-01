package ru.mirea.tepsikoevsa.firebaseauth;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.tepsikoevsa.firebaseauth.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        binding.editTextEmail.setText("exampla@gmail.com");
        binding.editTextPassword.setText("12345678");

        // Начально устанавливаем видимость кнопки выхода как GONE
        binding.buttonSignOut.setVisibility(View.GONE);

        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString());
            }
        });

        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString());
            }
        });

        binding.buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MainActivity.this, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MainActivity.this, "Registration succeeded.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Пользователь вошел в систему
            user = mAuth.getCurrentUser();
            if (user != null) {
                String userInfo = "Email User: " + user.getEmail() + "\n"
                        + "Email Verification Status: " + user.isEmailVerified() + "\n"
                        + "Firebase UID: " + user.getUid();
                binding.signOut.setText(userInfo);
            }
            binding.buttonSignIn.setVisibility(View.GONE);
            binding.buttonSignUp.setVisibility(View.GONE);
            binding.editTextEmail.setVisibility(View.GONE);
            binding.editTextPassword.setVisibility(View.GONE);
            binding.buttonSignOut.setVisibility(View.VISIBLE);
        } else {
            // Пользователь вышел из системы
            binding.signOut.setText("Signed Out");
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.buttonSignUp.setVisibility(View.VISIBLE);
            binding.editTextEmail.setVisibility(View.VISIBLE);
            binding.editTextPassword.setVisibility(View.VISIBLE);
            binding.buttonSignOut.setVisibility(View.GONE);
        }
    }
}