package com.s23010403.conutx;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productPrice, productDesc;
    Button btnAddCart;
    RecyclerView rvSuggested;
    ProductAdapter suggestedAdapter;
    ArrayList<Product> suggestedProducts;

    // Local cart
    ArrayList<Product> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDesc = findViewById(R.id.productDesc);
        btnAddCart = findViewById(R.id.btnAddCart);
        rvSuggested = findViewById(R.id.rvSuggested);

        // Load current product from intent
        Product currentProduct = new Product(
                getIntent().getStringExtra("productName"),
                getIntent().getStringExtra("productDesc"),
                getIntent().getIntExtra("productPrice", 0),
                getIntent().getIntExtra("productImage", R.drawable.fresh_coconut),
                "" // category not needed here
        );

        productName.setText(currentProduct.getName());
        productPrice.setText("Rs " + currentProduct.getPrice());
        productDesc.setText(currentProduct.getDescription());
        productImage.setImageResource(currentProduct.getImage());

        // Add to cart button
        btnAddCart.setOnClickListener(v -> {
            cartList.add(currentProduct);
            Toast.makeText(this, currentProduct.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        // Suggested products RecyclerView
        suggestedProducts = new ArrayList<>();
        suggestedProducts.add(new Product("Coconut Oil", "High quality coconut oil", 1200, R.drawable.coconut_oil, "Coconut Oil"));
        suggestedProducts.add(new Product("Fresh Coconut", "Fresh green coconut", 250, R.drawable.fresh_coconut, "Fresh Coconut"));
        suggestedProducts.add(new Product("Coconut Oil - SALE", "Discounted coconut oil", 999, R.drawable.coconut_oil, "Sale"));

        suggestedAdapter = new ProductAdapter(this, suggestedProducts);
        rvSuggested.setLayoutManager(new GridLayoutManager(this, 2));
        rvSuggested.setAdapter(suggestedAdapter);

        // Suggested product click → load details again
        suggestedAdapter.setOnItemClickListener(product -> {
            productName.setText(product.getName());
            productPrice.setText("Rs " + product.getPrice());
            productDesc.setText(product.getDescription());
            productImage.setImageResource(product.getImage());
        });
    }
}
