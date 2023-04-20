package com.example.exercise.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise.R;
import com.example.exercise.adapter.RecycleViewAdapter;
import com.example.exercise.dal.SQLiteHelper;
import com.example.exercise.model.Item;

import java.util.List;

public class FragmentSearch extends Fragment {
    private SearchView searchView;
    private Spinner spCategory;
    private Button btSearch;
    private EditText edFrom, edTo;
    private RecyclerView recycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.search);
        spCategory = view.findViewById(R.id.spCategory);
        btSearch = view.findViewById(R.id.btSearch);
        edTo = view.findViewById(R.id.edTo);
        edFrom = view.findViewById(R.id.edFrom);
        recycleView = view.findViewById(R.id.recycleView);

        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleView.setAdapter(recycleViewAdapter);
        recycleView.setLayoutManager(manager);
        SQLiteHelper sql = new SQLiteHelper(getContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> items =  sql.findBy(s);
                recycleViewAdapter.setItems(items);
                return false;
            }
        });

        return view;
    }
}
