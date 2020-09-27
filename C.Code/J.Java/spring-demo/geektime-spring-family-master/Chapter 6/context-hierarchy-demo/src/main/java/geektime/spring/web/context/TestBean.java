package geektime.spring.web.context;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xxrsdax
 */
@AllArgsConstructor
@Slf4j
public class TestBean {

    private String context;

    public void hello() {
        log.info("hello " + context);
    }

}
