package com.example.lamertex.cardview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by lamertex on 24/10/14.
 */
public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder>{
    private ArrayList<String> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mViewI;
        public CardView mViewC;
        public ViewHolder(View v){
            super(v);
            mViewI = (ImageView) v.findViewById(R.id.image);
            mViewC = (CardView) v.findViewById(R.id.card);
        }
    }

    public MyAdapter(ArrayList<String> myDataSet,Context context){
        mDataSet=myDataSet;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my,parent,false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        String name=mDataSet.get(position).toLowerCase();

        final int resImage=mContext.getResources().getIdentifier(name,"drawable",mContext.getPackageName());
        final BitmapWorkerTask bitmap=new BitmapWorkerTask(holder.mViewI,mContext);
        bitmap.execute(resImage);



        holder.mViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemFromData(position);
            }
        });
        holder.mViewC.setOnLongClickListener(new View.OnLongClickListener(){
           @Override
           public boolean onLongClick(View v){
               //itemMoved(position);
               if(holder.mViewC.getCardElevation()==3) {
                   holder.mViewC.setCardElevation(15);
                   Palette.generateAsync(BitmapFactory.decodeResource(mContext.getResources(), resImage), new Palette.PaletteAsyncListener() {
                       @Override
                       public void onGenerated(Palette palette) {
                           holder.mViewC.setBackgroundColor(palette.getVibrantColor(000));
                       }
                   });
               }
               else {
                   holder.mViewC.setCardElevation(3);
               }
               return true;
           }
        });
    }

    public void removeItemFromData(int position){
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
    public void itemMoved(int position){
        if(position<mDataSet.size()-1) {
            mDataSet.add(position + 1, mDataSet.remove(position));
            notifyItemMoved(position,position + 1);
        }
    }
    public void addList(){
        mDataSet.add("aggiunto");
        notifyItemInserted(mDataSet.size());
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

}
