package com.example.exercise.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise.R;
import com.example.exercise.UpdateDeleteActivity;
import com.example.exercise.adapter.RecycleViewAdapter;
import com.example.exercise.dal.SQLiteHelper;
import com.example.exercise.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private  RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db ;
    private TextView tvTong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        adapter =  new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());

        Date d = new Date();
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        Log.e("Date:",spd.format(d));
        List<Item> items = db.getItemByDate(spd.format(d));
        Log.e("Num item", items.size()+"");
        adapter.setItems(items);
        tvTong.setText("Tong tien: "+tong(items));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    private int tong(List<Item> items){
        int t = 0;
        for(Item item : items){
            t += Integer.parseInt(item.getPrice());
        }
        return  t;
    }
    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> items = db.getItemByDate(spd.format(d));
        adapter.setItems(items);
        tvTong.setText("Tong tien: "+tong(items));
    }
}
