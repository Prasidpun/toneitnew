package com.example.toneit.Post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toneit.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.viewHolder> {
    Context context;
    ArrayList<CommentModel>list;
    commentAdapter(Context context,ArrayList<CommentModel>list){

        this.context=context;
        this.list=list;

    }


    @NonNull
    @Override
    public commentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_design,parent,false);
        viewHolder holder=new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull commentAdapter.viewHolder holder, int position) {
        CommentModel model = list.get(position);
        Picasso.get().load(model.getProfile()).placeholder(R.drawable.villainjr).into(holder.profile_image);
        holder.comment.setText(model.getCommentText());
        String text = TimeAgo.using(model.getCommentAt());
        holder.timeStamp.setText(text);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;
        TextView comment,timeStamp;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.profile_image);
            comment=itemView.findViewById(R.id.comment);
            timeStamp=itemView.findViewById(R.id.timeStamp);

        }
    }
}
