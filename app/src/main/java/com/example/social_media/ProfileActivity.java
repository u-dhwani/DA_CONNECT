package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.social_media.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    private PostAdapter postAdapter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postAdapter=new PostAdapter(this);
        binding.postRecyclerProfile.setAdapter(postAdapter);
        binding.postRecyclerProfile.setLayoutManager(new LinearLayoutManager(this));
        userId=getIntent().getStringExtra("id");
        loaduserdata();
        loadPosts();
    }
    private void loaduserdata(){

        binding.usernameProfile.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel userModel=documentSnapshot.toObject(UserModel.class);
                        if(userModel.getUserBio()!=null){
                            binding.userbio.setText(userModel.getUserBio());
                        }
                        else{
                            binding.userbio.setText(R.string.bio);
                        }
                        if(userModel.getUserProfile()!=null){
                            Glide.with(ProfileActivity.this).load(userModel.getUserProfile())
                                    .into(binding.userImageProfile);
                        }
                        if(userModel.getUserCover()!=null){
                            Glide.with(ProfileActivity.this).load(userModel.getUserCover())
                                    .into(binding.cover);
                        }
                    }
                });
    }
    private void loadPosts(){
        FirebaseFirestore.getInstance()
                .collection("Posts")
                .whereEqualTo("userId",userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        postAdapter.clearPosts();
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList){
                            PostModel postModel=ds.toObject(PostModel.class);
                            postAdapter.addPost(postModel);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}