package io.springbatch.springbatch.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {

    private final JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        final String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("age", 2L)
                .addDouble("weight", 60.8)
                .toJobParameters();

        final JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if (lastJobExecution != null) {
            for (StepExecution execution : lastJobExecution.getStepExecutions()) {
                BatchStatus status = execution.getStatus();
                System.out.println("status = " + status);

                ExitStatus exitStatus = execution.getExitStatus();
                System.out.println("exitStatus = " + exitStatus);

                String stepName = execution.getStepName();
                System.out.println("stepName = " + stepName);

            }
        }


    }
}
