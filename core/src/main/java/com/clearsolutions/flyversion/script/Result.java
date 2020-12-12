package com.clearsolutions.flyversion.script;

import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class Result<T> {

    private final Optional<Information<T>> scriptMetaInformation;
    private final Optional<Throwable> error;

    public Result(Information<T> scriptMetaInformation, Throwable error) {
        this.scriptMetaInformation = ofNullable(scriptMetaInformation);
        this.error = ofNullable(error);
    }

    public Result<T> successHandler(Consumer<Information<T>> supplier) {
        scriptMetaInformation.ifPresent(supplier::accept);
        return this;
    }

    public boolean isSuccess() {
        return scriptMetaInformation.isPresent();
    }

    public boolean isFailed() {
        return scriptMetaInformation.isPresent();
    }

    public Result<T> failureHandler(Consumer<Throwable> supplier) {
        error.ifPresent(supplier::accept);
        return this;
    }
}
