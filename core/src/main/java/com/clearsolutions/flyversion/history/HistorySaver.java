package com.clearsolutions.flyversion.history;

import com.clearsolutions.flyversion.script.Information;

public interface HistorySaver {

    HistoryInformation save(Information information);
}
