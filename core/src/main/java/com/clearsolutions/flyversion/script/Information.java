package com.clearsolutions.flyversion.script;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Information<Script> {

    private Double version;
    private Script script;
    private String name;
    private ScriptType type;
}
