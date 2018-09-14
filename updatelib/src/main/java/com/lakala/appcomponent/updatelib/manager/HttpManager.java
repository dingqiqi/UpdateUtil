package com.lakala.appcomponent.updatelib.manager;

import com.lakala.appcomponent.updatelib.build.FormBuild;
import com.lakala.appcomponent.updatelib.build.GetBuild;
import com.lakala.appcomponent.updatelib.build.StringBuild;

public class HttpManager {

    public static GetBuild get() {
        return new GetBuild();
    }

    public static StringBuild requestString() {
        return new StringBuild();
    }

    public static FormBuild requestForm() {
        return new FormBuild();
    }

}
