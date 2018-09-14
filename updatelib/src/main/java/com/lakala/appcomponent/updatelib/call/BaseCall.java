package com.lakala.appcomponent.updatelib.call;

import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.mode.Response;

import java.util.Map;

public abstract class BaseCall {

    String url;

    Method method;

    Map<String, String> heads;

    public BaseCall(String url, Method method, Map<String, String> heads) {
        this.url = url;
        this.method = method;
        this.heads = heads;
    }

    public abstract Response execute();

    public abstract void execute(BaseCallBack callBack);
}
