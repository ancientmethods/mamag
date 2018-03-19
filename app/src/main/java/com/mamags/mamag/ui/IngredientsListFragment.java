package com.mamags.mamag.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.adapters.DefaultListItem;
import com.mamags.mamag.api.ResponseCode;
import com.mamags.mamag.databinding.MealtypeListfragmentBinding;
import com.mamags.mamag.interfaces.IngredientsView;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.Ingredient;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.IngredientsRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Responses.IngredientsResponse;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.CRUDViewModel;
import com.mamags.mamag.viewmodel.IngredientsViewModel;
import com.mamags.mamag.viewmodel.MealTypeViewModel;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by samer on 19/03/2018.
 */


public class IngredientsListFragment extends BaseFragment<MealtypeListfragmentBinding, IngredientsViewModel> implements IngredientsView, ActionMode.Callback,
        FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {


    FlexibleAdapter<IFlexible> adapter;

    private CRUDViewModel crudViewModel;
    int mActivatedPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new IngredientsViewModel(ctx.getApplication(), restAPI);
        viewModel.attach(this);

        crudViewModel = new CRUDViewModel(ctx.getApplication(), restAPI);
        crudViewModel.attach(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindView(inflater, R.layout.mealtype_listfragment, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        setupRefreshLayout();

        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below
        binding.setRecyclerViewVisibility(true);
        showLoadingStatus();


        binding.newrecord.setOnClickListener(v -> startActivity(new Intent(ctx, CreateIngredientActivity.class)));

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {

        viewModel.getIngredientsList(apiRequests.getIngredients());
    }



    boolean deleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle(getString(R.string.warning));
        alertDialog.setMessage(getString(R.string.delete_mealtype_warning));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), (dialog, which) -> deleteItems());
        alertDialog.show();
        return true;
    }


    void deleteItems() {
        try {
            List<Integer> selectedItems = adapter.getSelectedPositions();
            DefaultListItem flexItem = (DefaultListItem) adapter.getItem(selectedItems.get(0));
            MealTypeRequest deleteMealTypeRequest = apiRequests.deleteMealType(flexItem.getMealType().getId());


            crudViewModel.createMealTypeDisposable(deleteMealTypeRequest);
            viewModel.getmActionModeHelper().destroyActionModeIfCan();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "error");
        }

    }

    void showLoadingStatus() {
        binding.swiperefresh.setRefreshing(true);
    }

    void showLoadedStatus() {
        binding.swiperefresh.setRefreshing(false);
        binding.setRecyclerViewVisibility(true);
    }

    //process response after delete
    @Override
    public void processStandardResponse(FDSresponse fdSresponse, boolean shouldClose) {
        if (fdSresponse.getResponseCode() == ResponseCode.SUCCESS) {
            displayUtils.displaySuccessMessage(binding.getRoot());
            adapter.removeItem(mActivatedPosition);
            adapter.notifyDataSetChanged();
        } else {
            showLoadedStatus();
        }
        super.processStandardResponse(fdSresponse, shouldClose);
    }



    @Override
    public void showNoDataView() {
        binding.setRecyclerViewVisibility(false);
        binding.swiperefresh.setRefreshing(false);
    }


    private void setupRefreshLayout() {

        binding.swiperefresh.setOnRefreshListener(() -> {
            loadData();
        });
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        deleteDialog();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemLongClick(int position) {

        if (position != mActivatedPosition) setActivatedPosition(position);

        //make sure only the currently selected item will get selected again as we are in single selection mode
        if (adapter.getSelectedPositions().size() == 0 || adapter.getSelectedPositions().contains(position)) {
            viewModel.getmActionModeHelper().onLongClick(ctx, position);
        }


    }

    @Override
    public boolean onItemClick(int position) {

        if (viewModel.getmActionModeHelper().getActionMode() != null) {
            return false;
        }

        if (position != mActivatedPosition) setActivatedPosition(position);



        //go to edit screen activity
        try {
            Intent intent = new Intent(ctx, CreateIngredientActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("ingredient", (DefaultListItem) adapter.getItem(mActivatedPosition));

            startActivity(intent);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return true; //Important!
    }

    private void setActivatedPosition(int position) {
        mActivatedPosition = position;
        //adapter.toggleSelection(position); //Important!
    }

    @Override
    public void loadIngredientsList(IngredientsResponse ingredientListResponse) {
        List<IFlexible> myItems = new ArrayList<>();
        for (Ingredient ingredient : ingredientListResponse.getIngredientList()) {
            DefaultListItem ingredientFlexItem = new DefaultListItem(ingredient,2);
            myItems.add(ingredientFlexItem);
        }
        //Initialize the Adapter
        adapter = viewModel.displayFlexibleAdapter(myItems,binding.list, this);
        adapter.addListener(this);
        showLoadedStatus();
        displayUtils.displaySuccessMessage(binding.getRoot());
    }
}

