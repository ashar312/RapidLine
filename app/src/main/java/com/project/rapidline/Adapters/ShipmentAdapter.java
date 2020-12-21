package com.project.rapidline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rapidline.Models.RapidLine.Bilty;
import com.project.rapidline.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ShipmentViewHolder> {

    private Context mContext;
    private ArrayList<Bilty> biltyArrayList;
    private ArrayList<Bilty> selectedBiltyList=new ArrayList<>();

    public ShipmentAdapter(Context mContext, ArrayList<Bilty> biltyArrayList) {
        this.mContext = mContext;
        this.biltyArrayList = biltyArrayList;
    }

    @NonNull
    @Override
    public ShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_item_layout,
                parent, false);
        return new ShipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentViewHolder holder, int position) {
        holder.builty_no.setText(biltyArrayList.get(position).getBiltyNo());
        holder.sender_txt.setText(biltyArrayList.get(position).getSenderId());
        holder.receiver_txt.setText(biltyArrayList.get(position).getReceiverId());
        holder.date_txt.setText(formattedDate(biltyArrayList.get(position).getMadeDateTime()));
        holder.senderCity.setText(biltyArrayList.get(position).getFromCity());
        holder.receiverCity.setText(biltyArrayList.get(position).getToCity());

        if(biltyArrayList.get(position).shipped){
            holder.shipmentSelected.setVisibility(View.GONE);
            holder.shipmentHint.setVisibility(View.VISIBLE);
        }
        else {
            holder.shipmentSelected.setVisibility(View.VISIBLE);
            holder.shipmentHint.setVisibility(View.INVISIBLE);
        }

    }

    private String formattedDate(Date mydate) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(mydate);
    }

    @Override
    public int getItemCount() {
        return biltyArrayList.size();
    }

    public class ShipmentViewHolder extends RecyclerView.ViewHolder{
        TextView sender_txt, receiver_txt, date_txt, builty_no,senderCity,receiverCity,shipmentHint;
        CheckBox shipmentSelected;
        public ShipmentViewHolder(@NonNull View itemView) {
            super(itemView);
            sender_txt = itemView.findViewById(R.id.sender_name);
            receiver_txt = itemView.findViewById(R.id.receiver_name);
            builty_no = itemView.findViewById(R.id.builty_no);
            date_txt = itemView.findViewById(R.id.date_txt);
            senderCity=itemView.findViewById(R.id.senderCity);
            receiverCity=itemView.findViewById(R.id.receiverCity);
            shipmentSelected=itemView.findViewById(R.id.selectShipment);
            shipmentHint=itemView.findViewById(R.id.shipmentHint);

            shipmentSelected.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if(isChecked){
                    selectedBiltyList.add(biltyArrayList.get(getAdapterPosition()));
                }
                else {
                    selectedBiltyList.remove(biltyArrayList.get(getAdapterPosition()));
                }
            });

        }

    }

    public void setBiltyArrayList(ArrayList<Bilty> biltyArrayList) {
        this.biltyArrayList = biltyArrayList;
        notifyDataSetChanged();
    }

    public ArrayList<Bilty> getSelectedBiltyList() {
        return selectedBiltyList;
    }


}
