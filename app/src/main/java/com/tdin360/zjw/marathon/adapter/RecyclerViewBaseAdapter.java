package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 *
 * 万能 RecyclerView.Adapter
 * Created by admin on 17/8/2.
 * @author 张治军
 */

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEAD=0x01;
    private final int FOOTER=0x02;
    private LayoutInflater inflater;
    private List<T>list;
    private int layoutId;
    private View headerView;
    private View footerView;

    public RecyclerViewBaseAdapter(Context context, List<T>list, int layoutId){

        this.inflater=LayoutInflater.from(context);
        this.list=list;
        this.layoutId=layoutId;

    }

    /**
     * 更新
     * @param list
     */
    public void update(List<T> list){

        this.list=list;
        notifyDataSetChanged();
    }


    /**
     * 删除
     * @param position
     */
    public void removeItem(int position){

        list.remove(position);
         notifyItemRemoved(position);
         notifyItemChanged(position);

    }
    /**
     * 添加头部布局
     * @param headerView
     */
    public void addHeaderView(View headerView){

        this.headerView=headerView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.footerView.setLayoutParams(params);
        notifyItemInserted(0);

    }

    /**
     * 添加头部布局
     * @param layoutId
     */
    public void addHeaderView(@LayoutRes int layoutId){

        View view = inflater.inflate(layoutId, null);
        this.headerView=view;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.headerView.setLayoutParams(params);
        notifyItemInserted(0);

    }

    /**
     * 添加尾部布局
     * @param footerView
     */
    public void addFooter(View footerView){

        this.footerView=footerView;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.footerView.setLayoutParams(params);
        notifyItemInserted(getItemCount());


    }

    /**
     * 添加尾部布局
     * @param layoutId
     */
    public void addFooterView(@LayoutRes int layoutId){

        View view = inflater.inflate(layoutId, null);
        this.footerView=view;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.footerView.setLayoutParams(params);
        notifyItemInserted(getItemCount());

    }

    @Override
    public int getItemViewType(int position) {

        if(position==0)return HEAD;
        if(position+1==getItemCount())return FOOTER;

        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==HEAD&&headerView!=null)return new HeaderViewHolder(headerView);

        if(viewType==FOOTER&&footerView!=null)return new FooterViewHolder(footerView);

        return new NormalViewHolder(inflater.inflate(layoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof NormalViewHolder){

         this.onBindNormalViewHolder((NormalViewHolder) holder,list.get(getPosition(holder)));
        }

        if(holder instanceof HeaderViewHolder){

            onBindHeaderViewHolder((HeaderViewHolder) holder);

        }
        if(holder instanceof FooterViewHolder){

            onBindFooterViewHolder((FooterViewHolder) holder);

        }


    }

    /**
     * 处理gridView 的头和尾的适配
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if(layoutManager instanceof GridLayoutManager){



          final GridLayoutManager mGridLayoutManager = (GridLayoutManager) layoutManager;

            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if((getItemViewType(position)==HEAD)&&headerView!=null||(getItemViewType(position)==FOOTER)&&footerView!=null)
                        return mGridLayoutManager.getSpanCount();
                    return 1;
                }
            });

        }


    }

    /**
     * 处理瀑布流的头尾适配
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);


        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }

        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == getItemCount()-1) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }


    }

    public void onBindHeaderViewHolder(HeaderViewHolder holder){
    }

    protected abstract void onBindNormalViewHolder(NormalViewHolder holder,T model);
    public void onBindFooterViewHolder(FooterViewHolder holder){

    }


    @Override
    public int getItemCount() {

        if(headerView==null&&footerView==null)return list==null?0:list.size();
        if(footerView!=null&&headerView!=null)return list==null?0:list.size()+2;
        return list==null? 0:list.size()+1;
    }


    public int getPosition(RecyclerView.ViewHolder holder){

        return headerView==null? holder.getAdapterPosition():holder.getAdapterPosition()-1;
    }


    /**
     * 头布局holder
     * @param <T>
     */
    public  class HeaderViewHolder<T> extends RecyclerView.ViewHolder implements Common{


        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

       public <T extends View> T getViewById(@IdRes int id){

           return RecyclerViewBaseAdapter.this.getViewById(id,itemView);
       }

        public void setText(@IdRes int id,CharSequence text){

            RecyclerViewBaseAdapter.this.setText(id,text,itemView);
        }
        public void setTextResource(@IdRes int id, @StringRes int resId){

            RecyclerViewBaseAdapter.this.setTextResource(id,resId,itemView);
        }

       @Override
       public void setBitmap(@IdRes int id, Bitmap bitmap) {

           RecyclerViewBaseAdapter.this.setBitmap(id,bitmap,itemView);

       }

       @Override
       public void setImageResource(@IdRes int id, @DrawableRes int resId) {

           RecyclerViewBaseAdapter.this.setImageResource(id,resId,itemView);

       }

   }

    /**
     * 普通布局holder
     * @param <T>
     */
      public class NormalViewHolder<T> extends RecyclerView.ViewHolder implements Common{


       public NormalViewHolder(View itemView) {
           super(itemView);



           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(listener!=null){

                       listener.onItemClick(v, headerView==null? getAdapterPosition():getAdapterPosition()-1);
                   }
               }
           });

           itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {

                   if(longClickListener!=null){

                       longClickListener.onLongClick(v,headerView==null?getAdapterPosition():getAdapterPosition()-1);
                   }
                   return true;
               }
           });

       }


          public <T extends View> T getViewById(@IdRes int id){

              return RecyclerViewBaseAdapter.this.getViewById(id,itemView);
          }

          public void setText(@IdRes int id,CharSequence text){

              RecyclerViewBaseAdapter.this.setText(id,text,itemView);
          }
          public void setTextResource(@IdRes int id, @StringRes int resId){

              RecyclerViewBaseAdapter.this.setTextResource(id,resId,itemView);
          }

          @Override
          public void setBitmap(@IdRes int id, Bitmap bitmap) {

              RecyclerViewBaseAdapter.this.setBitmap(id,bitmap,itemView);

          }

          @Override
          public void setImageResource(@IdRes int id, @DrawableRes int resId) {

              RecyclerViewBaseAdapter.this.setImageResource(id,resId,itemView);

          }


      }


    /**
     * 尾布局holder
     * @param <T>
     */
     public class FooterViewHolder<T> extends RecyclerView.ViewHolder implements Common{


        public FooterViewHolder(View itemView) {
            super(itemView);

        }


        public <T extends View> T getViewById(@IdRes int id){


            return RecyclerViewBaseAdapter.this.getViewById(id,itemView);
        }

        @Override
        public void setText(@IdRes int id, CharSequence text) {

            RecyclerViewBaseAdapter.this.setText(id,text,itemView);
        }

        @Override
        public void setTextResource(@IdRes int id, @StringRes int resId) {

            RecyclerViewBaseAdapter.this.setTextResource(id,resId,itemView);
        }

        @Override
        public void setBitmap(@IdRes int id, Bitmap bitmap) {

            RecyclerViewBaseAdapter.this.setBitmap(id,bitmap,itemView);
        }

        @Override
        public void setImageResource(@IdRes int id, @DrawableRes int resId) {

            RecyclerViewBaseAdapter.this.setImageResource(id,resId,itemView);
        }
    }


   protected interface Common{

       <T extends View> T getViewById(@IdRes int id);
       void setText(@IdRes int id, CharSequence text);
       void setTextResource(@IdRes int id, @StringRes int resId);
       void setBitmap(@IdRes int id, Bitmap bitmap);
       void setImageResource(@IdRes int id, @DrawableRes int resId);
   }



   private <T extends View> T getViewById(@IdRes int id,View itemView){

       View view = itemView.findViewById(id);

       return (T) view;

   }
   private void setText(@IdRes int id,CharSequence text,View itemView){

       TextView tv = (TextView) itemView.findViewById(id);
       tv.setText(text);


   }
   private void setTextResource(@IdRes int id,@StringRes int resId,View itemView){

       TextView tv = (TextView) itemView.findViewById(id);
       String str = itemView.getContext().getResources().getString(resId);
       tv.setText(str);

   }

   private void setBitmap(@IdRes int id,Bitmap bitmap,View itemView){

       ImageView view = (ImageView) itemView.findViewById(id);
       view.setImageBitmap(bitmap);

   }

   private void setImageResource(@IdRes int id,@DrawableRes int resId,View itemView){

       ImageView view = (ImageView) itemView.findViewById(id);
       view.setImageResource(resId);
   }


    public interface OnItemClickListener{

        void onItemClick(View view, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;
    }

    public interface OnLongClickListener{

        void onLongClick(View view, int position);
    }

    private  OnLongClickListener longClickListener;
    public void setLongClickListener(OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
