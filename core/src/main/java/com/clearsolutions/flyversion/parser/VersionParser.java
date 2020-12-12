package com.clearsolutions.flyversion.parser;

public class VersionParser {

    public Double parse(String version) {
        var values = version.split("_");
        var result = new StringBuilder();
        for (var value : values) {
            result.append(Double.parseDouble(value));
        }
        return Double.parseDouble(result.toString());
    }
}
