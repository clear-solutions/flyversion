package com.clearsolutions.flyversion.script;

public interface ScriptRunner<T> {

    Result<T> run(Information<T> script);
}
