package io.springbatch.springbatch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

// ApplicationRunner : SpringBoot 가 초기화되면 호출되는 기본 러너
@Component
@AllArgsConstructor
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher; // SpringBatch 초기화 시 Bean 으로 등록 됨.
    private final Job simpleJob;

    /*
     * @Component 주석 처리 후(ApplicationRunner 비활성화), spring.batch.job.enable = false 처리 후,
     * java -jar spring-batch-0.0.1-SNAPSHOT.jar name=user1 seq(long)=2L date(date)=2021/01/01 weight(double)=16.5
     * 명령어 수행 시 parameter 주입 가능.
     * */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("age", 20L)
                .addDate("birth", new Date())
                .addDouble("weight", 65.5)
                .toJobParameters();

        jobLauncher.run(simpleJob, jobParameters);
    }
}
