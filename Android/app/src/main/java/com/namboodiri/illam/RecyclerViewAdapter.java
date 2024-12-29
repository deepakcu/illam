package com.namboodiri.illam;

import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    public ArrayList<String> myValues;
    public static int caller = 0;
    public static int toSend = 0;
    public RecyclerViewAdapter (ArrayList<String> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.myTextView.setText(myValues.get(position));
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.text_cardview);
            CardView myCard = itemView.findViewById(R.id.card);
            myCard.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v) {
                    String selected = (String)myTextView.getText();
                    Intent intent;
                    if(caller == 0) {
                        intent = new Intent(v.getContext(), ResultsActivity.class);
                        intent.putExtra("KEY", selected);
                    }
                    else
                    {
                        intent = new Intent(v.getContext(), HostActivity.class);
                        if(toSend==1)
                            RelationshipFragment.name1 = selected;
                        else if (toSend==2)
                            RelationshipFragment.name2 = selected;
                        caller = 0;
                        intent.putExtra("TAB", 1);
                    }
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}