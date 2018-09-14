package com.lakala.appcomponent.updatelib.callback;

import com.lakala.appcomponent.updatelib.mode.Response;

import java.io.InputStream;

public abstract class BaseCallBack<T> {

    public abstract void onSuccess(Response<T> response);

    public abstract T onParse(InputStream stream,int total);

    public abstract void onFailed(Exception e);

}
