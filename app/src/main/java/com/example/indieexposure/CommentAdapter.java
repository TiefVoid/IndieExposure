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

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
    private ArrayList<Comment> data;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_simplified, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = data.get(position);

        holder.getTvCDesc().setText(comment.getDesc());

        long fechaHora = comment.getFechaHora();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, hh:mm a", new Locale("ES"));
        holder.getTvCTime().setText(simpleDateFormat.format(new Timestamp(fechaHora)));

        holder.getTvCUser().setText(comment.getUser());

        if (!comment.getPfp().equals("")) {
            Picasso.get().load(comment.getPfp()).into(holder.getIvCPfp());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

    public void add(Comment comment) {
        data.add(comment);
        notifyDataSetChanged();
    }
}
