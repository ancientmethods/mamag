package com.mamags.mamag.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.adapters.MealTypeFlexItem;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.MealtypeListfragmentBinding;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.MealTypeViewModel;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by samer on 11/11/2017.
 */

public class MealTypeListFragment extends BaseFragment<MealtypeListfragmentBinding, MealTypeViewModel> implements MealTypeView, ActionMode.Callback,
        FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {


    FlexibleAdapter<IFlexible> adapter;
    private ActionMode mActionMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new MealTypeViewModel(ctx.getApplication(), restAPI);
        viewModel.attach(this);

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
        binding.swiperefresh.setRefreshing(true);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below
        MealTypeRequest request = new MealTypeRequest();
        request.CrudOption = RequestAction.list.getValue();

        binding.setRecyclerViewVisibility(true);
        viewModel.getMealTypeList(request);
        binding.newMealType.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMealTypeActivity.class)));

    }


    @Override
    public void loadMealTypeResults(MealTypeListResponse mealTypeListResponse) {
        //recyclerView.setAdapter(new MenuAdapter(14));
        binding.swiperefresh.setRefreshing(false);
        binding.setRecyclerViewVisibility(true);

        List<IFlexible> myItems = new ArrayList<>();
        for (MealType mealType : mealTypeListResponse.getMealTypeList()) {
            MealTypeFlexItem mealTypeFlexItem = new MealTypeFlexItem(mealType);
            myItems.add(mealTypeFlexItem);
        }
        //Initialize the Adapter
        adapter = new FlexibleAdapter<>(myItems);
        adapter.addListener(this);
        adapter.setMode(SelectableAdapter.Mode.MULTI);

        binding.list.setAdapter(adapter);

        // binding.list.setAdapter(new MealTypeAdapter(mealTypeListResponse.getMealTypeList()));
        //DisplayUtils.displaySnackbar(binding.getRoot(),String.valueOf(mealTypeListResponse.getResponseCode()), Snackbar.LENGTH_SHORT,ctx);

    }

    @Override
    public void showNoDataView() {
        binding.setRecyclerViewVisibility(false);
        binding.swiperefresh.setRefreshing(false);
    }


    private void setupRefreshLayout() {

        binding.swiperefresh.setOnRefreshListener(() -> {
            MealTypeRequest request = new MealTypeRequest();
            request.CrudOption = RequestAction.list.getValue();
            viewModel.getMealTypeList(request);
        });
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.generic_edit, menu);


        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemLongClick(int position) {
        mActionMode =  ((AppCompatActivity) getActivity()).startSupportActionMode(this);

        Toast.makeText(ctx, String.valueOf(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemClick(int position) {
        mActionMode =  ((AppCompatActivity) getActivity()).startSupportActionMode(this);

        adapter.toggleSelection(position);

        return true;
    }
}
