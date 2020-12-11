package com.clearsolutions.flyversion.history;

import java.util.List;

public interface HistoryProvider {

    List<HistoryInformation> getHistory();
}
