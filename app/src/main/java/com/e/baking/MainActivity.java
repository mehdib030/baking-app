package com.e.baking;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Baking app main activity class
 */
public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ItemClickListener {//implements MasterListFragment.OnImageClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> recipes;
    @BindView(R.id.recipe_recycler_phone_view)
    @Nullable
    public RecyclerView recyclerView;


    @BindView(R.id.recipe_recycler_tablet_view)
    @Nullable
    public RecyclerView recyclerTabletView;

    private boolean isTwoPanel;


    private List<Recipe> recipeList = new ArrayList();

    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new RecipesAsyncTask().execute();

    }

    @Override
    public void onItemClick(View view, int position) {
        //Log.i(TAG,"You clicked on "+recipeRecyclerViewAdapter.getItem(position)+ " at position "+position);
        if(isTwoPanel){
            launchDetailActivity(position);
        } else {
            launchDetailActivity(position);
        }

    }

    public class RecipesAsyncTask extends AsyncTask<String,Integer, List<Recipe>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Recipe> doInBackground(String... strings) {

            return loadRecipes();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);

            if(recipes != null){
                if(recyclerView != null) {

                    isTwoPanel = false;

                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

                    recyclerView.setLayoutManager(linearLayoutManager);

                    recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(recipes,recipes.size(),MainActivity.this);

                    recipeRecyclerViewAdapter.setClickListener(MainActivity.this);

                    recyclerView.setAdapter(recipeRecyclerViewAdapter);

                } else {
                    isTwoPanel=true;

                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);

                    recyclerTabletView.setLayoutManager(gridLayoutManager);

                    recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(recipes,recipes.size(),MainActivity.this);

                    recipeRecyclerViewAdapter.setClickListener(MainActivity.this);

                    recyclerTabletView.setAdapter(recipeRecyclerViewAdapter);
                }


            }
        }
    }

    public List<Recipe> loadRecipes(){

        String json = null;
        try {
            json = readUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        recipeList = gson.fromJson(json, new TypeToken<List<Recipe>>(){}.getType());


        return recipeList;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * Displays the details of a recipe (ingredients and steps)
     * @param position
     */
    private void launchDetailActivity(int position) {
        Recipe recipe = recipeList.get(position);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(recipe);

        editor.putString("recipe", json);
        editor.commit();

        Intent intent1 = new Intent(this, RecipeWidgetProvider.class);
        intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        //)getAppWidgetI‌​ds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        sendBroadcast(intent1);

        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.EXTRA_POSITION,position);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }

}
