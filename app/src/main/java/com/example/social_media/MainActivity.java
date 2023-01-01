package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.social_media.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(view -> {

            String name=binding.name.getText().toString();
            String number=binding.number.getText().toString();
            String email=binding.email.getText().toString();
            String password=binding.password.getText().toString();

             progressDialog=new ProgressDialog(MainActivity.this);
             progressDialog.setTitle("SIGNING UP");
             progressDialog.setMessage("Creating An Account!!! Please Wait For Some Time...");
             progressDialog.show();

//                Firebase authorization for E-mail and password

            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.trim(),password.trim())
                    .addOnSuccessListener(authResult -> {
                        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseAuth.getInstance().getCurrentUser()
                                .updateProfile(userProfileChangeRequest);
                        new MySharedPreferences(MainActivity.this).setData(number);

                        // to store data in fire store create user model
                        // Calling UserModel
                        UserModel userModel=new UserModel();

                        // Filling data in user model
                        userModel.setUserName(name);
                        userModel.setUserNumber(number);
                        userModel.setUserEmail(email);

                        // Uploading on fire store
                        FirebaseFirestore
                                .getInstance()
                                .collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(userModel);

                        reset();


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        binding.signupSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void reset() {
        progressDialog.cancel();
        Toast.makeText(this,"Registered Successfully...PLease Log In",Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();

    }
}