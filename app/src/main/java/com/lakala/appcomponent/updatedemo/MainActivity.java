package com.lakala.appcomponent.updatedemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lakala.appcomponent.updatelib.util.Method;
import com.lakala.appcomponent.updatelib.util.Type;
import com.lakala.appcomponent.updatelib.UpdateManager;
import com.lakala.appcomponent.updatelib.callback.FileCallBack;
import com.lakala.appcomponent.updatelib.callback.StringCallBack;
import com.lakala.appcomponent.updatelib.mode.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Map<String, String> params = new HashMap<>();
        //平台
        params.put("platform", "ANDROID");
        //apk版本
        params.put("appVer", "1.0.0");
        //zip版本
        params.put("zipVer", "1.0.0");
        //定位id
        params.put("installationId", "");

        //省份code
        params.put("provinceCode", "1001");

        //token
        params.put("token", "");

        queryData("http://10.7.112.166:8081/cmmp/version/checkAppVersion", params);

    }

    public void queryData(String url, Map<String, String> params) {
        UpdateManager.requestData(new UpdateManager.Build()
                .url(url)
                .method(Method.POST)
                .type(Type.JSON)
                .heads(null)
                .params(params), new StringCallBack() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.i("aaa", "onSuccess:" + response);

                if (200 == response.getCode()) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.getBody());

                        if ("000000".equals(jsonObject.getString("retCode"))) {

                            jsonObject = new JSONObject(jsonObject.getString("retData"));

                            downloadFile(jsonObject.getString("zipUrl"));

                        } else {
                            Toast.makeText(MainActivity.this, jsonObject.getString("retMsg"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(MainActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("aaa", "error:" + e);
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downloadFile(String url) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.zip");

        UpdateManager.requestData(new UpdateManager.Build().url(url), new FileCallBack(file) {
            @Override
            public void onProgress(float progress, int total) {
                Log.i("aaa", "progress:" + progress / (total * 1.0f));
            }

            @Override
            public void onSuccess(Response<File> response) {
                Log.i("aaa", "onSuccess:" + response);
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
