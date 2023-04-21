package com.example.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercise.dal.SQLiteHelper;
import com.example.exercise.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edTitle, edPrice;
    private Spinner spCategory;
    private TextView tvDate;
    private Button btUpdate, btRemove, btAdd;
    private Item item;
    String[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        edTitle = findViewById(R.id.edTitle);
        edPrice = findViewById(R.id.edPrice);
        spCategory = findViewById(R.id.spCategory);
        tvDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btRemove = findViewById(R.id.btRemove);
        btAdd = findViewById(R.id.btAdd);

        item = (Item) this.getIntent().getSerializableExtra("item");
        edTitle.setText(item.getTitle());
        edPrice.setText(item.getPrice());
        tvDate.setText(item.getDate());
        btUpdate.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        categories = getResources().getStringArray(R.array.category);
        spCategory.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,categories));
        for(int i = 0; i < categories.length ; i++){
            if(categories[i].equals(item.getCategory())){
                spCategory.setSelection(i);
            }
        }
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if(d > 9){
                            date +=  d;
                        }else{
                            date += "0"+d;
                        }
                        date += "/";
                        if(m > 9){
                            date +=  m;
                        }else{
                            date += "0"+m;
                        }
                        date += "/";
                        date += y;
                        tvDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        String title = edTitle.getText().toString();
        String category = spCategory.getSelectedItem().toString();
        String price = edPrice.getText().toString();
        String date = tvDate.getText().toString();
        SQLiteHelper  sql = new SQLiteHelper(this);

        if(view == btUpdate){
            Log.e("CLick here", category);
            item.setTitle(title);
            item.setCategory(category);
            item.setPrice(price);
            item.setDate(date);
            sql.updateItem(item);
            finish();
        }

        if(view == btRemove){
            sql.remove(item);
            finish();
        }

        if(view == btAdd){

            Item item = new Item(title, category, price, date);
            sql.addItem(item);
            finish();
        }
    }
}