package com.e.baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.e.baking.model.Ingredient;
import com.e.baking.model.Recipe;
import com.e.baking.model.Step;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterListFragment extends Fragment implements RecipeRecyclerViewAdapter.ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Step> steps = new ArrayList<>();


    private Recipe recipe;

    private OnFragmentInteractionListener mListener;

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    public MasterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MasterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MasterListFragment newInstance(String param1, String param2) {
        MasterListFragment fragment = new MasterListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        if(recipe != null) {
            populateIngredients(recipe.getIngredients(), rootView);
        }

        populateSteps(steps,rootView);


        return rootView;
        //return inflater.inflate(R.layout.fragment_master_list, container, false);
        //final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
    }

    public void populateSteps(List<Step> steps,View rootView){

        if(steps != null ){
            RecyclerView recyclerView =  rootView.findViewById(R.id.steps_recylerview);

            final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this.getActivity());

            recyclerView.setLayoutManager(linearLayoutManager);

          /* recyclerView.setOnItemClickListener(new StepDetailsRecyclerViewAdapter.ItemClickListener() {


               @Override
               public void onItemClick(View view, int position) {
                   mCallback.onStepSelected(position);
               }
            });*/

            StepDetailsRecyclerViewAdapter stepDetailsRecyclerViewAdapter =  new StepDetailsRecyclerViewAdapter(steps,steps.size(),this.getActivity(),new StepDetailsRecyclerViewAdapter.BtnClickListener() {

                @Override
                public void onBtnClick(int position) {
                    // TODO Auto-generated method stub
                    System.out.println("STEP SELECTED");
                }

            });

            stepDetailsRecyclerViewAdapter.setClickListener(this);

            recyclerView.setAdapter(stepDetailsRecyclerViewAdapter);
        }
    }

    public void populateUI(Recipe recipe){
       // mNameTextView.setText(recipe.getName());
    }


    public void populateIngredients(List<Ingredient> ingredients,View rootView){

        if(ingredients != null ){
            RecyclerView recyclerView =  rootView.findViewById(R.id.ingredients_recylerview);

            final LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this.getActivity());

            recyclerView.setLayoutManager(linearLayoutManager);

            IngredientDetailsRecyclerViewAdapter ingredientDetailsRecyclerViewAdapter =  new IngredientDetailsRecyclerViewAdapter(ingredients,this.getActivity(),ingredients.size(),new IngredientDetailsRecyclerViewAdapter.BtnClickListener() {

                @Override
                public void onBtnClick(int position) {
                    // TODO Auto-generated method stub
                    System.out.println("INGREDIENTS SELECTED");
                }

            });

            recyclerView.setAdapter(ingredientDetailsRecyclerViewAdapter);

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction1(uri);
        }*/
    }

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, int position) {

       mCallback.onStepSelected(steps,position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(List<Step> steps,int position);
    }
}
