package com.example.gearbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GearAdapter extends RecyclerView.Adapter<GearAdapter.GearViewHolder> {
    private ArrayList<Gear> dataSet;

    public GearAdapter(ArrayList<Gear> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public GearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.gear_card, parent, false);
        return new GearViewHolder(card);
    }

    @Override
    public void onBindViewHolder(GearViewHolder holder, int position) {
        holder.gearMaker.setText(dataSet.get(position).getMaker());
        holder.gearDescription.setText(dataSet.get(position).getDescription());

        String price = "$" + dataSet.get(position).getPrice().toString();
        holder.gearPrice.setText(price);

        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.CANADA);
        String date = dateFormat.format(dataSet.get(position).getDate());
        holder.gearDate.setText(date);

        // Get the comment, if it is null then write an empty string instead
        String comment = dataSet.get(position).getComment();
        holder.gearComment.setText(comment != null ? comment : "");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class GearViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView gearMaker;
        public TextView gearDescription;
        public TextView gearDate;
        public TextView gearComment;
        public TextView gearPrice;

        public GearViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.gearCard);
            gearMaker = (TextView) itemView.findViewById(R.id.gearCardTextViewMaker);
            gearDescription = (TextView) itemView.findViewById(R.id.gearCardTextViewDescription);
            gearDate = (TextView) itemView.findViewById(R.id.gearCardTextViewDate);
            gearComment = (TextView) itemView.findViewById(R.id.gearCardTextViewComment);
            gearPrice = (TextView) itemView.findViewById(R.id.gearCardTextViewPrice);
        }
    }

}
