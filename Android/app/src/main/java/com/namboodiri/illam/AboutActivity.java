package com.namboodiri.illam;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// Activity that displays information about the application
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_about);
    }

    /**
     * Handler for when the source code link is clicked
     * Opens the GitHub repository in the device's default browser
     * @param v The view that was clicked
     */
    public void onSourceClick(View v) {
        // GitHub repository URL
        String url = "http://github.com/deepakcu/illam";
        // Create an intent to view the URL
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        // Launch the browser
        startActivity(i);
    }
}
