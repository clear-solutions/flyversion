package com.clearsolutions.flyversion.history;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HistoryInformation {

    private String version;
}
