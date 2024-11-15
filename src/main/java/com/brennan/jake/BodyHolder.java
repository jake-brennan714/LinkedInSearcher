package com.brennan.jake;

public class BodyHolder {
    private String body;

    public BodyHolder() {
        this.body = null;
    }

    public BodyHolder(String defaultBody) {
        this.body = defaultBody;
    }

    public String getBody() {
        return this.body;
    }

    public boolean setBody(String newBody) {
        this.body = newBody;
        return this.body.equals(newBody);
    }
}
