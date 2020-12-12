package com.clearsolutions.flyversion.filter;

import com.clearsolutions.flyversion.history.HistoryInformation;
import com.clearsolutions.flyversion.script.Information;
import com.clearsolutions.flyversion.script.ScriptType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.partitioningBy;

public class DefaultFilter implements Filter {
    @Override
    public <T> List<Information<T>> filter(List<Information<T>> scripts,
                                           List<HistoryInformation> history, Double envVersion) {
        var categorisedScripts = scripts.stream()
                                        .collect(partitioningBy(script -> script.getType().equals(ScriptType.DO)));

        var doScripts =
            categorisedScripts.get(true).stream().collect(Collectors.toMap(Information::getVersion, identity()));

        var undoScripts =
            categorisedScripts.get(false).stream().collect(Collectors.toMap(Information::getVersion, identity()));

        history.sort(Comparator.comparing(HistoryInformation::getVersion));

        for (var historyInformation : history) {
            doScripts.remove(historyInformation.getVersion());
            if (historyInformation.getEnvironmentVersion() <= envVersion) {
                undoScripts.remove(historyInformation.getVersion());
            }
        }
        var result = new ArrayList<>(doScripts.values());
        result.addAll(undoScripts.values());
        return result;
    }
}
