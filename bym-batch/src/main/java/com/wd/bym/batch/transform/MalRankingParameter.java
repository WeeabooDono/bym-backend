package com.wd.bym.batch.transform;

public enum MalRankingParameter {
    RANKING_TYPE("ranking_type"),
    OFFSET("offset"),
    LIMIT("limit"),
    FIELDS("fields");

    private final String value;

    MalRankingParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
