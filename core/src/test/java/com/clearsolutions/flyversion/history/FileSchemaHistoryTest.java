package com.clearsolutions.flyversion.history;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileSchemaHistoryTest {

    @AfterEach
    public void removeFiles() {
        ImmutableList.of(
            "version/schema1.csv",
            "version/schema2.csv")
                     .stream()
                     .map(File::new)
                     .forEach(file -> {
                         File dir = new File(file.getParent());
                         dir.delete();
                         file.delete();
                     });
    }

    @Test
    void getAppliedVersions_FileDoesNotExist_shouldCreate() {
        assertThat(new File("version/schema1.csv")).doesNotExist();

        FileSchemaHistory history = new FileSchemaHistory("version/schema1.csv");
        List<String> expected = history.getAppliedVersions();

        assertThat(expected).isEmpty();
        assertThat(new File("version/schema1.csv")).exists();
    }

    @Test
    void apply_FileDoesNotExist_shouldCreate() {
        assertThat(new File("version/schema2.csv")).doesNotExist();

        FileSchemaHistory history = new FileSchemaHistory("version/schema2.csv");
        boolean expected = history.apply("1");

        assertThat(expected).isTrue();
        assertThat(new File("version/schema2.csv")).exists();
    }
}