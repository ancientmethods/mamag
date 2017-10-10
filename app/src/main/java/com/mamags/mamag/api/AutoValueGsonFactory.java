package com.mamags.mamag.api;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 *
 *
 */

@GsonTypeAdapterFactory
public abstract class AutoValueGsonFactory implements TypeAdapterFactory {

    // Static factory method to access the package
    // private generated implementation
//    public static TypeAdapterFactory create() {
//       // GsonTypeAdapter
//       // return new AutoValueGson_AutoValueGsonFactory();
//    }

}
