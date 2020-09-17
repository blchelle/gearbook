package com.example.gearbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
//        allGear.add(new Gear(new Date(), "Brock", "lala", (float) 45.0));
//        allGear.add(new Gear(new Date(), "Bork", "hahd alkj l ioo lorem ipsum lala hjjhj", (float) 4520.0, "This is a good"));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGears);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GearAdapter(allGear);
        recyclerView.setAdapter(adapter);

    }

    public void deleteItem(View view) {
        int index = recyclerView.getChildLayoutPosition((View) view.getParent().getParent());
        allGear.remove(index);
        adapter.notifyItemRemoved(index);
    }

    public void onAddItemButtonClick(View view) {
        Intent intent = new Intent(this, AddGearActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Gear newGear = data.getParcelableExtra("NEW_GEAR");
                allGear.add(newGear);
            }
        }
    }
}