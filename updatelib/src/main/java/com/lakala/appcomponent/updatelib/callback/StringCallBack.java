package com.lakala.appcomponent.updatelib.callback;

import com.lakala.appcomponent.updatelib.util.FileUtil;

import java.io.InputStream;

public abstract class StringCallBack extends BaseCallBack<String> {

    @Override
    public String onParse(InputStream stream, int total) {
        return FileUtil.streamToString(stream);
    }
}
