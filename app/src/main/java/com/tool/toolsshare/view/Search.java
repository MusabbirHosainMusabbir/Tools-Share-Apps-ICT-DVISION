package com.tool.toolsshare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tool.Utils.Utils;
import com.tool.adapter.ItemListAdapter;
import com.tool.toolsshare.R;

public class Search extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    SearchView toolSearch, mainSearch;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ItemListAdapter itemListAdapter;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolSearch = findViewById(R.id.searchviewToolbar);
        mainSearch = findViewById(R.id.main_searchbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        toolbar = findViewById(R.id.tool_bar);
        itemListAdapter = new ItemListAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        search = findViewById(R.id.search_img);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Search.this,Profile.class));
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemListAdapter);

        Utils.fullScreenView(this,false);

        if (!Utils.hasNavBar(this)){
            Log.e("hasNav--->", "onCreate: YES---> " );
            Utils.adjustBottomNav(this,R.id.soft_key_layout,R.id.main_layout);
            //navLayout.setVisibility(View.GONE);

        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                Log.e("----->", "onOffsetChanged: "+i+" "+toolbar.getHeight() );

                if (i < -toolbar.getHeight()) {
                    //toolbar is collapsed here
                    //write your code here
                    toolSearch.setVisibility(View.VISIBLE);
                }
                else {
                    toolSearch.setVisibility(View.GONE);
                }


            }
        });
    }


}
