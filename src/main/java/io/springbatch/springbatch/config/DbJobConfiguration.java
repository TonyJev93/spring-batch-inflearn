package io.springbatch.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DbJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job dbJob() {
        return jobBuilderFactory.get("dbJob")
                .start(dbStep())
                .build();
    }

    @Bean
    public Step dbStep() {
        return stepBuilderFactory.get("dbStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("=================");
                    System.out.println(" > db Job Start!");
                    System.out.println("=================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
