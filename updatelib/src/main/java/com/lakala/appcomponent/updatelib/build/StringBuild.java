package com.lakala.appcomponent.updatelib.build;

import com.lakala.appcomponent.updatelib.call.BaseCall;
import com.lakala.appcomponent.updatelib.call.StringCall;

public class StringBuild extends BaseBuild<StringBuild> {

    private String content;

    public StringBuild content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public BaseCall build() {
        return new StringCall(url, method, heads, content);
    }
}
