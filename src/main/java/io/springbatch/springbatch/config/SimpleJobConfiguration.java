package io.springbatch.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(step1())
                .next(step2())
                .build();
    }


    @Bean
    public Job flowJob() {
        return jobBuilderFactory.get("flowJob")
                .start(simpleFlow())
                .next(step5())
                .end()
                .build();

    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("=================== stepContribution =================");
                    final JobParameters jobParameters = stepContribution.getStepExecution().getJobParameters();

                    jobParameters.getParameters().forEach((key, jobParameter) -> System.out.println(key + ":" + jobParameter.getValue()));
                    System.out.println("======================================================");

                    System.out.println("=================== chunkContext =================");
                    final Map<String, Object> jobParameterMap = chunkContext.getStepContext().getJobParameters();

                    jobParameterMap.forEach((key, object) -> System.out.println(key + ":" + object.toString()));
                    System.out.println("======================================================");
                    System.out.println("step1 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step2 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Flow simpleFlow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlow");
        return flowBuilder
                .start(step3())
                .next(step4())
                .end();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step3 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step4 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("step5 was executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
