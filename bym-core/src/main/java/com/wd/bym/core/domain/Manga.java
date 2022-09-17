package com.wd.bym.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

@Entity
@Getter
@Setter
@ToString
@Table(name = "mangas")
public class Manga extends AbstractEntity<Long> {

    @Serial
    private static final long serialVersionUID = -6107265379902913942L;

    @Column(name = "title")
    private String title;

}

