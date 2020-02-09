package com.example.proiectmesotavariantafinala.ui.catalogElev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CatalogModelElev extends ViewModel {

    private MutableLiveData<String> mText;

    public CatalogModelElev() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}