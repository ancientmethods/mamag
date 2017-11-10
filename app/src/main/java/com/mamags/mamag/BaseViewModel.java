package com.mamags.mamag;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.IView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by samer on 01/10/2017.
 */

public class BaseViewModel<T extends IView> extends AndroidViewModel {




    protected CompositeDisposable compositeDisposable;
    public T Iview;

    public BaseViewModel(Application application) {
        super(application);
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



}
