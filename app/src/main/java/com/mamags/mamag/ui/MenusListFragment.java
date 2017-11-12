package com.mamags.mamag.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mamags.mamag.BaseFragment;
import com.mamags.mamag.MyApplication;
import com.mamags.mamag.R;
import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.databinding.FragmentMenuListBinding;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Responses.MenuListResponse;
import com.mamags.mamag.interfaces.MenuView;
import com.mamags.mamag.viewmodel.MenuViewModel;


import java.util.List;


/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     MenusListFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link MenusListFragment.Listener}.</p>
 */

public class MenusListFragment extends BaseFragment<FragmentMenuListBinding, MenuViewModel> implements MenuView {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";

    Listener listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof Listener){
            listener=(Listener) context;
        }
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getComponent().inject(this);

        viewModel = new MenuViewModel(ctx.getApplication(), restAPI);
        viewModel.attach(this);


      /*  Gson gson = new GsonBuilder().create();
        String json = gson.toJson(menu);
        try {
            JSONObject jsonObject1 = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
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
        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below
        FDSRequest request = new FDSRequest();
        request.CrudOption = RequestAction.list.getValue();

        binding.setRecyclerViewVisibility(true);
        viewModel.getMenuList(request);

        binding.newMenu.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMenuActivity.class)));
        binding.newMeal.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMenuActivity.class)));
        binding.viewMealTypes.setOnClickListener(v -> listener.onMenuClicked(1));
        binding.newMealType.setOnClickListener(v -> startActivity(new Intent(ctx, CreateMealTypeActivity.class)));

    }


    @Override
    public void loadMenuResults(MenuListResponse menuListResponse) {
        //recyclerView.setAdapter(new MenuAdapter(14));
        binding.swiperefresh.setRefreshing(false);
        binding.setRecyclerViewVisibility(true);
        binding.list.setAdapter(new MenuAdapter(menuListResponse.getMenuList()));
        DisplayUtils.displaySnackbar(binding.getRoot(), String.valueOf(menuListResponse.getResponseCode()), Snackbar.LENGTH_LONG, ctx);

    }


    @Override
    public void showNoDataView() {
        binding.setRecyclerViewVisibility(false);
        binding.swiperefresh.setRefreshing(false);
    }

    public interface Listener {
        void onMenuClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_menu_list_item, parent, false));
            text = itemView.findViewById(R.id.text);

            text.setOnClickListener(v -> {
            });
        }

    }

    private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Menu> menuList;

        MenuAdapter(List<Menu> menuList) {
            this.menuList = menuList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(menuList.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

    }

    private void setupRefreshLayout() {

        binding.swiperefresh.setOnRefreshListener(() -> {
            FDSRequest request = new FDSRequest();
            request.CrudOption = RequestAction.list.getValue();
            viewModel.getMenuList(request);
        });
    }


}
