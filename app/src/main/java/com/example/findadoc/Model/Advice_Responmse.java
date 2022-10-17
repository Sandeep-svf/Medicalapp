package com.example.findadoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advice_Responmse {
    @SerializedName(value = "descriptionBoardEn" , alternate = {"description_board_en","description_board_ar","description_board_fr"})
    @Expose

    private String descriptionBoardEn;

    public String getDescriptionBoardEn() {
        return descriptionBoardEn;
    }

    public void setDescriptionBoardEn(String descriptionBoardEn) {
        this.descriptionBoardEn = descriptionBoardEn;
    }

}
