package com.example.indieexposure;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostHolder extends RecyclerView.ViewHolder {
    private ImageView ivPfp,ivPhotoShort;
    private TextView tvUserShort, tvSoundCheck, tvDescShort, tvPostDate;

    public PostHolder(@NonNull View itemView) {
        super(itemView);

        ivPfp = itemView.findViewById(R.id.ivPfp);
        ivPhotoShort = itemView.findViewById(R.id.ivPhotoShort);
        tvUserShort = itemView.findViewById(R.id.tvUserShort);
        tvSoundCheck = itemView.findViewById(R.id.tvSoundCheck);
        tvDescShort = itemView.findViewById(R.id.tvDescShort);
        tvPostDate = itemView.findViewById(R.id.tvPostDate);
    }

    public ImageView getIvPfp() {
        return ivPfp;
    }

    public void setIvPfp(ImageView ivPfp) {
        this.ivPfp = ivPfp;
    }

    public ImageView getIvPhotoShort() {
        return ivPhotoShort;
    }

    public void setIvPhotoShort(ImageView ivPhotoShort) {
        this.ivPhotoShort = ivPhotoShort;
    }

    public TextView getTvUserShort() {
        return tvUserShort;
    }

    public void setTvUserShort(TextView tvUserShort) {
        this.tvUserShort = tvUserShort;
    }

    public TextView getTvSoundCheck() {
        return tvSoundCheck;
    }

    public void setTvSoundCheck(TextView tvSoundCheck) {
        this.tvSoundCheck = tvSoundCheck;
    }

    public TextView getTvDescShort() {
        return tvDescShort;
    }

    public void setTvDescShort(TextView tvDescShort) {
        this.tvDescShort = tvDescShort;
    }

    public TextView getTvPostDate() {
        return tvPostDate;
    }

    public void setTvPostDate(TextView tvPostDate) {
        this.tvPostDate = tvPostDate;
    }
}
