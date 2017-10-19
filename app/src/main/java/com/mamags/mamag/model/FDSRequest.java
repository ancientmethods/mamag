package com.mamags.mamag.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samer on 18/10/2017.
 */

public class FDSRequest {

    /// <summary>
    /// Defines the device version sent from the device that initiated the request
    /// </summary>
    @SerializedName("DeviceVersion")
    private String DeviceVersion = "1.0";

    /// <summary>
    /// Defines the device type that sent the request
    /// </summary>
    @SerializedName("DeviceType")
    public int DeviceType = 0;


    /// <summary>
    /// Defines the action to be performed when the request is received
    /// </summary>
    @SerializedName("CrudOption")
    public int CrudOption =0;
}
