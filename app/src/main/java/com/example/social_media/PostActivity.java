package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.social_media.databinding.ActivityPostBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    ActivityPostBinding binding;
    private Uri selectedImageUri;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
       

        // Selecting image from gallery
        binding.pictureGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_post,menu);
        return true;
    }

    // getting post details
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.post){
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Posting");
            progressDialog.show();

            String id= UUID.randomUUID().toString();
            StorageReference storageReference=FirebaseStorage.getInstance().getReference("Posts/"+id+"image.png");
            if(selectedImageUri!=null){
                storageReference.putFile(selectedImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>(){
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                progressDialog.cancel();
                                                finish();
                                                Toast.makeText(PostActivity.this,"Posted",Toast.LENGTH_SHORT).show();
                                                PostModel postModel=new PostModel(id,
                                                        FirebaseAuth.getInstance().getUid(),binding.postText.getText().toString(),
                                                        uri.toString(),"0","0",
                                                        Calendar.getInstance().getTimeInMillis());

                                                FirebaseFirestore.getInstance()
                                                        .collection("Posts")
                                                        .document(id)
                                                        .set(postModel);


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.cancel();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(PostActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else{
                PostModel postModel=new PostModel(id,
                        FirebaseAuth.getInstance().getUid(),binding.postText.getText().toString(),
                        null,"0","0",
                        Calendar.getInstance().getTimeInMillis());

                FirebaseFirestore.getInstance()
                        .collection("Posts")
                        .document(id)
                        .set(postModel);

                progressDialog.cancel();
                finish();
                Toast.makeText(PostActivity.this,"Posted",Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        return false;
    }

    // Glide library for image loading from gallery

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==100){
            if(data!=null){
                selectedImageUri=data.getData();
                binding.selectedImage.setImageURI(selectedImageUri);
                Glide.with(PostActivity.this)
                        .load(selectedImageUri).into(binding.selectedImage);
            }
            else{
                Toast.makeText(this,"Image Not Selected",Toast.LENGTH_SHORT).show();
            }
        }
    }
}