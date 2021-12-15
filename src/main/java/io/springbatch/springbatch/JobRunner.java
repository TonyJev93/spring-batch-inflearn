package io.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// ApplicationRunner : SpringBoot 가 초기화되면 호출되는 기본 러너
@Component
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher; // SpringBatch 초기화 시 Bean 으로 등록 됨.
    private final Job simpleJob;

    public JobRunner(JobLauncher jobLauncher, Job simpleJob) {
        this.jobLauncher = jobLauncher;
        this.simpleJob = simpleJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user2")
                .addLong("age", 20L)
                .toJobParameters();

        jobLauncher.run(simpleJob, jobParameters);
    }
}
