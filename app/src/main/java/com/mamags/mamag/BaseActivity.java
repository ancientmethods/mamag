package com.mamags.mamag;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mamags.mamag.Utils.DisplayUtils;
import com.mamags.mamag.api.API_Requests;
import com.mamags.mamag.api.ResponseCode;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.IView;
import com.mamags.mamag.model.Responses.FDSresponse;

import javax.inject.Inject;

/**
 * Created by samer on 30/09/2017.
 * Base activity class where all activities should extend from
 * the generic classes T and B will make sure that the inheriting classes will
 * instantiate classes that extend ViewDatabinding and AndroidViewModel
 */

public abstract class BaseActivity<B extends ViewDataBinding, T extends BaseViewModel> extends AppCompatActivity implements IView {

    protected T viewModel;
    public B binding;

    @Inject
    public RestAPI restAPI;

    @Inject
    public DisplayUtils displayUtils;


    @Inject
    public API_Requests apiRequests;
    /**
     * ViewModel must be initialized before bindView() is called
     *
     * @param layout layout for the activity
     */
    protected final void bindView(int layout) {
        /*if (viewModel == null) {
            throw new IllegalStateException("viewModel must not be null and should be injected via activityComponent().inject(this)");
        }*/
        binding = DataBindingUtil.setContentView(this, layout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewModel != null) {
            viewModel.clearSubscriptions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.detach();
        }

    }

    @Override
    public void error(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void error(String message) {
        displayUtils.displayErrorMessage(binding.getRoot(),message);
    }

    @Override
    public void showNoDataView() {

    }

    @Override
    public void processStandardResponse(FDSresponse fdSresponse, boolean shouldClose) {


        if (fdSresponse.getResponseCode() == ResponseCode.SUCCESS) {
            displayUtils.displaySuccessMessage(binding.getRoot());
        } else {
            displayUtils.displayErrorMessage(binding.getRoot());
        }
        if (shouldClose) {
            finish();
        }

    }
}
