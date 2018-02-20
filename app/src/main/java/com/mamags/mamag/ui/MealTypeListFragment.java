package com.mamags.mamag.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.MealTypeAdapter;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.FragmentMenuListBinding;
import com.mamags.mamag.databinding.MealtypeListfragmentBinding;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.interfaces.MenuView;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.MealTypeViewModel;
import com.mamags.mamag.viewmodel.MenuViewModel;

import java.util.List;

/**
 * Created by samer on 11/11/2017.
 */

public class MealTypeListFragment extends BaseFragment<MealtypeListfragmentBinding,MealTypeViewModel> implements MealTypeView {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new MealTypeViewModel(ctx.getApplication(),restAPI);
        viewModel.attach(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindView(inflater, R.layout.mealtype_listfragment,container);
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
        binding.list.setAdapter(new MealTypeAdapter(mealTypeListResponse.getMealTypeList()));
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
            viewModel.getMealTypeList(request);});
    }
}
