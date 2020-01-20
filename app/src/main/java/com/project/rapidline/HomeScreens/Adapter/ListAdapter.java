package com.project.rapidline.HomeScreens.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rapidline.Models.RecyclerViewItemsCount;
import com.project.rapidline.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<RecyclerViewItemsCount> recyclerViewItemsCounts;
    private Context context;
    public ListAdapter(Context context,ArrayList<RecyclerViewItemsCount> recyclerViewItemsCounts){
        this.context = context;
        this.recyclerViewItemsCounts = recyclerViewItemsCounts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_view, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Count.setText(recyclerViewItemsCounts.get(position).getCount());
        holder.StrItem.setText(recyclerViewItemsCounts.get(position).getItem());
    }

    @Override
    public int getItemCount() {
        return recyclerViewItemsCounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Count, StrItem;
        ImageView imageEdit, imageDelete;

        public ViewHolder(View view) {
            super(view);
            Count = view.findViewById(R.id.Count);
            StrItem = view.findViewById(R.id.itemstr);
            imageEdit = view.findViewById(R.id.editimage);
            imageDelete = view.findViewById(R.id.deleteimage);
            imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                }
            });
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
