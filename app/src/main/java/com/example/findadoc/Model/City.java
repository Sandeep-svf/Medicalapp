package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class City {

        @SerializedName("municipal_id")
        @Expose
        private Integer municipalId;
        @SerializedName("municipal_name")
        @Expose
        private String municipal_name_en;

        public Integer getMunicipalId() {
        return municipalId;
        }

        public void setMunicipalId(Integer municipalId) {
        this.municipalId = municipalId;
        }

        public String getMunicipalName() {
        return municipal_name_en;
        }

        public void setMunicipalName(String municipalName) {
        this.municipal_name_en = municipalName;
        }

        }