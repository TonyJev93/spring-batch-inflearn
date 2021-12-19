package io.springbatch.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobLauncherConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job launcherJob() {
        return jobBuilderFactory.get("launcherJob")
                .start(launcherStep1())
                .next(launcherStep2())
                .build();
    }

    @Bean
    public Step launcherStep1() {
        return stepBuilderFactory.get("launcherStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    Thread.sleep(3000);
                    System.out.println("launcherStep1 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step launcherStep2() {
        return stepBuilderFactory.get("launcherStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    Thread.sleep(3000);
                    System.out.println("launcherStep2 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
