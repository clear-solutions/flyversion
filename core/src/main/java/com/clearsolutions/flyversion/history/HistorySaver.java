package com.clearsolutions.flyversion.history;

import com.clearsolutions.flyversion.script.Information;

public interface HistorySaver<T> {

    HistoryInformation save(Information<T> information);
}
