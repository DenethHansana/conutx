package com.s23010403.conutx;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class CustomerDashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Product> productList, filteredList;
    ProductAdapter adapter;
    ImageView btnHome, btnCart, btnProfile, btnKnowledgeCenter;
    EditText etSearch;
    Button btnCategoryOil, btnCategoryFresh, btnCategorySale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        recyclerView = findViewById(R.id.recyclerViewProducts);
        btnHome = findViewById(R.id.btnHome);
        btnCart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.btnProfile);
        btnKnowledgeCenter = findViewById(R.id.btnKnowledgeCenter);
        etSearch = findViewById(R.id.etSearch);
        btnCategoryOil = findViewById(R.id.btnCategoryOil);
        btnCategoryFresh = findViewById(R.id.btnCategoryFresh);
        btnCategorySale = findViewById(R.id.btnCategorySale);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        productList = new ArrayList<>();
        filteredList = new ArrayList<>();
// Default Products + Example Sale Product
        productList.add(new Product("Coconut Oil", "High quality coconut oil", 1200, R.drawable.coconut_oil, "Coconut Oil"));
        productList.add(new Product("Fresh Coconut", "Fresh green coconut", 250, R.drawable.fresh_coconut, "Fresh Coconut"));
        productList.add(new Product("Coconut Oil - SALE", "Discounted coconut oil", 999, R.drawable.coconut_oil, "Sale"));
        filteredList.addAll(productList);
        adapter = new ProductAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);
// 🔎 Search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
// Category filters
        btnCategoryOil.setOnClickListener(v -> filterByCategory("Coconut Oil"));
        btnCategoryFresh.setOnClickListener(v -> filterByCategory("Fresh Coconut"));
        btnCategorySale.setOnClickListener(v -> filterByCategory("Sale"));
// Product click → details
        adapter.setOnItemClickListener(product -> {
            Intent i = new Intent(CustomerDashboardActivity.this, ProductDetailsActivity.class);
            i.putExtra("productName", product.getName());
            i.putExtra("productPrice", product.getPrice());
            i.putExtra("productDesc", product.getDescription());
            i.putExtra("productImage", product.getImage());
            startActivity(i);
        });
// Bottom Nav Clicks
        btnHome.setOnClickListener(v -> {
// ✅ refresh dashboard
            Intent i = new Intent(this, CustomerDashboardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
        btnCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        btnKnowledgeCenter.setOnClickListener(v -> startActivity(new Intent(this, KnowledgeCenterActivity.class)));
    }
    private void filterProducts(String query) {
        filteredList.clear();
        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private void filterByCategory(String category) {
        filteredList.clear();
        for (Product p : productList) {
            if (p.getCategory().equals(category)) {
                filteredList.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
