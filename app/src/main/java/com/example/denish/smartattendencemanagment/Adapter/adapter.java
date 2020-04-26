package com.example.denish.smartattendencemanagment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.denish.smartattendencemanagment.Activity.AttendenceAcitvity;
import com.example.denish.smartattendencemanagment.Model.Classes.Item;
import com.example.denish.smartattendencemanagment.R;

import java.util.ArrayList;

/**
 * Created by Denish on 31-10-2018.
 */

public class adapter extends RecyclerView.Adapter<adapter.Holder>{

    AttendenceAcitvity attendenceAcitvity;
    ArrayList<Item> datalist;
    LayoutInflater inflater;
    public static  SparseBooleanArray mSelectedItemsIds;
    public adapter(AttendenceAcitvity context, ArrayList<Item> responselist) {
        this.attendenceAcitvity=context;
        this.datalist=responselist;
        inflater = LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();

    }

    @Override
    public adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items, parent, false);

        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(adapter.Holder holder, final int position) {
        holder.txt_fname.setText(datalist.get(position).getFname());
        holder.txt_mname.setText(datalist.get(position).getMname());
        holder.txt_stid.setText(datalist.get(position).getSt_id());
        holder.chkbox.setChecked(mSelectedItemsIds.get(position));
        holder.chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(position, !mSelectedItemsIds.get(position));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCheckBox(position, !mSelectedItemsIds.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    /**
     * Remove all checkbox Selection
     **/
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, true);
            Log.e("AddCheckBox: ", "" + position);
        } else {
            mSelectedItemsIds.delete(position);
            Log.e("RemoveCheckBox: ", "" + position);
        }
        //demo.getData(studcount.size());
        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public static SparseBooleanArray getmSelectedIds() {
        return mSelectedItemsIds;
    }



    public class Holder extends RecyclerView.ViewHolder {

        TextView txt_stid,txt_fname,txt_mname;
        CheckBox chkbox;
        public Holder(View itemView) {
            super(itemView);
            txt_fname = (TextView)itemView.findViewById(R.id.txt_fname);
            txt_mname = (TextView)itemView.findViewById(R.id.txt_mname);
            txt_stid = (TextView)itemView.findViewById(R.id.txt_stid);
            chkbox = (CheckBox)itemView.findViewById(R.id.chkbox);
        }
    }
}
