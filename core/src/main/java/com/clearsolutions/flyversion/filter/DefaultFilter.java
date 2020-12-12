package com.clearsolutions.flyversion.filter;

import com.clearsolutions.flyversion.history.HistoryInformation;
import com.clearsolutions.flyversion.script.Information;
import com.clearsolutions.flyversion.script.ScriptType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.partitioningBy;

public class DefaultFilter implements Filter {
    @Override
    public <Script> List<Information<Script>> filter(List<Information<Script>> scripts,
                                                     List<HistoryInformation> history, Double envVersion) {
        Map<Boolean, List<Information<Script>>> categorisedScripts =
            scripts.stream()
                   .collect(partitioningBy(script -> script.getType().equals(ScriptType.DO)));

        Map<Double, Information<Script>> doScripts =
            categorisedScripts.get(true).stream().collect(Collectors.toMap(Information::getVersion, identity()));

        Map<Double, Information<Script>> undoScripts =
            categorisedScripts.get(false).stream().collect(Collectors.toMap(Information::getVersion, identity()));

        history.sort(Comparator.comparing(HistoryInformation::getVersion));

        for (HistoryInformation historyInformation : history) {
            doScripts.remove(historyInformation.getVersion());
            if (historyInformation.getEnvironmentVersion() <= envVersion) {
                undoScripts.remove(historyInformation.getVersion());
            }
        }
        ArrayList<Information<Script>> result = new ArrayList<>(doScripts.values());
        result.addAll(undoScripts.values());
        return result;
    }
}
