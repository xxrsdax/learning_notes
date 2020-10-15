package geektime.spring.hello.greeting;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class GreetingApplicationRunner implements ApplicationRunner {

    private String name;


    public GreetingApplicationRunner() {
        this("testTime");
    }


    public GreetingApplicationRunner(String name) {
        this.name = name;
        log.info("Initializing GreetingApplicationRunner.----" + this.name);
    }

    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello everyone! We all like Spring! ");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
