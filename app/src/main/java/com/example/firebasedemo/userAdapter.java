package com.example.firebasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userHolder> {

    Context mContext;
    List<UserInfo> userInfos;
    DatabaseReference databaseReference;

    public userAdapter(Context mContext, List<UserInfo> userInfos, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.userInfos = userInfos;
        this.databaseReference = databaseReference;
    }

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

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deldailog=new AlertDialog.Builder(mContext);
                deldailog.setMessage("Do you want to Delete?");
                deldailog.setCancelable(true);

                deldailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Query  query=databaseReference.orderByChild("name").equalTo(holder.txtname.getText().toString());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    dataSnapshot.getRef().removeValue();
                                }
                                Toast.makeText(mContext,"User Deleted",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(mContext,""+error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                deldailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=deldailog.create();
                dialog.show();
            }
        });
        holder.updbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder updatedailog=new AlertDialog.Builder(mContext);
                updatedailog.setMessage("Do you want to Delete?");
                updatedailog.setCancelable(true);

                View myview=LayoutInflater.from(mContext).inflate(R.layout.customview,null);
                updatedailog.setView(myview);
                EditText edtname,edtmail,edtphone;
                edtmail=myview.findViewById(R.id.updatemail);
                edtname=myview.findViewById(R.id.updatename);
                edtphone=myview.findViewById(R.id.updatephone);


                edtname.setText(holder.txtname.getText().toString());
                edtmail.setText(holder.txtmail.getText().toString());
                edtphone.setText(holder.txtphone.getText().toString());

                String username=holder.txtname.getText().toString();
                updatedailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Query query=databaseReference.orderByChild("name").equalTo(username);
                        HashMap Student=new HashMap();

                        Student.put("name",edtname.getText().toString());
                        Student.put("email",edtmail.getText().toString());
                        Student.put("phone",edtphone.getText().toString());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    dataSnapshot.getRef().updateChildren(Student);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(mContext,"User Updated successfully",Toast.LENGTH_LONG).show();
                    }
                });
                updatedailog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog=updatedailog.create();
                dialog.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class userHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtmail,txtphone;
        ImageView delbtn,updbtn;
        public userHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.viewname);
            txtmail=itemView.findViewById(R.id.viewemail);
            txtphone=itemView.findViewById(R.id.viewphone);
            delbtn=itemView.findViewById(R.id.delete);
            updbtn=itemView.findViewById(R.id.update);
        }
    }
}
