package com.example.proiectmesotavariantafinala.ui.acasaElev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AcasaModelElev extends ViewModel {

    private MutableLiveData<String> mText;

    public AcasaModelElev() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}