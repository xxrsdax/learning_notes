package geektime.spring.web.foo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author xxrsdax
 */
@Aspect
@Slf4j
public class FooAspect {


    /**
     * Spring AOP also supports an additional PCD named bean.
     * This PCD lets you limit the matching of join points to a particular named Spring bean
     * or
     * to a set of named Spring beans (when using wildcards).
     * The bean PCD has the following form
     * */
    /**
     *
     * 简单的说 bean(bean名称*)  就是可以匹配指定 符合匹配规则 的bean
     *
     */
    @AfterReturning("bean(testBean*)")
    public void printAfter() {
        log.info("after hello()");
    }


    @Around("repositoryOps()")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();
        String name = "-";
        String result = "Y";

        try {
            name = pjp.getSignature().toShortString();
            return pjp.proceed();

        } catch (Throwable t) {
            result = "N";
            throw t;

        } finally {
            long endTime = System.currentTimeMillis();
            log.info("{};{};{}ms", name, result, endTime - startTime);

        }
    }

    @Pointcut("execution(* geektime.spring.web.foo..*(..))")
    private void repositoryOps() {
    }


}
