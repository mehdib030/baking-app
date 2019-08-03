package com.e.baking;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Recipe> recipes;
    @BindView(R.id.recipe_recycler_view)public RecyclerView recyclerView;


    private List<Recipe> recipeList = new ArrayList();

    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

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
        Log.i(TAG,"You clicked on "+recipeRecyclerViewAdapter.getItem(position)+ " at position "+position);
        launchDetailActivity(position);
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

                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

                recyclerView.setLayoutManager(linearLayoutManager);

                recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(recipes,recipes.size(),MainActivity.this);

                recipeRecyclerViewAdapter.setClickListener(MainActivity.this);

                recyclerView.setAdapter(recipeRecyclerViewAdapter);
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
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RecipeDetailsActivity.EXTRA_POSITION,position);
        Recipe recipe = recipeList.get(position);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }


}
