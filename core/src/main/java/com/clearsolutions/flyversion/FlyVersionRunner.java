package com.clearsolutions.flyversion;

import com.clearsolutions.flyversion.filter.Filter;
import com.clearsolutions.flyversion.history.HistoryProvider;
import com.clearsolutions.flyversion.history.HistorySaver;
import com.clearsolutions.flyversion.listener.ErrorListener;
import com.clearsolutions.flyversion.script.Information;
import com.clearsolutions.flyversion.script.Result;
import com.clearsolutions.flyversion.script.ScriptProvider;
import com.clearsolutions.flyversion.script.ScriptRunner;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class FlyVersionRunner<Script> {

    private final ErrorListener errorListener;
    private final ScriptProvider<Script> scriptProvider;
    private final ScriptRunner<Script> scriptRunner;
    private final HistoryProvider historyProvider;
    private final HistorySaver<Script> historySaver;
    private final Filter filter;
    private final Double environmentVersion;

    public void run() {
        List<Information<Script>> filteredScripts =
            this.filter.filter(scriptProvider.getScripts(), historyProvider.getHistory(), environmentVersion);

        for (Information<Script> information : filteredScripts) {
            Result result = scriptRunner.run(information)
                                        .successHandler(this::handle)
                                        .failureHandler(this::errorHandle);
            if (result.isFailed()) {
                break;
            }
        }
    }

    private void handle(Information<Script> information) {
        historySaver.save(information);
    }

    private void errorHandle(Throwable error) {
        errorListener.on(error);
    }
}
