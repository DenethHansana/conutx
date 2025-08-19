package com.s23010403.conutx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.KnowledgeViewHolder> {

    Context context;
    ArrayList<KnowledgeItem> knowledgeList;

    public KnowledgeAdapter(Context context, ArrayList<KnowledgeItem> knowledgeList){
        this.context = context;
        this.knowledgeList = knowledgeList;
    }

    @Override
    public KnowledgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_knowledge, parent, false);
        return new KnowledgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KnowledgeViewHolder holder, int position){
        KnowledgeItem item = knowledgeList.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtDesc.setText(item.getDescription());
    }

    @Override
    public int getItemCount(){
        return knowledgeList.size();
    }

    public static class KnowledgeViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtDesc;

        public KnowledgeViewHolder(View itemView){
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtKnowledgeTitle);
            txtDesc = itemView.findViewById(R.id.txtKnowledgeDesc);
        }
    }
}
