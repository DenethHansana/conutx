package com.s23010403.conutx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerSignupActivity extends AppCompatActivity {

    EditText etName, etBirthday, etEmail, etContact, etAddress, etPassword;
    Button btnSignup;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etName);
        etBirthday = findViewById(R.id.etBirthday);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String birthday = etBirthday.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String contact = etContact.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = dbHelper.insertCustomer(name, birthday, email, contact, address, password);

            if(id > 0){
                // Save to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("userId", (int)id);
                editor.putString("userType", "customer");
                editor.apply();

                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CustomerSignupActivity.this, ProfileActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
