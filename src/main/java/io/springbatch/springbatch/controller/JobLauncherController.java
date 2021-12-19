package io.springbatch.springbatch.controller;

import io.springbatch.springbatch.controller.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final Job launcherJob;
    private final JobLauncher defaultJobLauncher;

    private final BasicBatchConfigurer basicBatchConfigurer;


    @PostMapping("/batch-sync")
    public String launchSync(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        SimpleJobLauncher syncJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher(); // BasicBatchConfigurer 는 실제 SimpleJobLauncher 객체를 가지고 있음
        syncJobLauncher.setTaskExecutor(new SyncTaskExecutor());

        syncJobLauncher.run(launcherJob, jobParameters);

        return "sync batch completed";
    }

    @PostMapping("/batch-async")
    public String launchAsync(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();
        // SimpleJobLauncher asyncJobLauncher = (SimpleJobLauncher) defaultJobLauncher // Type Casting 예외 발생 (JobLauncher 는 SimpleJobLauncher 의 Proxy 객체로 생성하기 때문에 SimpleJobLauncher 로 형변환 불가능)
        SimpleJobLauncher asyncJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher(); // BasicBatchConfigurer 는 실제 SimpleJobLauncher 객체를 가지고 있음
        asyncJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        asyncJobLauncher.run(launcherJob, jobParameters);

        return "async batch completed";
    }
}
