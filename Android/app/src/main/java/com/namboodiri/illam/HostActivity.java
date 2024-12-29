package com.namboodiri.illam;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.namboodiri.illam.R;

import org.w3c.dom.Text;

public class HostActivity extends AppCompatActivity {

    // UI components for tab navigation
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        
        // Set up the toolbar as the action bar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        
        // Initialize ViewPager and TabLayout for tab navigation
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        
        // Check if we need to switch to a specific tab (tab 1) based on intent extra
        int a = getIntent().getIntExtra("TAB", 0);
        if (a != 0) {
            viewPager.setCurrentItem(1);
        }
    }

    /**
     * Sets up the ViewPager with two fragments:
     * 1. SearchFragment for browsing
     * 2. RelationshipFragment for finding relations
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), "Browse");
        adapter.addFragment(new RelationshipFragment(), "Find Relation");
        viewPager.setAdapter(adapter);
    }

    /**
     * Handles selection of a person from the relationship cards
     * @param v The clicked view
     */
    public void selectPerson(View v) {
        // Determine which card was clicked (1 or 2)
        int a = (v.getId() == R.id.card_rel1) ? 1 : 2;
        
        // Launch SearchActivity with appropriate parameters
        Intent intent = new Intent(v.getContext(), SearchActivity.class);
        intent.putExtra("CALLER", a);
        intent.putExtra("ACTION", 1);
        startActivity(intent);
    }

    /**
     * Inverts the relationship by swapping the selected persons
     */
    public void invert(View b) {
        RelationshipFragment temp = new RelationshipFragment();
        TextView t1 = findViewById(R.id.rel1);
        TextView t2 = findViewById(R.id.rel2);
        temp.swap(t1, t2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up any ongoing relationship searches
        RelationshipFragment.resetSearch();
    }
}
