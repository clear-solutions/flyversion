package com.clearsolutions.flyversion.script;

import java.util.Optional;
import java.util.function.Consumer;

public class Result {

    private Optional<Information> scriptMetaInformation;
    private Optional<Throwable> error;

    public Result isSuccess(Consumer<Information> supplier) {
        scriptMetaInformation.ifPresent(supplier::accept);
        return this;
    }

    public Result isFailure(Consumer<Throwable> supplier) {
        error.ifPresent(supplier::accept);
        return this;
    }
}
