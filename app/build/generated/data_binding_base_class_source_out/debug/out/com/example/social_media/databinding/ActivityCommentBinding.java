// Generated by view binder compiler. Do not edit!
package com.example.social_media.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.social_media.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCommentBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RecyclerView commentRecycler;

  @NonNull
  public final RelativeLayout commentsendlayout;

  @NonNull
  public final EditText commenttype;

  @NonNull
  public final TextView sendcomment;

  private ActivityCommentBinding(@NonNull RelativeLayout rootView,
      @NonNull RecyclerView commentRecycler, @NonNull RelativeLayout commentsendlayout,
      @NonNull EditText commenttype, @NonNull TextView sendcomment) {
    this.rootView = rootView;
    this.commentRecycler = commentRecycler;
    this.commentsendlayout = commentsendlayout;
    this.commenttype = commenttype;
    this.sendcomment = sendcomment;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCommentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_comment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCommentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.comment_recycler;
      RecyclerView commentRecycler = ViewBindings.findChildViewById(rootView, id);
      if (commentRecycler == null) {
        break missingId;
      }

      id = R.id.commentsendlayout;
      RelativeLayout commentsendlayout = ViewBindings.findChildViewById(rootView, id);
      if (commentsendlayout == null) {
        break missingId;
      }

      id = R.id.commenttype;
      EditText commenttype = ViewBindings.findChildViewById(rootView, id);
      if (commenttype == null) {
        break missingId;
      }

      id = R.id.sendcomment;
      TextView sendcomment = ViewBindings.findChildViewById(rootView, id);
      if (sendcomment == null) {
        break missingId;
      }

      return new ActivityCommentBinding((RelativeLayout) rootView, commentRecycler,
          commentsendlayout, commenttype, sendcomment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
