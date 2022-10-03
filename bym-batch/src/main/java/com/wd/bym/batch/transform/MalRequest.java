package com.wd.bym.batch.transform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MalRequest {

    private int offset;

    private int limit;

    private MalRankingType rankingType;

    private List<String> fields;

    public String getComaSeparatedFields() {
        return String.join(",", this.fields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MalRequest that = (MalRequest) o;
        return offset == that.offset && limit == that.limit && rankingType == that.rankingType && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit, rankingType, fields);
    }
}
