package com.s23010403.conutx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    TextView tvCartItems, tvTotal;
    Button btnCheckout;

    ArrayList<Product> cartList = new ArrayList<>(); // Local cart

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvCartItems = findViewById(R.id.tvCartItems); // Multi-line TextView for cart
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Example: Add a product (you can call addProduct() from ProductDetailsActivity)
        // addProduct(new Product("Coconut Oil", "High quality", 1200, R.drawable.coconut_oil, "Oil"));

        updateCartDisplay();

        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_SHORT).show();
                cartList.clear();
                updateCartDisplay();
            }
        });
    }

    // Add product to cart
    public void addProduct(Product product) {
        cartList.add(product);
        updateCartDisplay();
    }

    // Update cart TextView and total
    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for (Product p : cartList) {
            sb.append(p.getName()).append(" - Rs ").append(p.getPrice()).append("\n");
            total += p.getPrice();
        }
        tvCartItems.setText(sb.toString().isEmpty() ? "Cart is empty" : sb.toString());
        tvTotal.setText("Total: Rs " + total);
    }
}
