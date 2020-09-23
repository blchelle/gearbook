package com.example.gearbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView textViewTotalPrice;

    private ArrayList<Gear> allGear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allGear = new ArrayList<>();

        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        textViewTotalPrice.setText("Total Price: $"+Float.toString(sumGearPrices()));

        recyclerView = findViewById(R.id.recycle_view_gears);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GearAdapter(allGear);
        recyclerView.setAdapter(adapter);

    }

    private float sumGearPrices() {
        float sum = 0;
        for (Gear gear: this.allGear) { sum += gear.getPrice(); }
        return sum;
    }

    public void deleteItem(View view) {
        // The parent of the delete button is the vertical linear layout holding that and the edit button
        // The parent of that element is the horizontal linear layout holding the content of the card
        // The parent of that element is the card element, that is the element that we want
        int index = recyclerView.getChildLayoutPosition((View) view.getParent().getParent().getParent());
        allGear.remove(index);
        adapter.notifyItemRemoved(index);

        // Update the price
        textViewTotalPrice.setText("Total Price: $"+Float.toString(sumGearPrices()));
    }

    public void launchEditItemActivity(View view) {
        // The parent of the edit button is the vertical linear layout holding that and the edit button
        // The parent of that element is the horizontal linear layout holding the content of the card
        // The parent of that element is the card element, that is the element that we want
        CardView cardToBeEdited = (CardView) view.getParent().getParent().getParent();
        int gearIndex = recyclerView.getChildLayoutPosition(cardToBeEdited);

        Intent intent = new Intent(this, AddOrEditGearActivity.class);
        intent.putExtra("GEAR", allGear.get(gearIndex));
        intent.putExtra("GEAR_INDEX", gearIndex);
        startActivityForResult(intent, 2);
    }

    public void onAddItemButtonClick(View view) {
        Intent intent = new Intent(this, AddOrEditGearActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Gear newGear = data.getParcelableExtra("GEAR");
                allGear.add(newGear);
                adapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Gear modifiedGear = data.getParcelableExtra("GEAR");
                int gearIndex = data.getIntExtra("GEAR_INDEX", 0);
                allGear.set(gearIndex, modifiedGear);
                adapter.notifyDataSetChanged();
            }
        }

        // Update the price
        textViewTotalPrice.setText("Total Price: $"+Float.toString(sumGearPrices()));
    }
}