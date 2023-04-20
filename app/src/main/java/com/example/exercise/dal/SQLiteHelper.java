package com.example.exercise.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.exercise.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

     public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE items (\n" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  title TEXT NOT NULL,\n" +
                "  category TEXT NOT NULL,\n" +
                "  price TEXT NOT NULL,\n" +
                "  date TEXT NOT NULL\n" +
                ");\n";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Item> getAllItem(){
        List<Item> items = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor cursor = st.query("items",null, null, null,null,null,order);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String price = cursor.getString(3);
            String date = cursor.getString(4);
            items.add(new Item(id, title,category,price,date));
        }
        return  items;
    }

    public long addItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());

        SQLiteDatabase sql = getWritableDatabase();
        return sql.insert("items", null, values);
    }

    public  void updateItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase sql = getWritableDatabase();
        String[] args = {item.getId()+""};
        sql.update("items", values, "id=?", args);
    }
    public List<Item> getItemByDate(String date){
        List<Item> items = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase sql = getReadableDatabase();
        Cursor cursor = sql.query("items", null, whereClause,whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String price = cursor.getString(3);
            String d = cursor.getString(4);
            items.add(new Item(id, title,category,price,d));
        }
        return items;
    }

    public void remove(Item item){
        SQLiteDatabase sql = getWritableDatabase();
        String[] args = {item.getId()+""};
        sql.delete("items","id=?",args);
    }

    public List<Item> findBy(String s){
        List<Item> items = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        String q = "title like ?";
        String[] args = {"%"+s+"%"};
        Cursor cursor = st.query("items",null, q, args,null,null,order);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String price = cursor.getString(3);
            String date = cursor.getString(4);
            items.add(new Item(id, title,category,price,date));
        }
        return  items;
    }

}
