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
    private GearAdapter adapter;
    private TextView textViewTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view_gears);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GearAdapter(new ArrayList<Gear>());
        recyclerView.setAdapter(adapter);

        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        textViewTotalPrice.setText("Total Price: $"+String.format("%.2f", sumGearPrices()));
    }

    private float sumGearPrices() {
        float sum = 0;
        for (Gear gear: this.adapter.getGears()) { sum += gear.getPrice(); }
        return sum;
    }

    public void deleteItem(View view) {
        // The parent of the delete button is the vertical linear layout holding that and the edit button
        // The parent of that element is the horizontal linear layout holding the content of the card
        // The parent of that element is the card element, that is the element that we want
        int index = recyclerView.getChildLayoutPosition((View) view.getParent().getParent().getParent());
        this.adapter.removeGear(index);
        adapter.notifyItemRemoved(index);

        // Update the price
        textViewTotalPrice.setText("Total Price: $"+String.format("%.2f", sumGearPrices()));
    }

    public void launchEditItemActivity(View view) {
        // The parent of the edit button is the vertical linear layout holding that and the edit button
        // The parent of that element is the horizontal linear layout holding the content of the card
        // The parent of that element is the card element, that is the element that we want
        CardView cardToBeEdited = (CardView) view.getParent().getParent().getParent();
        int gearIndex = recyclerView.getChildLayoutPosition(cardToBeEdited);

        // Launch the AddOrEditGearActivity class with the request code 2
        // The request code 2 indicates that it will edit an existing gear rather than adding one
        Intent intent = new Intent(this, AddOrEditGearActivity.class);
        intent.putExtra("GEAR", adapter.getGears().get(gearIndex));
        intent.putExtra("GEAR_INDEX", gearIndex);
        startActivityForResult(intent, 2);
    }

    public void launchAddItemActivity(View view) {
        // Launch the AddOrEditGearActivity class with the request code 1
        // The request code 1 indicates that it will add new gear rather than editing one
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
                adapter.addGear(newGear);
                adapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Gear modifiedGear = data.getParcelableExtra("GEAR");

                if (modifiedGear != null) {
                    int gearIndex = data.getIntExtra("GEAR_INDEX", 0);
                    adapter.modifyGear(gearIndex, modifiedGear);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        // Update the price
        textViewTotalPrice.setText("Total Price: $"+String.format("%.2f", sumGearPrices()));
    }
}
