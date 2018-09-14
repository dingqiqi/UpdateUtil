package com.lakala.appcomponent.updatelib.build;

import com.lakala.appcomponent.updatelib.call.BaseCall;
import com.lakala.appcomponent.updatelib.call.GetCall;

import java.util.HashMap;
import java.util.Map;

public class GetBuild extends BaseBuild<GetBuild> {

    private Map<String, String> params;

    public GetBuild params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public GetBuild addParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.put(key, value);
        return this;
    }

    @Override
    public BaseCall build() {
        return new GetCall(url, method, heads, params);
    }
}
