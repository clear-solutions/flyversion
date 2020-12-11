package com.clearsolutions.flyversion.script;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Information<Script> {

    private String version;
    private Script doScript;
    private Script undoScript;
    private String name;
    private String type;
}
