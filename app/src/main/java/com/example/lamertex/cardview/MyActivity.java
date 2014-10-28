package com.example.lamertex.cardview;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    final ArrayList <String> mDataSet=new ArrayList<String>();
    String [] myDataSet={"Cupcake","Donut","Eclair","Froyo","Gingerbread","Honeycomb","Icecreamsandwich","Jellybean","Kitkat","Lollipop"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reciclerview);
        //creating the toolbar
        Toolbar toolbar= (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //creating the drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.text,myDataSet));

        //implementing the arrow animation
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_closed);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //creating the layout manager and the adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator(){
            @Override
            public void onRemoveFinished(RecyclerView.ViewHolder item) {
                super.onRemoveFinished(item);
            }
            @Override
            public void onMoveFinished(RecyclerView.ViewHolder item) {
                super.onMoveFinished(item);
                mAdapter.notifyDataSetChanged();
            }
        });

        for(String a:myDataSet)
            mDataSet.add(a);
        mAdapter = new MyAdapter(mDataSet,this);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_text) {
            if(mDataSet.size()<10) {
                int i;
                for(i=0;i<myDataSet.length-1 && mDataSet.contains(myDataSet[i]);i++){}
                mDataSet.add(i,myDataSet[i]);
                mAdapter.notifyItemInserted(i);
            }
        }
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        int itemId = item.getItemId();
        return itemId == R.id.action_settings || super.onOptionsItemSelected(item);

    }
}