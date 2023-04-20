package com.example.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.exercise.dal.SQLiteHelper;
import com.example.exercise.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText edTitle, edPrice;
    private Spinner spCategory;
    private TextView tvDate;
    private Button  btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edTitle = findViewById(R.id.edTitle);
        edPrice = findViewById(R.id.edPrice);
        spCategory = findViewById(R.id.spCategory);
        tvDate = findViewById(R.id.tvDate);
        btAdd = findViewById(R.id.btAdd);

        spCategory.setAdapter(new ArrayAdapter <String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edTitle.getText().toString();
                String category = spCategory.getSelectedItem().toString();
                String price = edPrice.getText().toString();
                String date = tvDate.getText().toString();
                Item item = new Item(title, category, price, date);
                SQLiteHelper sql = new SQLiteHelper(AddActivity.this);
                sql.addItem(item);
                finish();
            }
        });
    }

}