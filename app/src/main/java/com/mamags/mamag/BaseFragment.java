package com.mamags.mamag;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.api.ResponseCode;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.IView;
import com.mamags.mamag.model.Responses.FDSresponse;

import javax.inject.Inject;

/**
 * Created by samer on 01/10/2017.
 */

public abstract class BaseFragment<B extends ViewDataBinding, T extends BaseViewModel> extends Fragment implements IView {

    protected T viewModel;
    protected B binding;
    public AppCompatActivity ctx;

    public String TAG = "Mamag";
    @Inject public RestAPI restAPI;
    @Inject public DisplayUtils displayUtils;


    /**
     * ViewModel must be initialized before bindView() is called
     * @param layout layout for the activity
     */
    protected final void bindView(LayoutInflater inflater, int layout, ViewGroup container) {
        if (viewModel == null) {
            throw new IllegalStateException("viewModel must not be null and should be injected via activityComponent().inject(this)");
        }
        binding = DataBindingUtil.inflate(
                inflater, layout, container, false);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = (AppCompatActivity) context;
    }


    @Override
    public void onStop() {
        super.onStop();
        viewModel.clearSubscriptions();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        viewModel.detach();
    }

    @Override public void error(Throwable e) {
        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override public void error(String message) {
        displayUtils.displayErrorMessage(binding.getRoot(),message);
    }

    @Override
    public void processStandardResponse(FDSresponse fdSresponse, boolean shouldClose) {


        if (fdSresponse.getResponseCode() == ResponseCode.SUCCESS) {
            displayUtils.displayErrorMessage(binding.getRoot());
        } else {
            displayUtils.displayErrorMessage(binding.getRoot());

        }


    }
}
