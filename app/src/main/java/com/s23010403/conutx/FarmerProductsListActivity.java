package com.s23010403.conutx;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import java.util.ArrayList;

public class FarmerProductsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> productList;
    ProductAdapter adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_products_list);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getAllProducts();
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
                // Add category as empty string for now
                productList.add(new Product(name, desc, price, image, ""));
            } while(cursor.moveToNext());
        }
        cursor.close();


        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }
}
