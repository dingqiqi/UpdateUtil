package com.lakala.appcomponent.updatelib.callback;

import com.lakala.appcomponent.updatelib.util.FileUtil;

import java.io.File;
import java.io.InputStream;

public abstract class FileCallBack extends BaseCallBack<File> {

    private File file;

    public FileCallBack(File file) {
        this.file = file;
    }

    @Override
    public File onParse(InputStream stream, final int total) {

        if (FileUtil.streamToFile(stream, file, new FileUtil.FileProgressBack() {
            @Override
            public void progress(float progress) {
                onProgress(progress, total);
            }
        })) {
            return file;
        }

        return null;
    }

    public abstract void onProgress(float progress, int total);
}
