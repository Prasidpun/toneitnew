package com.example.toneit.Post;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toneit.R;
import com.example.toneit.userprofile.profileModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class dailyactivityadapter extends RecyclerView.Adapter<dailyactivityadapter.viewHolder> {

    Context context;
    ArrayList<profileModel> list;

    public dailyactivityadapter(Context context, ArrayList<profileModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public dailyactivityadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyacitvity,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dailyactivityadapter.viewHolder holder, int position) {

        holder.profile_image.setImageResource(list.get(position).getProfile_image());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.profileImg);
        }
    }
}
