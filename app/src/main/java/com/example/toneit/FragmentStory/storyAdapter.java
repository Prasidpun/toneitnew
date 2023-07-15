package com.example.toneit.FragmentStory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.toneit.R;

import java.util.ArrayList;

public class storyAdapter extends RecyclerView.Adapter<storyAdapter.viewHolder> {

    Context context;
    ArrayList<storyModel> list;

    public storyAdapter(Context context, ArrayList<storyModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
       view=LayoutInflater.from(context).inflate(R.layout.storydesign,parent,false);
       viewHolder holder=new viewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.profileImage.setImageResource(list.get(position).getProfileImage());
        holder.userName.setText(list.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView userName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.profileImg);
            userName=itemView.findViewById(R.id.userName);
        }
    }
}
