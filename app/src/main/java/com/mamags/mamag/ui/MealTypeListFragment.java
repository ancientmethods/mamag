package com.mamags.mamag.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.mamags.mamag.api.API_Requests;
import com.mamags.mamag.api.ResponseCode;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.databinding.MealtypeListfragmentBinding;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
import com.mamags.mamag.viewmodel.CRUDViewModel;
import com.mamags.mamag.viewmodel.MealTypeViewModel;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.helpers.ActionModeHelper;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by samer on 11/11/2017.
 */

public class MealTypeListFragment extends BaseFragment<MealtypeListfragmentBinding, MealTypeViewModel> implements MealTypeView, ActionMode.Callback,
        FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {


    FlexibleAdapter<IFlexible> adapter;

    private CRUDViewModel crudViewModel;
    int mActivatedPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new MealTypeViewModel(ctx.getApplication(), restAPI);
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
        loadData();

        binding.newMealType.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMealTypeActivity.class)));

    }


    void loadData() {

        viewModel.getMealTypeList(apiRequests.getMealTypes());
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
    public void loadMealTypeResults(MealTypeListResponse mealTypeListResponse) {

        List<IFlexible> myItems = new ArrayList<>();
        for (MealType mealType : mealTypeListResponse.getMealTypeList()) {
            DefaultListItem mealTypeFlexItem = new DefaultListItem(mealType,1);
            myItems.add(mealTypeFlexItem);
        }
        //Initialize the Adapter
        adapter = viewModel.displayFlexibleAdapter(myItems,binding.list, this);
        adapter.addListener(this);
        showLoadedStatus();
        displayUtils.displaySuccessMessage(binding.getRoot());

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
        return true; //Important!
    }

    private void setActivatedPosition(int position) {
        mActivatedPosition = position;
        //adapter.toggleSelection(position); //Important!
    }
}
