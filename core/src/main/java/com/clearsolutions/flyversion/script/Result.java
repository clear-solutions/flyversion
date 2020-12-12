package com.clearsolutions.flyversion.script;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Result {

    private Optional<Information> scriptMetaInformation;
    private Optional<Throwable> error;

    public Result successHandler(Consumer<Information> supplier) {
        scriptMetaInformation.ifPresent(supplier::accept);
        return this;
    }

    public boolean isSuccess() {
        return scriptMetaInformation.isPresent();
    }

    public boolean isFailed() {
        return scriptMetaInformation.isPresent();
    }

    public Result failureHandler(Consumer<Throwable> supplier) {
        error.ifPresent(supplier::accept);
        return this;
    }
}
