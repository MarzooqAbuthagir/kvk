package com.kvknamakkal;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter implements AdapterView.OnItemSelectedListener {
    List<TableDataClass> listValue;
    Context cont;

    public TableViewAdapter(Context cont, List<TableDataClass> arraylist) {
        this.listValue = arraylist;
        this.cont = cont;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (position == 0) {
            rowViewHolder.text1.setTypeface(rowViewHolder.text1.getTypeface(), Typeface.BOLD);
            rowViewHolder.text2.setTypeface(rowViewHolder.text2.getTypeface(), Typeface.BOLD);
            rowViewHolder.text3.setTypeface(rowViewHolder.text3.getTypeface(), Typeface.BOLD);
            rowViewHolder.text4.setTypeface(rowViewHolder.text4.getTypeface(), Typeface.BOLD);
            rowViewHolder.text5.setTypeface(rowViewHolder.text5.getTypeface(), Typeface.BOLD);
            rowViewHolder.text6.setTypeface(rowViewHolder.text6.getTypeface(), Typeface.BOLD);
        } else {
            rowViewHolder.text1.setTypeface(rowViewHolder.text1.getTypeface(), Typeface.BOLD);
        }

        TableDataClass modal = listValue.get(rowPos);

        rowViewHolder.text1.setText(modal.getContent1());
        rowViewHolder.text2.setText(modal.getContent2());
        rowViewHolder.text3.setText(modal.getContent3());
        rowViewHolder.text4.setText(modal.getContent4());
        rowViewHolder.text5.setText(modal.getContent5());
        rowViewHolder.text6.setText(modal.getContent6());
    }

    @Override
    public int getItemCount() {
        return listValue.size(); // one more to add header row
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView text1;
        protected TextView text2;
        protected TextView text3;
        protected TextView text4;
        protected TextView text5;
        protected TextView text6;

        public RowViewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.textView1);
            text2 = itemView.findViewById(R.id.textView2);
            text3 = itemView.findViewById(R.id.textView3);
            text4 = itemView.findViewById(R.id.textView4);
            text5 = itemView.findViewById(R.id.textView5);
            text6 = itemView.findViewById(R.id.textView6);
        }
    }
}

