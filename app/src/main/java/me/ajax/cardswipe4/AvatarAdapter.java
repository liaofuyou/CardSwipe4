package me.ajax.cardswipe4;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by aj on 2018/4/23
 */


//自定义Adapter
public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyViewHolder> {

    private ArrayList<String> myData;

    public AvatarAdapter() {

        int size = 13;
        myData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            myData.add("str:" + i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_avatar, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setStr(myData.get(position));
        holder.setStr(position + "");
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    //自定义Holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView strTv;

        Random random = new Random();

        public MyViewHolder(View itemView) {
            super(itemView);
            strTv = (TextView) itemView.findViewById(R.id.text);

            //itemView.setBackgroundColor(getRandomColor());
        }

        public void setStr(String str) {
            strTv.setText(str);
        }

    }
}

