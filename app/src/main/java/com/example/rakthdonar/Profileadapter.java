package com.example.rakthdonar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Profileadapter extends RecyclerView.Adapter<Profileadapter.Viewholder> {
    Context context;
    ArrayList<User> userArrayList;
    public Profileadapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }



    @NonNull
    @Override
    public Profileadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Profileadapter.Viewholder holder, int position) {

        User user=userArrayList.get(position);
        String g=user.getBloodgroup();
        holder.weight.setText(user.getWeight());
        holder.t2.setText(user.getGender());
        holder.recently.setText(user.getRecentlydonated());
        holder.Age.setText(user.getAge());
        holder.t1.setText(user.getBloodgroup());
        holder.email.setText(user.getEmail());

        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "Hi");
                share.setPackage("com.google.android.apps.dynamite");  // open hangouts app directly
                context.startActivity(share);
            }
        });
        if(g=="A+" || g=="a+"){
            holder.im.setImageResource(R.drawable.bnegative);
        }
        if(g=="A-" || g=="a-"){
          holder.im.setImageResource(R.drawable.bnegative);
        }


        if(g=="B+" || g=="b+"){
            holder.im.setImageResource(R.drawable.bpositive);
        }

        if(g=="B-" || g=="b-"){
            holder.im.setImageResource(R.drawable.bnegative);
        }
        if(g=="O+" || g=="o+"){
           holder.im.setImageResource(R.drawable.bnegative);
        }
        if(g=="O-" || g=="o-"){
            holder.im.setImageResource(R.drawable.bnegative);
        }
        if(g=="AB+" || g=="ab+"){
            holder.im.setImageResource(R.drawable.bnegative);

        }
        if(g=="Ab+" || g=="aB+"){
           holder.im.setImageResource(R.drawable.bnegative);

        }
        if(g=="AB-" || g=="ab-"){
            holder.im.setImageResource(R.drawable.bnegative);
        }
        if(g=="Ab-" || g=="aB-"){
            holder.im.setImageResource(R.drawable.bnegative);
        }


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class Viewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,Age,weight,recently,email;
        ImageView im;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            im=itemView.findViewById(R.id.img1);
            email=itemView.findViewById(R.id.email);
            t1=itemView.findViewById(R.id.bgroup);
            t2=itemView.findViewById(R.id.bgender);
            Age=itemView.findViewById(R.id.bage);
            weight=itemView.findViewById(R.id.bweight);
            recently=itemView.findViewById(R.id.brecently);

        }
    }

}
