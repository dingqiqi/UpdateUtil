package com.lakala.appcomponent.updatelib.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    /**
     * 流转化为String
     *
     * @param inputStream 文件流
     * @return String
     */
    public static String streamToString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        byte[] by = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        try {
            while ((len = inputStream.read(by)) != -1) {
                outputStream.write(by, 0, len);
            }

            return outputStream.toString("utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 流转化为文件
     *
     * @param inputStream 文件流
     * @param file        文件
     * @return boolean true 成功
     */
    public static boolean streamToFile(InputStream inputStream, File file, FileProgressBack callback) {
        if (inputStream == null) {
            return false;
        }

        byte[] by = new byte[1024];
        OutputStream outputStream = null;
        int len, sum = 0;
        try {
            outputStream = new FileOutputStream(file);

            while ((len = inputStream.read(by)) != -1) {
                outputStream.write(by, 0, len);
                sum += len;
                if (callback != null) {
                    callback.progress(sum);
                }
            }

            outputStream.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public interface FileProgressBack {
        public void progress(float progress);
    }
}
