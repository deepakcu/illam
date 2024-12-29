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

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        int a= 0;
        a = getIntent().getIntExtra("TAB", 0);
        if (a!=0)
            viewPager.setCurrentItem(1);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), "Browse");
        adapter.addFragment(new RelationshipFragment(), "Find Relation");
        viewPager.setAdapter(adapter);
    }

    public void selectPerson(View v)
    {
        int a;
        if (v.getId() == R.id.card_rel1)
            a = 1;
        else
            a = 2;
        Intent intent = new Intent(v.getContext(), SearchActivity.class);
        intent.putExtra("CALLER", a);
        intent.putExtra("ACTION", 1);
        startActivity(intent);
    }

    public void invert(View b)
    {
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

    public void onDestroy()
    {
        super.onDestroy();
        RelationshipFragment.resetSearch();
    }
}
