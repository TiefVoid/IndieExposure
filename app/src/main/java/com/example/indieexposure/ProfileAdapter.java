package com.example.indieexposure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileHolder> {
    private ArrayList<User> data;
    private Context context;
    private ProfileAdapter.OnProfileClickListener interfaz;

    public ProfileAdapter(Context context, ProfileAdapter.OnProfileClickListener interfaz){
        this.context = context;
        this.interfaz = interfaz;
        data = new ArrayList<>();
    }

    public void add(User profile){
        data.add( profile );
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
    }

    public User getProfile(int position) {
        User profile = data.get(position);
        return profile;
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_simplified, parent, false);
        return new ProfileHolder(view,interfaz);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {
        User u = data.get(position);

        holder.getTvProfUser().setText(u.getUser());
        holder.getTvProfPseud().setText(u.getPseud());

        Picasso.get().load( u.getProfile_picture() ).into( holder.getIvProfPfp() );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnProfileClickListener{
        void onClick(int position);
    }
}
