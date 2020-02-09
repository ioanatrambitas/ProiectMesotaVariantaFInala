package com.example.proiectmesotavariantafinala.ui.downloadElev;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DownloadModelElev extends ViewModel {

    private MutableLiveData<String> mText;

    public DownloadModelElev() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}