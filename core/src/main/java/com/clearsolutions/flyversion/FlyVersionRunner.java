package com.clearsolutions.flyversion;

import com.clearsolutions.flyversion.filter.Filter;
import com.clearsolutions.flyversion.history.HistoryProvider;
import com.clearsolutions.flyversion.history.HistorySaver;
import com.clearsolutions.flyversion.listener.ErrorListener;
import com.clearsolutions.flyversion.script.Information;
import com.clearsolutions.flyversion.script.ScriptProvider;
import com.clearsolutions.flyversion.script.ScriptRunner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FlyVersionRunner<T> {

    private final ErrorListener errorListener;
    private final ScriptProvider<T> scriptProvider;
    private final ScriptRunner<T> scriptRunner;
    private final HistoryProvider historyProvider;
    private final HistorySaver<T> historySaver;
    private final Filter filter;
    private final Double environmentVersion;

    public void run() {
        var filteredScripts =
            this.filter.filter(scriptProvider.getScripts(), historyProvider.getHistory(), environmentVersion);

        for (var information : filteredScripts) {
            var result = scriptRunner.run(information)
                                     .successHandler(this::handle)
                                     .failureHandler(this::errorHandle);
            if (result.isFailed()) {
                break;
            }
        }
    }

    private void handle(Information<T> information) {
        historySaver.save(information);
    }

    private void errorHandle(Throwable error) {
        errorListener.on(error);
    }
}
