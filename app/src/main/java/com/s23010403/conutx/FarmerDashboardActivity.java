package com.s23010403.conutx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerDashboardActivity extends AppCompatActivity {

    Button btnAddProduct, btnViewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnViewProducts = findViewById(R.id.btnViewProducts);

        btnAddProduct.setOnClickListener(v -> startActivity(new Intent(this, AddProductActivity.class)));
        btnViewProducts.setOnClickListener(v -> startActivity(new Intent(this, FarmerProductsListActivity.class)));
    }
}
