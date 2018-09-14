package com.lakala.appcomponent.updatelib.build;

import com.lakala.appcomponent.updatelib.call.BaseCall;
import com.lakala.appcomponent.updatelib.call.FormCall;

import java.util.HashMap;
import java.util.Map;

public class FormBuild extends BaseBuild<FormBuild> {

    private Map<String, String> params;

    public FormBuild params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public FormBuild addParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);

        return this;
    }

    @Override
    public BaseCall build() {
        return new FormCall(url, method, heads, params);
    }
}
