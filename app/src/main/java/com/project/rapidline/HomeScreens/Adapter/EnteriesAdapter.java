package com.project.rapidline.HomeScreens.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.rapidline.Database.entity.Bails;
import com.project.rapidline.Models.BailMinimal;
import com.project.rapidline.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnteriesAdapter  extends RecyclerView.Adapter<EnteriesAdapter.EnterieViewHolder> {

    private ArrayList<BailMinimal> bailsArrayList;
    private Context mCtx;

    public EnteriesAdapter(Context mCtx,ArrayList<BailMinimal> bailsArrayList) {
        this.bailsArrayList = bailsArrayList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public EnterieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cell_register,
                parent,false);
        return new EnterieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnterieViewHolder holder, int position) {
        holder.builty_no.setText(bailsArrayList.get(position).getBiltyNo());
        holder.sender_txt.setText(bailsArrayList.get(position).getSendName());
        holder.receiver_txt.setText(bailsArrayList.get(position).getReceiverName());
        holder.name_txt.setText(bailsArrayList.get(position).getName());
        holder.date_txt.setText(formattedDate(bailsArrayList.get(position).getTime()));
    }

    private String formattedDate(Date mydate){
        DateFormat dateFormat=DateFormat.getDateInstance();
        return dateFormat.format(mydate);
    }

    @Override
    public int getItemCount() {
        return bailsArrayList.size();
    }

    public void setBailsArrayList(ArrayList<BailMinimal> bailsArrayList) {
        this.bailsArrayList = bailsArrayList;
        notifyDataSetChanged();
    }

    public class EnterieViewHolder extends RecyclerView.ViewHolder{
        TextView sender_txt,receiver_txt,date_txt,builty_no,name_txt;
        Button print_btn;

        public EnterieViewHolder(@NonNull View itemView) {
            super(itemView);
            sender_txt=itemView.findViewById(R.id.sender_name);
            receiver_txt=itemView.findViewById(R.id.receiver_name);
            builty_no=itemView.findViewById(R.id.builty_no);
            date_txt=itemView.findViewById(R.id.date_txt);
            name_txt=itemView.findViewById(R.id.name_txt);
            print_btn=itemView.findViewById(R.id.print_btn);
        }
    }
}
