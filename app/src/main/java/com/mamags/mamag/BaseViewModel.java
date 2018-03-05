package com.mamags.mamag;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.IView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import eu.davidea.flexibleadapter.helpers.ActionModeHelper;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by samer on 01/10/2017.
 */

public class BaseViewModel<T extends IView> extends AndroidViewModel {




    private ActionModeHelper mActionModeHelper;

    Application application;

    protected CompositeDisposable compositeDisposable;
    public T Iview;

    public BaseViewModel(Application application) {
        super(application);
        this.application = application;
        compositeDisposable = new CompositeDisposable();

    }


    public void clearSubscriptions() {
        compositeDisposable.dispose();
    }

    /**
     * this will attach the view to the view model, will make sure that view implements
     * the IView class or a subclass of it
     * @param view
     */
    public void attach(T view){
        this.Iview =view;
    }

    public void detach() {
        Iview = null;
    }


   public FlexibleAdapter<IFlexible> displayFlexibleAdapter(List<IFlexible> myItems, RecyclerView recyclerView, ActionMode.Callback callback){
        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<>(myItems);

        adapter.setMode(SelectableAdapter.Mode.SINGLE);
        initializeActionModeHelper(SelectableAdapter.Mode.SINGLE,adapter,callback);
        recyclerView.setLayoutManager(new SmoothScrollLinearLayoutManager(application));

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Divider item decorator with DrawOver enabled
        recyclerView.addItemDecoration(new FlexibleItemDecoration(application)
                .withDivider(R.drawable.divider, R.layout.recycler_simple_item)
                .withDrawOver(true));
        return adapter;
    }

    private void initializeActionModeHelper(@SelectableAdapter.Mode int mode, FlexibleAdapter adapter, ActionMode.Callback callback) {
        //this = ActionMode.Callback instance
        mActionModeHelper = new ActionModeHelper(adapter, R.menu.generic_edit, callback) {
            // Override to customize the title
            @Override
            public void updateContextTitle(int count) {
                // You can use the internal mActionMode instance
                if (mActionMode != null) {
                    mActionMode.setTitle(application.getString(R.string.action_selected_one) + " " + count);

                }
            }
        }.withDefaultMode(mode);
    }

    public ActionModeHelper getmActionModeHelper() {
        return mActionModeHelper;
    }

    public void setmActionModeHelper(ActionModeHelper mActionModeHelper) {
        this.mActionModeHelper = mActionModeHelper;
    }




}
