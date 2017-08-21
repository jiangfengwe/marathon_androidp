package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.activity.PhotoBrowseActivity;
import com.tdin360.zjw.marathon.weight.SpaceItemDecoration;

/**
 * 圈友
 * Created by admin on 17/7/24.
 */

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.MyViewHolder>{


    private LayoutInflater inflater;
    public CircleListAdapter(Context context){

        this.inflater=LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.circle_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.recyclerView.setAdapter(new ImageListAdapter());
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);

         this.recyclerView= (RecyclerView) itemView.findViewById(R.id.mRecyclerView);
         this.recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
         this.recyclerView.addItemDecoration(new SpaceItemDecoration(5,3));

        }
    }

    class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder>{


        @Override
        public ImageListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ImageView imageView = new ImageView(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
            imageView.setLayoutParams(params);
             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return new MyViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(ImageListAdapter.MyViewHolder holder, int position) {

            holder.imageView.setImageResource(R.drawable.guide0);
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private  ImageView imageView;
            public MyViewHolder(final View itemView) {
                super(itemView);
                imageView= (ImageView) itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        itemView.getContext().startActivity(new Intent(itemView.getContext(), PhotoBrowseActivity.class));
                    }
                });
            }
        }
    }
}


