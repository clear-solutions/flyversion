package com.clearsolutions.flyversion.history;

import com.clearsolutions.flyversion.script.Information;

public interface HistorySaver<Script> {

    HistoryInformation save(Information<Script> information);
}
