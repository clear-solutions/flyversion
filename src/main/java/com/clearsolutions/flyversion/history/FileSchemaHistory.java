package com.clearsolutions.flyversion.history;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FileSchemaHistory implements History {

    private final File history;

    public FileSchemaHistory(String path) {
        this.history = new File(path);
        createFile();
    }

    @Override
    public List<String> getAppliedVersions() {
        return Collections.emptyList();
    }

    @Override
    public boolean apply(String version) {
        return true;
    }

    @SneakyThrows
    private void createFile() {
        String parent = history.getParent();
        File dir = new File(parent);
        if (dir.mkdirs()) {
            log.info("directory {} created for file version {}", dir.getAbsolutePath(), history.getName());
        }
        if (history.createNewFile()) {
            log.info("file version {} is created", history);
        }
    }
}
