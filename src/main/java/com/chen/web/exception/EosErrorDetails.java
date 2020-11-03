package com.chen.web.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EosErrorDetails {

    private String message;

    private String file;

    @JsonProperty("line_number")
    private Integer lineNumber;

    private String method;

    private EosErrorDetails() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "EosErrorDetails{" +
                "message='" + message + '\'' +
                ", file='" + file + '\'' +
                ", lineNumber=" + lineNumber +
                ", method='" + method + '\'' +
                '}';
    }
}
