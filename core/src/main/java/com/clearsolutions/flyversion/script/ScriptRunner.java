package com.clearsolutions.flyversion.script;

public interface ScriptRunner<Script> {

    Result run(Information<Script> script);
}
