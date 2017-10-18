package com.mamags.mamag.model;

/**
 * Created by Samer on 18/10/2017.
 */

public class FDSRequest {

    /// <summary>
    /// Defines the device version sent from the device that initiated the request
    /// </summary>
    private String DeviceVersion = "1.0";

    /// <summary>
    /// Defines the device type that sent the request
    /// </summary>

    public String DeviceType = "ANDROID";


    /// <summary>
    /// Defines the action to be performed when the request is received
    /// </summary>
    public String CrudOption = "Add";
}
