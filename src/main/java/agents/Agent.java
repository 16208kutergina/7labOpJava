package agents;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class Agent {
    public static Instrumentation inst;

    public static void premain(String argument, Instrumentation inst) {
        Agent.inst = inst;
        new AgentBuilder.Default()
                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
                .type(ElementMatchers.any())
                .transform((builder, typeDesciption, classLoader, module)->builder
                        .method(ElementMatchers.any())
                        .intercept(Advice.to(TimerAdvice.class))
                ).installOn(inst);

        new AgentBuilder.Default()
                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
                .type(ElementMatchers.any())
                .transform((builder, typeDesciption, classLoader, module)->builder
                        .method(ElementMatchers.hasMethodName("main"))
                        .intercept(Advice.to(PrinterNumberClasses.class))
                ).installOn(inst);
    }
}
