package run.app.step;

import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan({"run.app.step.project.system.mapper"})
public class StepApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private StringEncryptor stepEncryptorBean;

    public static void main(String[] args) {
        SpringApplication.run(StepApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Environment environment = appCtx.getBean(Environment.class);

    }
}
