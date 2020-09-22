package com.example.gearbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Gear> allGear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allGear = new ArrayList<>();
        allGear.add(new Gear(new Date(), "Brock", "lala", (float) 45.0));
        allGear.add(new Gear(new Date(), "Bork", "hahd alkj l ioo lorem ipsum lala hjjhj", (float) 4520.0, "This is a good"));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGears);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GearAdapter(allGear);
        recyclerView.setAdapter(adapter);

    }

    public void deleteItem(View view) {
        // The parent of the delete button is the vertical linear layout holding that and the edit button
        // The parent of that element is the horizontal linear layout holding the content of the card
        // The parent of that element is the card element, that is the element that we want
        int index = recyclerView.getChildLayoutPosition((View) view.getParent().getParent().getParent());
        allGear.remove(index);
        adapter.notifyItemRemoved(index);
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
    }
}