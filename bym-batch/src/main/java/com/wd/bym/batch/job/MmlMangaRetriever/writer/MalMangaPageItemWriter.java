package com.wd.bym.batch.job.MmlMangaRetriever.writer;

import com.wd.bym.core.annotation.Tx;
import com.wd.bym.core.domain.Manga;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
public class MalMangaPageItemWriter implements ItemWriter<List<Manga>> {

    private final EntityManagerFactory entityManagerFactory;

    public MalMangaPageItemWriter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    @Tx
    public void write(List<? extends List<Manga>> items) {
        EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        assert em != null;
        items.get(0).forEach(em::persist);
    }
}
