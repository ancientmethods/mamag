package com.mamags.mamag.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.adapters.DefaultListItem;
import com.mamags.mamag.api.ResponseCode;
import com.mamags.mamag.databinding.FragmentMenuListBinding;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Requests.MenuRequest;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Responses.MenuListResponse;
import com.mamags.mamag.interfaces.MenuView;
import com.mamags.mamag.viewmodel.CRUDViewModel;
import com.mamags.mamag.viewmodel.MenuViewModel;


import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;


/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     MenusListFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link MenusListFragment.Listener}.</p>
 */

public class MenusListFragment extends BaseFragment<FragmentMenuListBinding, MenuViewModel> implements MenuView, ActionMode.Callback,
        FlexibleAdapter.OnItemClickListener, FlexibleAdapter.OnItemLongClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";


    FlexibleAdapter<IFlexible> adapter;
    private CRUDViewModel crudViewModel;

    int mActivatedPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new MenuViewModel(ctx.getApplication(), restAPI);
        viewModel.attach(this);


        crudViewModel = new CRUDViewModel(ctx.getApplication(), restAPI);
        crudViewModel.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindView(inflater, R.layout.fragment_menu_list, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        setupRefreshLayout();
        binding.swiperefresh.setRefreshing(true);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.newMenu.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMenuActivity.class)));
        binding.newMeal.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMealActivity.class)));

    }


    @Override
    public void loadMenuResults(MenuListResponse menuListResponse) {
        //recyclerView.setAdapter(new MenuAdapter(14));
        binding.swiperefresh.setRefreshing(false);
        binding.setRecyclerViewVisibility(true);

        List<IFlexible> myItems = new ArrayList<>();
        for (Menu menu : menuListResponse.getMenuList()) {
            DefaultListItem mealTypeFlexItem = new DefaultListItem(menu,0);
            myItems.add(mealTypeFlexItem);
        }

        //Initialize the Adapter
        adapter = viewModel.displayFlexibleAdapter(myItems, binding.list, this);
        adapter.addListener(this);
        showLoadedStatus();
        displayUtils.displaySuccessMessage(binding.getRoot());

    }

    void showLoadedStatus() {
        binding.swiperefresh.setRefreshing(false);
        binding.setRecyclerViewVisibility(true);
    }

    @Override
    public void showNoDataView() {
        binding.setRecyclerViewVisibility(false);
        binding.swiperefresh.setRefreshing(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below

        binding.setRecyclerViewVisibility(true);
        viewModel.getMenuList(apiRequests.getMenus());
    }


    private void setupRefreshLayout() {

        binding.swiperefresh.setOnRefreshListener(() -> {
            loadData();
        });
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
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

    boolean deleteDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle(getString(R.string.warning));
        alertDialog.setMessage(getString(R.string.delete_menu_warning));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), (dialog, which) -> deleteItems());
        alertDialog.show();
        return true;
    }

    void deleteItems() {
        try {
            List<Integer> selectedItems = adapter.getSelectedPositions();
            DefaultListItem flexItem = (DefaultListItem) adapter.getItem(selectedItems.get(0));
            MenuRequest deleteMenuRequest = apiRequests.deleteMenu(flexItem.getMenu().getId());

            crudViewModel.createMenu(deleteMenuRequest);
            viewModel.getmActionModeHelper().destroyActionModeIfCan();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG, "error");
        }

    }

    @Override
    public void onItemLongClick(int position) {

        if (position != mActivatedPosition) setActivatedPosition(position);

        //make sure only the currently selected item will get selected again as we are in single selection mode
        if (adapter.getSelectedPositions().size() == 0 || adapter.getSelectedPositions().contains(position)) {
            viewModel.getmActionModeHelper().onLongClick(ctx, position);
        }


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
    public boolean onItemClick(int position) {

        if (viewModel.getmActionModeHelper().getActionMode() != null) {
            return false;
        }

        if (position != mActivatedPosition) setActivatedPosition(position);


        //go to edit screen activity
        try {
            Intent intent = new Intent(ctx, CreateMenuActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("menu", (DefaultListItem) adapter.getItem(mActivatedPosition));

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


}
