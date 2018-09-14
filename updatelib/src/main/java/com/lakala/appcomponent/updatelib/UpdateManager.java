package com.lakala.appcomponent.updatelib;

import android.text.TextUtils;

import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.manager.HttpManager;
import com.lakala.appcomponent.updatelib.mode.Response;
import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.util.Type;

import org.json.JSONObject;

import java.util.Map;

public class UpdateManager {

    /**
     * 网络请求
     *
     * @param build    请求的参数
     * @param callBack 结果回调
     */
    public static void requestData(Build build, BaseCallBack callBack) {
        if (build == null) {
            return;
        }

        //get请求
        if (Method.GET == build.method) {
            HttpManager
                    .get()
                    .url(build.url)
                    .params(build.params)
                    .heads(build.heads)
                    .build()
                    .execute(callBack);
        } else {

            //json请求
            if (Type.JSON == build.type) {
                //传递数据是map  转成json格式的内容
                if (TextUtils.isEmpty(build.content)) {
                    build.content(new JSONObject(build.params).toString());
                }

                HttpManager
                        .requestString()
                        .url(build.url)
                        .method(Method.POST)
                        .content(build.content)
                        .heads(build.heads)
                        .build()
                        .execute(callBack);
            } else {
                //form请求
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

    /**
     * 网络请求 同步(放在线程中执行)
     *
     * @param build 请求的参数
     * @return 网络结果返回
     */
    public static Response requestData(Build build) {
        if (build == null) {
            return null;
        }

        //get请求
        if (Method.GET == build.method) {
            return HttpManager
                    .get()
                    .url(build.url)
                    .params(build.params)
                    .heads(build.heads)
                    .build()
                    .execute();
        } else {
            //gson请求
            if (Type.JSON == build.type) {

                //传递数据是map  转成json格式的内容
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
        //请求类型 json或者form
        Type type;
        //请求地址
        String url;
        //请求内容
        String content;
        //请求方法get post
        Method method;
        //请求参数
        Map<String, String> params;
        //请求头
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
