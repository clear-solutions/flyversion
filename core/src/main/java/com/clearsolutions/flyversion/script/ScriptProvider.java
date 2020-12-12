package com.clearsolutions.flyversion.script;

import java.util.List;

public interface ScriptProvider<T> {
    List<Information<T>> getScripts();
}
