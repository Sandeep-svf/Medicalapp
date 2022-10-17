package com.example.findadoc.sam.dev.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModuleResult {
    @SerializedName("service_id")
    @Expose
    public Integer serviceId;
    @SerializedName("service_name")
    @Expose
    public String serviceName;
    @SerializedName("block")
    @Expose
    public Integer block;


    public Integer getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Integer getBlock() {
        return block;
    }
}