package com.alfred.framework.module.model.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alfred.framework.module.model.Dynamic_Bean;
import com.alfred.framework.myframework.R;
import com.alfred.framework.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by asus on 2018/3/10.
 */

public class DynamicCommentViewBinder extends ItemViewBinder<Dynamic_Bean.DynamicList.CommentList, DynamicCommentViewBinder.CommentViewHolder> {

    @NonNull
    @Override
    protected CommentViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new CommentViewHolder(inflater.inflate(R.layout.item_dynamic_comment, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentViewHolder holder, @NonNull Dynamic_Bean.DynamicList.CommentList item) {
        String str;
        StringBuilder builder = new StringBuilder();
        if (getPosition(holder) < 3){
            str = item.name + "：";
            builder.append(str);
            builder.append(item.content);
        }else if(getPosition(holder) == 3){
            str = "查看全部" + getAdapter().getItemCount() + "条评论....";
            builder.append(str);
        } else{
            holder.itemDynamicCommentText.setVisibility(View.GONE);
        }
        holder.itemDynamicCommentText.setText(builder.toString());
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_dynamic_comment_text)
        public TextView itemDynamicCommentText;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
