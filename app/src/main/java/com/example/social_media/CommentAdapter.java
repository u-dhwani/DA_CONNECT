package com.example.social_media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private Context context;
    private List<CommentModel> commentModelList;
    // constructor
    public CommentAdapter(Context context) {
        this.context = context;
        commentModelList=new ArrayList<>();
    }

    public void addPost(CommentModel commentModel){
        commentModelList.add(commentModel);
        notifyDataSetChanged();
    }

    public void clearPosts(){
        commentModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commentview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentModel commentModel=commentModelList.get(position);
        holder.comment.setText(commentModel.getComment());

        String uid=commentModel.getUserId();
        FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel userModel=documentSnapshot.toObject(UserModel.class);
                        if(userModel.getUserProfile()!=null){
                            Glide.with(context).load(userModel.getUserProfile()).into(holder.userProfile);
                        }
                        holder.userName.setText(userModel.getUserName());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,comment;
        private ImageView userProfile;
        public MyViewHolder(View itemView){
            super(itemView);
            userName=itemView.findViewById(R.id.username);
            comment=itemView.findViewById(R.id.comments);
            userProfile=itemView.findViewById(R.id.userimage);
        }
    }
}
