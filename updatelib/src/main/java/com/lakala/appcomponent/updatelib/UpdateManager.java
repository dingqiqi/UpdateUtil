package com.lakala.appcomponent.updatelib;

import android.text.TextUtils;

import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.manager.HttpManager;
import com.lakala.appcomponent.updatelib.mode.Response;
import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.util.Type;
import com.lakala.appcomponent.updatelib.util.Utils;

import org.json.JSONObject;

import java.util.Map;

public class UpdateManager {

    public static void requestData(Build build, BaseCallBack callBack) {
        if (build == null) {
            return;
        }

        if ("GET".equals(Utils.getMethod(build.method))) {
            HttpManager
                    .get()
                    .url(build.url)
                    .params(build.params)
                    .heads(build.heads)
                    .build()
                    .execute(callBack);
        } else {

            //预防传递数据是map
            if (TextUtils.isEmpty(build.content)) {
                build.content(new JSONObject(build.params).toString());
            }

            if (Type.JSON == build.type) {
                HttpManager
                        .requestString()
                        .url(build.url)
                        .method(Method.POST)
                        .content(build.content)
                        .heads(build.heads)
                        .build()
                        .execute(callBack);
            } else {
                HttpManager
                        .requestForm()
                        .url(build.url)
                        .method(Method.POST)
                        .params(build.params)
                        .heads(build.heads)
                        .build()
                        .execute(callBack);
            }
        }
    }

    public static Response requestData(Build build) {
        if (build == null) {
            return null;
        }

        if ("GET".equals(Utils.getMethod(build.method))) {
            return HttpManager
                    .get()
                    .url(build.url)
                    .params(build.params)
                    .heads(build.heads)
                    .build()
                    .execute();
        } else {

            if (Type.JSON == build.type) {

                //预防传递数据是map
                if (TextUtils.isEmpty(build.content)) {
                    build.content(new JSONObject(build.params).toString());
                }

                return HttpManager
                        .requestString()
                        .method(Method.POST)
                        .url(build.url)
                        .content(build.content)
                        .heads(build.heads)
                        .build()
                        .execute();
            } else {
                return HttpManager
                        .requestForm()
                        .method(Method.POST)
                        .url(build.url)
                        .params(build.params)
                        .heads(build.heads)
                        .build()
                        .execute();
            }
        }
    }

    public static class Build {
        Type type;
        String url;
        String content;
        Method method;
        Map<String, String> params;
        Map<String, String> heads;

        public Build content(String content) {
            this.content = content;
            return this;
        }

        public Build type(Type type) {
            this.type = type;
            return this;
        }

        public Build url(String url) {
            this.url = url;
            return this;
        }

        public Build method(Method method) {
            this.method = method;
            return this;
        }

        public Build params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Build heads(Map<String, String> heads) {
            this.heads = heads;
            return this;
        }

    }

}
