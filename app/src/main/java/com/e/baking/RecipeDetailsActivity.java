package com.e.baking;


import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;
import com.e.baking.model.Step;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements MasterListFragment.OnStepClickListener,RecipeRecyclerViewAdapter.ItemClickListener {//implements ReviewsRecyclerViewAdapter.ItemClickListener {
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    //@BindView(R.id.recipe_name_details)
    public TextView mNameTextView;

    List<Step> steps;

    private IngredientDetailsRecyclerViewAdapter ingredientDetailsRecyclerViewAdapter;

    private boolean isTwoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNameTextView = findViewById(R.id.recipe_name_details);

        if(mNameTextView == null){

            isTwoPanel=true;

            Intent intent = getIntent();

            if(intent == null){
                closeOnError();
            }

            int position = intent.getIntExtra(EXTRA_POSITION,DEFAULT_POSITION);

            if(position == DEFAULT_POSITION){
                closeOnError();
                return;
            }

            Recipe recipe = (Recipe) intent.getParcelableExtra("recipe");

            List<Ingredient> ingredientList = recipe.getIngredients();

            List<Step> steps = recipe.getSteps();

            MasterListFragment fragment = MasterListFragment.newInstance("","");
            fragment.setSteps(steps);
            fragment.setRecipe(recipe);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment, /*id of your frame layout*/
                            fragment /*instance of the fragment created in your activity*/)
                    .commit();


        } else {

            Intent intent = getIntent();

            if(intent == null){
                closeOnError();
            }

            int position = intent.getIntExtra(EXTRA_POSITION,DEFAULT_POSITION);

            if(position == DEFAULT_POSITION){
                closeOnError();
                return;
            }

            Recipe recipe = (Recipe) intent.getParcelableExtra("recipe");

            List<Ingredient> ingredientList = recipe.getIngredients();

            steps = recipe.getSteps();

            populateUI(recipe);

            populateIngredients(ingredientList);

            populateSteps(steps);
        }


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void populateUI(Recipe recipe){
        mNameTextView.setText(recipe.getName());
    }

    private void closeOnError(){
        finish();
    }

    public void populateIngredients(List<Ingredient> ingredients){

        if(ingredients != null ){
            RecyclerView recyclerView =  findViewById(R.id.ingredients_recylerview);

            final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(RecipeDetailsActivity.this);

            recyclerView.setLayoutManager(linearLayoutManager);

            IngredientDetailsRecyclerViewAdapter ingredientDetailsRecyclerViewAdapter =  new IngredientDetailsRecyclerViewAdapter(ingredients,RecipeDetailsActivity.this,ingredients.size(),new IngredientDetailsRecyclerViewAdapter.BtnClickListener() {

                @Override
                public void onBtnClick(int position) {
                    // TODO Auto-generated method stub
                }

            });

            recyclerView.setAdapter(ingredientDetailsRecyclerViewAdapter);
        }
    }

    public void populateSteps(List<Step> steps){

        if(steps != null ){
            RecyclerView recyclerView =  findViewById(R.id.steps_recylerview);

            final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(RecipeDetailsActivity.this);

            recyclerView.setLayoutManager(linearLayoutManager);

            StepDetailsRecyclerViewAdapter stepDetailsRecyclerViewAdapter =  new StepDetailsRecyclerViewAdapter(steps,steps.size(),RecipeDetailsActivity.this,new StepDetailsRecyclerViewAdapter.BtnClickListener() {

                @Override
                public void onBtnClick(int position) {
                    // TODO Auto-generated method stub
                }

            });

            stepDetailsRecyclerViewAdapter.setClickListener(RecipeDetailsActivity.this);
            recyclerView.setAdapter(stepDetailsRecyclerViewAdapter);
        }
    }



    @Override
    public void onStepSelected(List<Step> steps, int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // Creating a new video fragment
        VideoFragment videoFragment = new VideoFragment();

        // Add the fragment to its container using a transaction
        videoFragment.setSteps(steps);
        videoFragment.setListIndex(position);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();

        InstructionFragment instructionFragment = new InstructionFragment();

        instructionFragment.setSteps(steps);
        instructionFragment.setListIndex(position);
        // Add the fragment to its container using a transaction
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.instruction_container, instructionFragment)
                        .commit();
    }

    @Override
    public void onItemClick(View view, int position) {
        launchDetailActivity(position);
    }


    /**
     * Displays the details of a recipe (ingredients and steps)
     * @param position
     */
    private void launchDetailActivity(int position) {

        Intent intent = new Intent(this, VideoAndInstructionsActivity.class);
        intent.putExtra(VideoAndInstructionsActivity.EXTRA_POSITION,position);
        Step step = steps.get(position);
        intent.putExtra("step", step);
        startActivity(intent);

    }
}
