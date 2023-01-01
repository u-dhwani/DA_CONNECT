package com.example.social_media;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context context;
    private List<PostModel> postModelList;
    // constructor
    public PostAdapter(Context context) {
        this.context = context;
        postModelList=new ArrayList<>();
    }

    public void addPost(PostModel postModel){
        postModelList.add(postModel);
        notifyDataSetChanged();
    }

    public void clearPosts(){
        postModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.postview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostModel postModel=postModelList.get(position);
        if(postModel.getPostImage()!=null){
            holder.postImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(postModel.getPostImage()).into(holder.postImage);
        }
        else{
            holder.postImage.setVisibility(View.GONE);
        }
        holder.postText.setText(postModel.getPostText());
        holder.gotoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ProfileActivity.class);
                intent.putExtra("id",postModel.getUserId());
                context.startActivity(intent);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CommentActivity.class);
                intent.putExtra("id",postModel.getPostId());
                context.startActivity(intent);
            }
        });

        FirebaseFirestore.getInstance()
                .collection("Likes")
                .document(postModel.getPostId()+ FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot!=null){
                            postModel.setIsliked(true);
                            holder.like.setImageResource(R.drawable.post_thumbsup_blue);
                        }
                        else{
                            postModel.setIsliked(false);
                            holder.like.setImageResource(R.drawable.post_thumbsup_blue);
                        }
                    }
                });
        postModel.setIsliked(false);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(postModel.isIsliked()){
                    postModel.setIsliked(false);
                    holder.like.setImageResource(R.drawable.post_thumbsup);
                    FirebaseFirestore.getInstance()
                            .collection("Likes")
                            .document(postModel.getPostId()+FirebaseAuth.getInstance().getUid())
                            .delete();
                }
                else{
                    postModel.setIsliked(true);
                    holder.like.setImageResource(R.drawable.post_thumbsup_blue);
                    FirebaseFirestore.getInstance()
                            .collection("Likes")
                            .document(postModel.getPostId()+FirebaseAuth.getInstance().getUid())
                            .set(new PostModel("hi"));
                }
            }
        });

        String uid=postModel.getUserId();
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
        return postModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userName,postText;
        private ImageView userProfile,postImage,like,comment;
        private RelativeLayout gotoprofile;
        public MyViewHolder(View itemView){
            super(itemView);
            userName=itemView.findViewById(R.id.username);
            postText=itemView.findViewById(R.id.posttext);
            userProfile=itemView.findViewById(R.id.userimage);
            gotoprofile=itemView.findViewById(R.id.profile_click);
            postImage=itemView.findViewById(R.id.postimage);
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);


        }
    }
}
