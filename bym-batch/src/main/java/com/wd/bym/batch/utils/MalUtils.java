package com.wd.bym.batch.utils;

import com.wd.bym.batch.transform.MalRankingType;
import com.wd.bym.batch.transform.MalRequest;

import java.util.List;

public class MalUtils {

    public static MalRequest defaultMalRequest() {
        return new MalRequest(0, 500, MalRankingType.ALL, List.of("title"));
    }
}
