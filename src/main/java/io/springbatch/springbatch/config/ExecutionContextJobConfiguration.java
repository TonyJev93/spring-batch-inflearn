package io.springbatch.springbatch.config;

import io.springbatch.springbatch.task.ExecutionContextTasklet1;
import io.springbatch.springbatch.task.ExecutionContextTasklet2;
import io.springbatch.springbatch.task.ExecutionContextTasklet3;
import io.springbatch.springbatch.task.ExecutionContextTasklet4;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExecutionContextTasklet1 executionContextTasklet1;
    private final ExecutionContextTasklet2 executionContextTasklet2;
    private final ExecutionContextTasklet3 executionContextTasklet3;
    private final ExecutionContextTasklet4 executionContextTasklet4;

    private final JobExecutionListener jobExecutionListener;


    @Bean
    public Job executionContextJob() {
        return jobBuilderFactory.get("executionContextJob")
                .start(executionContextStep1())
                .next(executionContextStep2())
                .next(executionContextStep3())
                .next(executionContextStep4())
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    public Step executionContextStep1() {
        return stepBuilderFactory.get("executionContextStep1")
                .tasklet(executionContextTasklet1)
                .build();
    }

    @Bean
    public Step executionContextStep2() {
        return stepBuilderFactory.get("executionContextStep2")
                .tasklet(executionContextTasklet2)
                .build();
    }

    @Bean
    public Step executionContextStep3() {
        return stepBuilderFactory.get("executionContextStep3")
                .tasklet(executionContextTasklet3)
                .build();
    }

    @Bean
    public Step executionContextStep4() {
        return stepBuilderFactory.get("executionContextStep4")
                .tasklet(executionContextTasklet4)
                .build();
    }
}
