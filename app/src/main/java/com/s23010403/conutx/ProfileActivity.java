package com.s23010403.conutx;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText etName, etBirthday, etEmail, etContact, etAddress;
    Button btnUpdateProfile;
    LinearLayout layoutLoginSignup; // container for login/signup buttons
    DBHelper dbHelper;

    // Track current logged-in user
    String userType = null; // "customer" or "farmer"
    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);

        // Views
        layoutLoginSignup = findViewById(R.id.layoutLoginSignup);

        etName = findViewById(R.id.etName);
        etBirthday = findViewById(R.id.etBirthday);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etAddress = findViewById(R.id.etAddress);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        // Login / Signup Buttons
        Button btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        Button btnCustomerSignup = findViewById(R.id.btnCustomerSignup);
        Button btnFarmerLogin = findViewById(R.id.btnFarmerLogin);
        Button btnFarmerSignup = findViewById(R.id.btnFarmerSignup);

        btnCustomerLogin.setOnClickListener(v ->
                startActivityForResult(new Intent(this, CustomerLoginActivity.class), 101));

        btnCustomerSignup.setOnClickListener(v ->
                startActivityForResult(new Intent(this, CustomerSignupActivity.class), 102));

        btnFarmerLogin.setOnClickListener(v ->
                startActivityForResult(new Intent(this, FarmerLoginActivity.class), 201));

        btnFarmerSignup.setOnClickListener(v ->
                startActivityForResult(new Intent(this, FarmerSignupActivity.class), 202));

        btnUpdateProfile.setOnClickListener(v -> updateCustomerProfile());
    }

    private void updateCustomerProfile(){
        if(userType == null || userId == -1) return;

        String name = etName.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        boolean success = dbHelper.updateCustomerProfile(userId, name, birthday, email, contact, address);
        if(success){
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCustomerProfile(){
        Cursor cursor = dbHelper.getCustomerProfile(userId);
        if(cursor != null && cursor.moveToFirst()){
            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etBirthday.setText(cursor.getString(cursor.getColumnIndexOrThrow("birthday")));
            etEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            etContact.setText(cursor.getString(cursor.getColumnIndexOrThrow("contact")));
            etAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which login/signup was successful
        if(data != null){
            userType = data.getStringExtra("userType");
            userId = data.getIntExtra("userId", -1);

            if(userType != null && userId != -1){
                if(userType.equals("customer")){
                    layoutLoginSignup.setVisibility(View.GONE); // hide buttons
                    loadCustomerProfile(); // show profile fields
                    btnUpdateProfile.setVisibility(View.VISIBLE);
                } else if(userType.equals("farmer")){
                    // Farmer login/signup successful -> redirect to FarmerDashboard
                    startActivity(new Intent(this, FarmerDashboardActivity.class));
                    finish();
                }
            }
        }
    }
}
