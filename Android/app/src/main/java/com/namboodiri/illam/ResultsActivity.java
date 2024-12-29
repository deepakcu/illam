package com.namboodiri.illam;

import android.content.Intent;
import android.database.SQLException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResultsActivity extends AppCompatActivity {

    private String key;
    private DatabaseHelper myDbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Extract search key from intent and remove relationship text in parentheses
        String searchKey = getIntent().getStringExtra("KEY");
        key = searchKey;
        key = key.replaceAll("\\(.*?\\) ?", "");

        // Initialize database connection
        try {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        } catch(SQLException sqle){
            throw sqle;
        }

        // Initialize UI elements for displaying person details
        TextView name = findViewById(R.id.name);
        TextView father = findViewById(R.id.father);
        TextView mother = findViewById(R.id.mother);

        // Get person data from database
        Hashtable<String, Person> persons = myDbHelper.getPersons();
        Person p = myDbHelper.getPerson(persons, key);

        // Find reference person (Deepak) for relationship calculations
        Set<String> keys = persons.keySet();
        Person me = new Person();
        for(String key: keys) {
            if(persons.containsKey(key) && key.contains("Deepak Unnikrishnan")) {
                me = persons.get(key);
                break;
            }
        }

        // Display basic person information
        RelationUtils utils = new RelationUtils();
        name.setText(p.name);
        father.setText(p.father);
        mother.setText(p.mother);

        if(me.name.equals(p.name)) {
            name.setText(p.name);
        } else {
            //name.setText(p.name + "(Your " + utils.getRelation(me.name, p.name, myDbHelper) + ")");
            name.setText(p.name);
        }

        if (p.father != null) {
            father.setText(p.father);
        } else {
            father.setText("N/A");
        }

        if (p.mother != null) {
            mother.setText(p.mother);
        } else {
            mother.setText("N/A");
        }

        //mother.setText(p.mother+"("+utils.getRelation(me.name,p.mother, myDbHelper)+")");
        //Log.e("ILLAM",p.name+"("+utils.getRelation(me.name,p.name, myDbHelper)+")");
        //Log.e("ILLAM", p.father+"("+utils.getRelation(me.name,p.father, myDbHelper)+")");
        //Log.e("ILLAM", p.mother+"("+utils.getRelation(me.name,p.mother, myDbHelper)+")");

        // Set values of dynamic cards (spouse)
        ArrayList<String> spouseWithRels = new ArrayList<>();
        for (String sp : p.spouses) {
            String rel = "(Your " + utils.getRelation(me.name, sp, myDbHelper) + ")";
        }

        // Set up RecyclerView for spouse list
        RecyclerView spouse = findViewById(R.id.recycler_spouse);
        RecyclerViewAdapter adapter_spouse = new RecyclerViewAdapter(p.spouses, this);
        TextView sp = findViewById(R.id.spouse_head);
        if (adapter_spouse.getItemCount() == 0) {
            sp.setVisibility(View.GONE);
        }
        spouse.setAdapter(adapter_spouse);
        spouse.setNestedScrollingEnabled(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        spouse.setLayoutManager(llm);

        // Set up RecyclerView for children list
        RecyclerView child = findViewById(R.id.recycler_child);
        child.setNestedScrollingEnabled(false);
        RecyclerViewAdapter adapter_child = new RecyclerViewAdapter(p.children, this);
        TextView ch = findViewById(R.id.child_head);
        if (adapter_child.getItemCount()==0)
            ch.setVisibility(View.GONE);
        child.setAdapter(adapter_child);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        child.setLayoutManager(llm2);

        // Get references to all three CircleImageViews
        CircleImageView nameImage = findViewById(R.id.profile_image_name);    // You'll need to add this ID
        CircleImageView fatherImage = findViewById(R.id.profile_image_father); // You'll need to add this ID
        CircleImageView motherImage = findViewById(R.id.profile_image);       // This one exists

        // Load images for each person
        loadProfileImage(nameImage, p.name);
        if (p.father != null) {
            loadProfileImage(fatherImage, p.father);
        }
        if (p.mother != null) {
            loadProfileImage(motherImage, p.mother);
        }
    }

    private void loadProfileImage(ImageView imageView, String name) {
        String imageUrl = buildImageUrlFromName(name);
        
        // Check if image exists in assets
        try {
            getAssets().open(imageUrl);
            imageUrl = "file:///android_asset/" + imageUrl;
        } catch (IOException e) {
            imageUrl = "file:///android_asset/profile_def.png";
        }
        
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .circleCrop()
            .into(imageView);
    }

    private String buildImageUrlFromName(String name) {
        return name.toLowerCase()
                  .trim()
                  .replaceAll("\\s+", "_")
                  + ".jpg";
    }

    public void clicked(View v)
    {
        TextView t;
        if(v.getId()== R.id.card2)
            t = findViewById(R.id.father);
        else
            t = findViewById(R.id.mother);
        String selected = t.getText().toString();
        if(!selected.equalsIgnoreCase("N/A")) {
            Intent intent;
            intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("KEY", selected);
            // Log.e("ILLAM: SELECTED: ", selected);
            startActivity(intent);
        }
    }

}
