package com.example.firebasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userHolder> {

    Context mContext;
    List<UserInfo> userInfos;

    public userAdapter(Context mContext, List<UserInfo> userInfos) {
        this.mContext = mContext;
        this.userInfos = userInfos;
    }

    @NonNull
    @Override
    public userAdapter.userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(mContext).inflate(R.layout.user_view,parent,false);


        return new userHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.userHolder holder, int position) {
        UserInfo userInfo=userInfos.get(position);
        holder.txtname.setText(userInfo.getName());
        holder.txtphone.setText(userInfo.getPhone());
        holder.txtmail.setText(userInfo.getEmail());
    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class userHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtmail,txtphone;
        public userHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.viewname);
            txtmail=itemView.findViewById(R.id.viewemail);
            txtphone=itemView.findViewById(R.id.viewphone);
        }
    }
}
