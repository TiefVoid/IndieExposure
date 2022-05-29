package com.example.indieexposure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private ArrayList<Post> data;
    private Context context;

    public PostAdapter(Context context){
        this.context = context;
        data = new ArrayList<>();
    }

    public void add(Post post){
        data.add( post );
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
    }

    public Post getPost(int position) {
        Post post = data.get(position);
        return post;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_simplified, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = data.get(position);

        holder.getTvDescShort().setText(post.getDesc());

        long fechaHora = post.getFechaHora();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, hh:mm a", new Locale( "ES"));
        holder.getTvPostDate().setText(simpleDateFormat.format( new Timestamp(fechaHora)));

        holder.getTvUserShort().setText(post.getUser());

        if(!post.getAudio().equals("")){
            holder.getTvSoundCheck().setText("-Contiene audio");
        }else{
            holder.getTvSoundCheck().setText("");
        }

        if(!post.getImg().equals("")){
            Picasso.get().load( post.getImg() ).into( holder.getIvPhotoShort() );
            holder.getIvPhotoShort().setVisibility( View.VISIBLE );
        }else{
            holder.getIvPhotoShort().setImageResource(0);
            holder.getIvPhotoShort().setVisibility( View.GONE );
        }

        if(!post.getPfp().equals("")){
            Picasso.get().load( post.getPfp() ).into( holder.getIvPfp() );
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnClickListener{
        void onClick(int position);
    }
}
