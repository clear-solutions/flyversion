package com.clearsolutions.flyversion.history;

import java.util.List;

public interface History {

    List<String> getAppliedVersions();

    boolean apply(String version);
}
