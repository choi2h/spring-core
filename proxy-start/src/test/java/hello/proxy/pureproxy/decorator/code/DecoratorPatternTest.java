package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(component);

        decoratorPatternClient.execute();
    }

    @Test
    void decorator1() {
        Component component = new RealComponent();
        MessageDecorator decorator = new MessageDecorator(component);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(decorator);

        decoratorPatternClient.execute();
    }

    @Test
    void decorator2() {
        Component component = new RealComponent();
        MessageDecorator decorator = new MessageDecorator(component);
        TimeDecorator timeDecorator = new TimeDecorator(decorator);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);

        decoratorPatternClient.execute();
    }
}
