package com.lakala.appcomponent.updatelib.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lakala.appcomponent.updatelib.callback.BaseCallBack;
import com.lakala.appcomponent.updatelib.mode.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpUtil {

    //执行线程池
    public static Executor mExecutor = Executors.newFixedThreadPool(1);

    /**
     * 网络请求执行
     *
     * @param url      请求地址
     * @param method   请求方法
     * @param params   请求参数
     * @param heads    请求头
     * @param callBack 回调
     */
    public static <T> void execute(String url, String method, String params,
                                   Map<String, String> heads, BaseCallBack<T> callBack) {

        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            URL request = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) request.openConnection();

            urlConnection.setRequestMethod(method);
            urlConnection.setConnectTimeout(30000); //30秒连接超时
            urlConnection.setReadTimeout(30000);    //30秒读取超时

            if (!"GET".equals(method)) {
                //不允许缓存
                urlConnection.setUseCaches(false);
                //允许输出
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Length", String.valueOf(params.getBytes().length));
            }

            if (heads != null) {
                for (String key : heads.keySet()) {
                    urlConnection.setRequestProperty(key, heads.get(key));
                }
            }

            urlConnection.connect();

            if (!"GET".equals(method)) {
                outputStream = urlConnection.getOutputStream();

                outputStream.write(params.getBytes("utf-8"));
                outputStream.flush();
            }

            int code = urlConnection.getResponseCode();
            int length = urlConnection.getContentLength();

            Response<T> response = new Response<T>();
            response.setCode(code);

            if (code == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();

                response.setBody(callBack.onParse(inputStream, length));
            } else {
                inputStream = urlConnection.getErrorStream();
                response.setMessage(FileUtil.streamToString(inputStream));
            }

            onSuccess(callBack, response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            onFailed(callBack, e);
        } catch (IOException e) {
            e.printStackTrace();
            onFailed(callBack, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Response<String> execute(String url, String method, String params,
                                           Map<String, String> heads) {
        Response<String> response = new Response<>();

        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            URL request = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) request.openConnection();

            urlConnection.setRequestMethod(method);
            urlConnection.setConnectTimeout(30000); //30秒连接超时
            urlConnection.setReadTimeout(30000);    //30秒读取超时

            if (!"GET".equals(method)) {
                //不允许缓存
                urlConnection.setUseCaches(false);
                //允许输出
                urlConnection.setDoOutput(true);
            }

            for (String key : heads.keySet()) {
                urlConnection.setRequestProperty(key, heads.get(key));
            }

            urlConnection.setRequestProperty("Content-Length", String.valueOf(params.getBytes().length));

            urlConnection.connect();

            if (!"GET".equals(method)) {
                outputStream = urlConnection.getOutputStream();

                outputStream.write(params.getBytes("utf-8"));
                outputStream.flush();
            }

            int code = urlConnection.getResponseCode();

            response.setCode(code);

            if (code == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();

                response.setBody(FileUtil.streamToString(inputStream));
            } else {
                inputStream = urlConnection.getErrorStream();
                response.setMessage(FileUtil.streamToString(inputStream));
            }

            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static void onFailed(final BaseCallBack callBack, final Exception e) {
        if (callBack != null) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailed(e);
                }
            });
        }

    }

    private static Handler mHandler;

    private static <T> void onSuccess(final BaseCallBack callBack, final Response<T> response) {
        if (callBack != null) {

            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onSuccess(response);
                }
            });
        }
    }

}
