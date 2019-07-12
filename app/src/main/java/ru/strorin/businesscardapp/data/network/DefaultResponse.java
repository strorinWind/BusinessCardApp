package ru.strorin.businesscardapp.data.network;

import androidx.annotation.Nullable;

public class DefaultResponse<T> {

    private T results;

    @Nullable
    public T getData() {
        return results;
    }
}