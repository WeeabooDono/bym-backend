package com.wd.bym.batch.job.MmlMangaRetriever.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * On veut créer le bean pour ce Job dans deux conditions :
 * - On le lance depuis en mode service (paramètre défini dans l'application.yml).
 * - On le lance en ligne de commande, et il faut un arg --batch.name=mmlMangaRetrieverJob
 */
public class MmlMangaRetrieverJobLaunchCondition extends AnyNestedCondition {

    public MmlMangaRetrieverJobLaunchCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "bym.batch", name = "launcher.mode", havingValue = "service")
    static class LaunchModeService {
    }

    @ConditionalOnProperty(name = "batch.name", havingValue = "mmlMangaRetrieverJob")
    static class IsMmlMangaRetrieverJob {
    }
}
