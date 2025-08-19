package com.s23010403.conutx;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class KnowledgeCenterActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    KnowledgeAdapter adapter;
    ArrayList<KnowledgeItem> knowledgeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_center);

        recyclerView = findViewById(R.id.recyclerViewKnowledge);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        knowledgeList = new ArrayList<>();

        // Sample knowledge items
        knowledgeList.add(new KnowledgeItem("Coconut Farming Tips", "Learn how to grow healthy coconuts."));
        knowledgeList.add(new KnowledgeItem("Benefits of Coconut Oil", "Discover the health benefits of coconut oil."));
        knowledgeList.add(new KnowledgeItem("Coconut Product Recipes", "Easy recipes using coconut products."));

        adapter = new KnowledgeAdapter(this, knowledgeList);
        recyclerView.setAdapter(adapter);
    }
}
