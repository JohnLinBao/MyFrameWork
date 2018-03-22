package com.alfred.framework.module.model.home;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfred.framework.myframework.R;

import java.util.List;

/**
 * Created by asus on 2018/3/6.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list ;
    private Context context;
    private List<Integer> list_image;

    public MyAdapter( Context context, List<String> list, List<Integer> list_image) {
        this.list = list;
        this.context = context;
        this.list_image = list_image;
    }


    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 1;
        }else{
            return  2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_homepage, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_homepage1, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position %2 == 0) {
            ((MyViewHolder)holder).text.setText(list.get(position));

        } else {
            ((ImageViewHolder)holder).imageView.setImageResource(list_image.get(position));

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.item_text);
        }
     }
     public  static class ImageViewHolder extends RecyclerView.ViewHolder{

         private ImageView imageView;
         public ImageViewHolder(View itemView) {
             super(itemView);
             imageView = (ImageView)itemView.findViewById(R.id.item_homapge1_iamge);
         }
     }
//    interface OnChildClickListener {
//
//        public void OnClick(Context context , View){
//
//        }
//    }
}
