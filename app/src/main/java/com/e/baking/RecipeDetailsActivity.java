package com.e.baking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;
import com.e.baking.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipeDetailsActivity extends AppCompatActivity {//implements ReviewsRecyclerViewAdapter.ItemClickListener {
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    //@BindView(R.id.recipe_name_details)
    public TextView mNameTextView;

    private IngredientDetailsRecyclerViewAdapter ingredientDetailsRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        mNameTextView = findViewById(R.id.recipe_name_details);


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

        populateUI(recipe);

        populateIngredients(ingredientList);

        populateSteps(steps);

    }

    public void populateUI(Recipe recipe){
        mNameTextView.setText(recipe.getName());
    }

    private void closeOnError(){
        finish();
        //Toast.makeText(this,R.string.movie_details_not_available,Toast.LENGTH_SHORT);
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

            recyclerView.setAdapter(stepDetailsRecyclerViewAdapter);
        }
    }

    
}
