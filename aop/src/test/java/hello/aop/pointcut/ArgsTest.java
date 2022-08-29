package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }
    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }
    @Test
    void args() {
        //hello(String)과 매칭
        Assertions.assertTrue(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(String,..)")
                .matches(helloMethod, MemberServiceImpl.class));
    }
    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
     ** args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {
        //Args
        Assertions.assertTrue(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class));
        //Execution
        Assertions.assertTrue(pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("execution(* *(java.io.Serializable))")//매칭 실패
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
        Assertions.assertTrue(pointcut("execution(* *(Object))") //매칭 실패
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
