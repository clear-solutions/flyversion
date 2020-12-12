package com.clearsolutions.flyversion.filter;

import com.clearsolutions.flyversion.history.HistoryInformation;
import com.clearsolutions.flyversion.script.Information;

import java.util.List;

public interface Filter {

    <T> List<Information<T>> filter(List<Information<T>> scripts,
                                    List<HistoryInformation> history,
                                    Double envVersion);
}
