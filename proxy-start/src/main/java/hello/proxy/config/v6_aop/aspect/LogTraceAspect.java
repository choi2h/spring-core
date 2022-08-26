package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    /*
    @Around - pointcut
    method - advice

    @Aspect를 어드바이저로 변환-저장하는 과정
    1. 스프링 애플리케이션 로딩 시점에 자동 프록시 생성기 호출
    2. 모든 @Aspect 빈 조회
    3. 어드바이저 생성
    4. @Aspect 기반 어드바이저 저장

    어드바이저를 기반으로 프록시 생성
    1. 빈 생성
    2. 빈 후처리기에 전달
    3-1. Advisor 빈 조회
    3-2. @Aspect Advisor 조회
    4. 프록시 적용 대상 체크 (포인트컷 어드바이스)
    5. 프록시 생성
    6. 빈 등록
    */
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        }catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
