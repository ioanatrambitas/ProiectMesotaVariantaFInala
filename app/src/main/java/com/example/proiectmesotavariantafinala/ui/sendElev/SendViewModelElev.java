package com.example.proiectmesotavariantafinala.ui.sendElev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModelElev extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModelElev() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}