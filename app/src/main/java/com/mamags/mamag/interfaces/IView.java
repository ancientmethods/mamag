package com.mamags.mamag.interfaces;

import com.mamags.mamag.model.Responses.FDSresponse;

/**
 * View part of MVVM, the activities and fragment implement this
 * and is is used for interaction between ViewModel and Activities/Fragments
 */

public interface IView {

    void error(Throwable e);

    void error(String message);
    void showNoDataView();
    void processStandardResponse(FDSresponse fdSresponse, boolean shouldCloseScreen);

}
