package com.wd.bym.batch.transform;

public enum MalRankingType {
    ALL("all"),
    MANGA("manga"),
    NOVELS("novels"),
    ONE_SHOTS("oneshots"),
    DOUJIN("doujin"),
    MANHWA("manhwa"),
    MANHUA("manhua"),
    BY_POPULARITY("bypopularity"),
    FAVORITE("favorite");

    private final String value;

    MalRankingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
