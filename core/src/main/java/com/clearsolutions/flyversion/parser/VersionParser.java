package com.clearsolutions.flyversion.parser;

public class VersionParser {

    public Double parse(String version) {
        String[] values = version.split("_");
        StringBuilder result = new StringBuilder();
        for (String value : values) {
            result.append(Double.parseDouble(value));
        }
        return Double.parseDouble(result.toString());
    }
}
