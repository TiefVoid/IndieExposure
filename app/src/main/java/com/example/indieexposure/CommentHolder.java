package com.example.indieexposure;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CommentHolder extends RecyclerView.ViewHolder {
    private ImageView ivCPfp;
    private TextView tvCUser,tvCTime,tvCDesc ;
    private ConstraintLayout commentContainer;

    public CommentHolder(View view) {
        super(view);

        ivCPfp = itemView.findViewById(R.id.ivCPfp);
        tvCUser = itemView.findViewById(R.id.tvCUser);
        tvCTime = itemView.findViewById(R.id.tvCTime);
        tvCDesc = itemView.findViewById(R.id.tvCDesc);
        commentContainer = itemView.findViewById(R.id.commentContainer);

    }

    public ImageView getIvCPfp() {
        return ivCPfp;
    }

    public void setIvCPfp(ImageView ivCPfp) {
        this.ivCPfp = ivCPfp;
    }

    public TextView getTvCUser() {
        return tvCUser;
    }

    public void setTvCUser(TextView tvCUser) {
        this.tvCUser = tvCUser;
    }

    public TextView getTvCTime() {
        return tvCTime;
    }

    public void setTvCTime(TextView tvCTime) {
        this.tvCTime = tvCTime;
    }

    public TextView getTvCDesc() {
        return tvCDesc;
    }

    public void setTvCDesc(TextView tvCDesc) {
        this.tvCDesc = tvCDesc;
    }

    public ConstraintLayout getCommentContainer() {
        return commentContainer;
    }

    public void setCommentContainer(ConstraintLayout commentContainer) {
        this.commentContainer = commentContainer;
    }
}
