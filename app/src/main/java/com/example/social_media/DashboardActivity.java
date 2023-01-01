package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.social_media.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

        ActivityDashboardBinding binding;
        private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        FirebaseFirestore.getInstance()
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel userModel=documentSnapshot.toObject(UserModel.class);
                        if(userModel.getUserProfile()!=null){
                            Glide.with(DashboardActivity.this).load(userModel.getUserProfile()).into(binding.circleImageview);
                        }
                    }
                });

        binding.circleImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,ProfileActivity.class);
                intent.putExtra("id",FirebaseAuth.getInstance().getUid());
                startActivity(intent);
            }
        });

        postAdapter=new PostAdapter(this);
        binding.recyclerPost.setAdapter(postAdapter);
        binding.recyclerPost.setLayoutManager(new LinearLayoutManager(this));
        loadPosts();

        binding.createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,PostActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
            return true;
        }
        return false;
    }

    private void loadPosts(){
        FirebaseFirestore.getInstance()
                .collection("Posts")
                // Post in descending order as per post time
                .orderBy("posttime", Query.Direction.DESCENDING)
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
                        Toast.makeText(DashboardActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}