package org.example.parser;

import java.util.Map;

public interface RequestParser {
    public String requestParse(StringBuilder request,String[] requestLine, Map<String,String> headers, StringBuilder entityBody);
}
