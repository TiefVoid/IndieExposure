package com.example.indieexposure;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileHolder extends RecyclerView.ViewHolder {
    private ImageView ivProfPfp;
    private TextView tvProfUser, tvProfPseud;
    private ConstraintLayout profLayout;

    public ProfileHolder(@NonNull View itemView, ProfileAdapter.OnProfileClickListener interfaz) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();

                interfaz.onClick(position);
            }
        });

        ivProfPfp = itemView.findViewById(R.id.ivProfPfp);
        tvProfPseud = itemView.findViewById(R.id.tvProfPseud);
        tvProfUser = itemView.findViewById(R.id.tvProfUser);
    }

    public ImageView getIvProfPfp() {
        return ivProfPfp;
    }

    public void setIvProfPfp(ImageView ivProfPfp) {
        this.ivProfPfp = ivProfPfp;
    }

    public TextView getTvProfUser() {
        return tvProfUser;
    }

    public void setTvProfUser(TextView tvProfUser) {
        this.tvProfUser = tvProfUser;
    }

    public TextView getTvProfPseud() {
        return tvProfPseud;
    }

    public void setTvProfPseud(TextView tvProfPseud) {
        this.tvProfPseud = tvProfPseud;
    }

    public ConstraintLayout getProfLayout() {
        return profLayout;
    }

    public void setProfLayout(ConstraintLayout profLayout) {
        this.profLayout = profLayout;
    }
}
