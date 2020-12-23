package com.project.rapidline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BiltyAdapter extends RecyclerView.Adapter<BiltyAdapter.EnterieViewHolder> implements Filterable {

    private ArrayList<Bilty> biltyArrayList;
    private Context mCtx;
    private List<Bilty> biltyCopyList;
    private OnNoteListener mOnNoteListener;


    public BiltyAdapter(Context mCtx, ArrayList<Bilty> biltyArrayList, OnNoteListener onNoteListener) {
        this.biltyArrayList = biltyArrayList;
        this.mCtx = mCtx;
        biltyCopyList = new ArrayList<Bilty>(this.biltyArrayList);
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public EnterieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cell_register,
                parent, false);
        return new EnterieViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EnterieViewHolder holder, int position) {
        holder.builty_no.setText(biltyArrayList.get(position).getBiltyNo());
        holder.sender_txt.setText(biltyArrayList.get(position).getSenderId());
        holder.receiver_txt.setText(biltyArrayList.get(position).getReceiverId());
//        holder.name_txt.setText(biltyArrayList.get(position).getMadeBy());
        holder.date_txt.setText(formattedDate(biltyArrayList.get(position).getMadeDateTime()));
        holder.fromCity.setText(biltyArrayList.get(position).getFromCity());
        holder.toCity.setText(biltyArrayList.get(position).getToCity());

//        //Set click listeners for print
//        if (onItemClickListener != null) {
//            holder.print_btn.setOnClickListener(view -> onItemClickListener.onItemClick(biltyArrayList.get(position).getBiltyNo(), "print"));
//        }

    }

    private String formattedDate(Date mydate) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(mydate);
    }

    @Override
    public int getItemCount() {
        return biltyArrayList.size();
    }


    public class EnterieViewHolder extends RecyclerView.ViewHolder{
        TextView sender_txt, receiver_txt, date_txt, builty_no, name_txt,fromCity,toCity;
        Button print_btn;
        OnNoteListener onNoteListener;
        ImageView edit_btn;

        public EnterieViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            sender_txt = itemView.findViewById(R.id.sender_name);
            receiver_txt = itemView.findViewById(R.id.receiver_name);
            builty_no = itemView.findViewById(R.id.builty_no);
            date_txt = itemView.findViewById(R.id.date_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
//            print_btn = itemView.findViewById(R.id.print_btn);
            edit_btn = itemView.findViewById(R.id.editBailBiltyBtn);
            fromCity=itemView.findViewById(R.id.senderCity);
            toCity=itemView.findViewById(R.id.receiverCity);

            edit_btn.setOnClickListener(view -> {
                onNoteListener.onBiltyClick(biltyArrayList.get(getAdapterPosition()));
            });

        }

    }

    public interface OnNoteListener {
        void onBiltyClick(Bilty bilty);
    }

    public void setBiltyArrayList(ArrayList<Bilty> biltyArrayList) {
        this.biltyArrayList = biltyArrayList;
        biltyCopyList = new ArrayList<Bilty>(this.biltyArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Bilty> getBiltyArrayList() {
        return biltyArrayList;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Bilty> filtredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filtredList.addAll(biltyCopyList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Bilty bailList : biltyCopyList) {
                    if (bailList.getBiltyNo().toLowerCase().contains(filterPattern)) {
                        filtredList.add(bailList);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filtredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            biltyArrayList.clear();
            biltyArrayList.addAll((Collection<? extends Bilty>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
