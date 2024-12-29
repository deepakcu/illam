package com.namboodiri.illam;


import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.IOException;


public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout and get reference to the search view
        final View v = inflater.inflate(R.layout.fragment_search, container, false);
        final FloatingSearchView mSearchView = v.findViewById(R.id.floating_search_fragment);
        
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                // Handle search suggestions when implemented
            }

            @Override
            public void onSearchAction(String query) {
                // Get reference to the RecyclerView that will display results
                final RecyclerView myView = v.findViewById(R.id.recycler_frag);
                DatabaseHelper myDbHelper = new DatabaseHelper(myView.getContext());
                
                // Initialize the database
                try {
                    myDbHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                
                // Open database connection
                try {
                    myDbHelper.openDataBase();
                } catch (SQLException sqle) {
                    throw sqle;
                }
                
                // Set up RecyclerView with data from database query
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(myDbHelper.getDbData(query), requireContext());
                myView.setAdapter(adapter);
                
                // Configure RecyclerView layout and make it visible
                LinearLayoutManager llm = new LinearLayoutManager(myView.getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                myView.setLayoutManager(llm);
                myView.setVisibility(View.VISIBLE);
            }
        });
        
        return v;
    }

}
