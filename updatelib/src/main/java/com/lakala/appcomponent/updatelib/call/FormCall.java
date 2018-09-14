package com.lakala.appcomponent.updatelib.call;

import android.net.Uri;

import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.mode.Response;
import com.lakala.appcomponent.updatelib.util.HttpUtil;
import com.lakala.appcomponent.updatelib.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class FormCall extends BaseCall {

    private Map<String, String> params;

    private String content;

    public FormCall(String url, Method method, Map<String, String> head, Map<String, String> params) {
        super(url, method, head);
        this.params = params;

        if (heads == null) {
            heads = new HashMap<>();
        }

        heads.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        heads.put("Charset", "UTF-8");

        appendUrl();
    }

    private void appendUrl() {
        Uri.Builder builder = new Uri.Builder();

        for (String key : params.keySet()) {
            builder.appendQueryParameter(key, params.get(key));
        }

        content = builder.build().toString();
    }

    @Override
    public Response execute() {
        return HttpUtil.execute(url, Utils.getMethod(method), content, heads);
    }

    @Override
    public void execute(final BaseCallBack callBack) {
        HttpUtil.mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpUtil.execute(url, Utils.getMethod(method), content, heads, callBack);
            }
        });
    }
}
