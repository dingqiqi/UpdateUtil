package com.lakala.appcomponent.updatelib.call;

import android.net.Uri;

import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.mode.Response;
import com.lakala.appcomponent.updatelib.util.HttpUtil;

import java.util.Map;

public class GetCall extends BaseCall {

    private Map<String, String> params;

    public GetCall(String url, Method method, Map<String, String> heads, Map<String, String> param) {
        super(url, method, heads);
        this.params = param;

        //参数不为空
        if (params != null) {
            appendUrl();
        }
    }

    /**
     * url后面拼接参数
     */
    private void appendUrl() {
        Uri.Builder builder = Uri.parse(url).buildUpon();

        for (String key : params.keySet()) {
            builder.appendQueryParameter(key, params.get(key));
        }

        url = builder.build().toString();
    }

    @Override
    public Response execute() {
        return HttpUtil.execute(url, "GET", null, heads);
    }

    @Override
    public void execute(final BaseCallBack callBack) {
        HttpUtil.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpUtil.execute(url, "GET", null, heads, callBack);
            }
        });

    }
}
