package com.s23010403.conutx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "conutx.db";
    private static final int DB_VERSION = 3; // bumped for schema update

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Customers table
        db.execSQL("CREATE TABLE IF NOT EXISTS customers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "birthday TEXT," +
                "email TEXT UNIQUE," +
                "contact TEXT," +
                "address TEXT," +
                "password TEXT)");

        // Farmers table
        db.execSQL("CREATE TABLE IF NOT EXISTS farmers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "birthday TEXT," +
                "email TEXT UNIQUE," +
                "contact TEXT," +
                "address TEXT," +
                "password TEXT," +
                "latitude REAL," +
                "longitude REAL)");

        // Products table
        db.execSQL("CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "farmerId INTEGER," +
                "name TEXT," +
                "description TEXT," +
                "price INTEGER," +
                "image INTEGER)");

        // Cart table
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customerId INTEGER," +
                "productId INTEGER," +
                "quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS customers");
        db.execSQL("DROP TABLE IF EXISTS farmers");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    // -------------------------------
    // CUSTOMER METHODS
    public long insertCustomer(String name, String birthday, String email, String contact, String address, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("password", password);
        return db.insert("customers", null, cv);
    }

    public Cursor loginCustomer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM customers WHERE email=? AND password=?", new String[]{email, password});
    }

    public Cursor getCustomerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM customers WHERE id=?", new String[]{String.valueOf(id)});
    }

    public int updateCustomer(int id, String name, String birthday, String email, String contact, String address, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("password", password);
        return db.update("customers", cv, "id=?", new String[]{String.valueOf(id)});
    }

    // -------------------------------
    // FARMER METHODS
    public long insertFarmer(String name, String birthday, String email, String contact, String address, String password, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("password", password);
        cv.put("latitude", lat);
        cv.put("longitude", lng);
        return db.insert("farmers", null, cv);
    }

    public Cursor loginFarmer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM farmers WHERE email=? AND password=?", new String[]{email, password});
    }

    public Cursor getFarmerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM farmers WHERE id=?", new String[]{String.valueOf(id)});
    }

    public int updateFarmer(int id, String name, String birthday, String email, String contact, String address, String password, double lat, double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("password", password);
        cv.put("latitude", lat);
        cv.put("longitude", lng);
        return db.update("farmers", cv, "id=?", new String[]{String.valueOf(id)});
    }

    // -------------------------------
    // UNIVERSAL USER METHODS
    public Cursor getUser(String type, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (type.equals("customer")) {
            return db.rawQuery("SELECT * FROM customers WHERE id=?", new String[]{String.valueOf(id)});
        } else {
            return db.rawQuery("SELECT * FROM farmers WHERE id=?", new String[]{String.valueOf(id)});
        }
    }

    public int updateUser(String type, int id, String name, String birthday, String email, String contact, String address, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        cv.put("password", password);

        if (type.equals("customer")) {
            return db.update("customers", cv, "id=?", new String[]{String.valueOf(id)});
        } else {
            return db.update("farmers", cv, "id=?", new String[]{String.valueOf(id)});
        }
    }

    // -------------------------------
    // PRODUCT METHODS
    public long insertProduct(int farmerId, String name, String description, int price, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("farmerId", farmerId);
        cv.put("name", name);
        cv.put("description", description);
        cv.put("price", price);
        cv.put("image", image);
        return db.insert("products", null, cv);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM products", null);
    }

    public Cursor getProductsByFarmer(int farmerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM products WHERE farmerId=?", new String[]{String.valueOf(farmerId)});
    }

    public int updateProduct(int productId, String name, String description, int price, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("description", description);
        cv.put("price", price);
        cv.put("image", image);
        return db.update("products", cv, "id=?", new String[]{String.valueOf(productId)});
    }

    public int deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("products", "id=?", new String[]{String.valueOf(productId)});
    }

    // -------------------------------
    // CART METHODS
    public long addToCart(int customerId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("customerId", customerId);
        cv.put("productId", productId);
        cv.put("quantity", quantity);
        return db.insert("cart", null, cv);
    }

    public Cursor getCart(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT cart.id, products.name, products.price, products.image, cart.quantity " +
                "FROM cart INNER JOIN products ON cart.productId = products.id " +
                "WHERE cart.customerId=?", new String[]{String.valueOf(customerId)});
    }

    public int updateCartQuantity(int cartId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("quantity", quantity);
        return db.update("cart", cv, "id=?", new String[]{String.valueOf(cartId)});
    }

    public int removeFromCart(int cartId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cart", "id=?", new String[]{String.valueOf(cartId)});
    }
    // -------------------------------
// PROFILE GET METHODS
    public Cursor getCustomerProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM customers WHERE id=?", new String[]{String.valueOf(id)});
    }

    public Cursor getFarmerProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM farmers WHERE id=?", new String[]{String.valueOf(id)});
    }

    // -------------------------------
// PROFILE UPDATE METHODS
    public boolean updateCustomerProfile(int id, String name, String birthday, String email, String contact, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        int rows = db.update("customers", cv, "id=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public boolean updateFarmerProfile(int id, String name, String birthday, String email, String contact, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birthday", birthday);
        cv.put("email", email);
        cv.put("contact", contact);
        cv.put("address", address);
        int rows = db.update("farmers", cv, "id=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

}

