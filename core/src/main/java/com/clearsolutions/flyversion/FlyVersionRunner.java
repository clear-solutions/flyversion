package com.clearsolutions.flyversion;

import com.clearsolutions.flyversion.history.HistoryInformation;
import com.clearsolutions.flyversion.history.HistoryProvider;
import com.clearsolutions.flyversion.history.HistorySaver;
import com.clearsolutions.flyversion.listener.ErrorListener;
import com.clearsolutions.flyversion.script.Information;
import com.clearsolutions.flyversion.script.ScriptProvider;
import com.clearsolutions.flyversion.script.ScriptRunner;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

class FlyVersionRunner<Script> {

    private final ErrorListener errorListener;
    private final ScriptProvider<Script> scriptProvider;
    private final ScriptRunner<Script> scriptRunner;
    private final HistoryProvider historyProvider;
    private final HistorySaver historySaver;
    private final Function<Map.Entry<List<Information<Script>>, List<HistoryInformation>>, List<Information<Script>>>
        filter;
    private final String version;

    public FlyVersionRunner(ErrorListener errorListener,
                            ScriptProvider<Script> scriptProvider,
                            ScriptRunner<Script> scriptRunner,
                            HistoryProvider historyProvider,
                            HistorySaver historySaver,
                            Function<Map.Entry<List<Information<Script>>, List<HistoryInformation>>, List<Information<Script>>> filter,
                            String version) {
        this.errorListener = errorListener;
        this.scriptProvider = scriptProvider;
        this.scriptRunner = scriptRunner;
        this.historyProvider = historyProvider;
        this.historySaver = historySaver;
        this.filter = filter;
        this.version = version;
    }

    public void run() {
        List<Information<Script>> filteredScripts = filter.apply(new AbstractMap.SimpleImmutableEntry<>(
            scriptProvider.getScripts(),
            historyProvider.getHistory()
        ));

        filteredScripts.stream()
                       .map(script -> scriptRunner.run(script, version))
                       .forEach(result -> result.isSuccess(historySaver::save).isFailure(errorListener::on));
    }

    public Function<Map.Entry<List<Information<Script>>, List<HistoryInformation>>, List<Information<Script>>> getFilter() {
        return pair -> {
            Map<String, Information<Script>> scripts = pair.getKey()
                                                           .stream()
                                                           .collect(toMap(Information::getVersion, o -> o));

            List<HistoryInformation> history = pair.getValue();
            for (HistoryInformation historyInformation : history) {
                scripts.remove(historyInformation.getVersion());
            }
            return new ArrayList<>(scripts.values());
        };
    }
}
