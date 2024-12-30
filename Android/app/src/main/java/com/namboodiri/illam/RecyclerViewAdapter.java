package com.namboodiri.illam;

import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import android.content.Context;
import android.widget.ImageView;
import android.util.Log;
import java.io.IOException;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> myValues;
    public static int caller = 0;
    public static int toSend = 0;
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> myValues, Context context) {
        this.myValues = myValues;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String name = myValues.get(position);
        holder.myTextView.setText(name);
        
        // Load profile image using the same logic as ResultsActivity
        loadProfileImage(holder.profileImageView, name);
    }

    private void loadProfileImage(ImageView imageView, String name) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String imageUrl = buildImageUrlFromName(name);
        
        // Try lowercase .jpg first
        try {
            context.getAssets().open(imageUrl);
            imageUrl = "file:///android_asset/" + imageUrl;
        } catch (IOException e) {
            // If not found, try uppercase .JPG
            try {
                String upperCaseUrl = imageUrl.replace(".jpg", ".JPG");
                context.getAssets().open(upperCaseUrl);
                imageUrl = "file:///android_asset/" + upperCaseUrl;
            } catch (IOException e2) {
                imageUrl = "file:///android_asset/profile_def.png";
            }
        }
        
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .circleCrop()  // Use circleCrop for spouse/children images
            .into(imageView);
    }

    private String buildImageUrlFromName(String name) {
        if (name == null) return "";
        return name.toLowerCase()
                  .trim()
                  .replaceAll("\\s+", "_")
                  + ".jpg";
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;
        private ImageView profileImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.text_cardview);
            profileImageView = itemView.findViewById(R.id.profile_image);
            CardView myCard = itemView.findViewById(R.id.card);
            
            myCard.setOnClickListener(v -> {
                String selected = (String) myTextView.getText();
                Intent intent;
                
                if (caller == 0) {
                    intent = new Intent(v.getContext(), ResultsActivity.class);
                    intent.putExtra("KEY", selected);
                } else {
                    intent = new Intent(v.getContext(), HostActivity.class);
                    if (toSend == 1) {
                        RelationshipFragment.name1 = selected;
                    } else if (toSend == 2) {
                        RelationshipFragment.name2 = selected;
                    }
                    caller = 0;
                    intent.putExtra("TAB", 1);
                }
                v.getContext().startActivity(intent);
            });
        }
    }
}