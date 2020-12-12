package com.clearsolutions.flyversion.script;

import java.util.List;

public interface ScriptProvider<Script> {
    List<Information<Script>> getScripts();
}
