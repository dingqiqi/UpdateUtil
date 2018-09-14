package com.lakala.appcomponent.updatelib.build;

import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.call.BaseCall;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseBuild<T extends BaseBuild> {

    protected Method method;

    protected String url;

    protected Map<String, String> heads;

    public T method(Method method) {
        this.method = method;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T heads(Map<String, String> heads) {
        this.heads = heads;
        return (T) this;
    }

    public T addHead(String key, String value) {
        if (this.heads == null) {
            this.heads = new HashMap<>();
        }
        this.heads.put(key, value);

        return (T) this;
    }

    public abstract BaseCall build();

}
