package com.example.social_media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.social_media.databinding.ActivityCommentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.UUID;

public class CommentActivity extends AppCompatActivity {
    ActivityCommentBinding binding;
    private String postId;
    private CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postId=getIntent().getStringExtra("id");

        commentAdapter=new CommentAdapter(this);
        binding.commentRecycler.setAdapter(commentAdapter);
        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        loadcomments();

        binding.sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=binding.commenttype.getText().toString();
                if(comment.trim().length()>0){
                    comment(comment);
                }
            }
        });
    }

    private void loadcomments(){
        FirebaseFirestore.getInstance()
                .collection("Comment")
                .whereEqualTo("postId",postId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        commentAdapter.clearPosts();
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList){
                            CommentModel commentModel=ds.toObject(CommentModel.class);
                            commentAdapter.addPost(commentModel);
                        }
                    }
                });
    }
    private void comment(String comment){
        String id= UUID.randomUUID().toString();
        CommentModel commentModel=new CommentModel(id,postId, FirebaseAuth.getInstance().getUid(),comment);
        FirebaseFirestore.getInstance().collection("Comment")
                .document(id)
                .set(commentModel);
        commentAdapter.addPost(commentModel);
    }
}