package agents;

import javassist.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class Agent {
    public static Instrumentation inst;

    static class MyTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws
                IllegalClassFormatException {
            if (!className.contains("TransactionProcessor")) {
                return  classfileBuffer;
            }
            byte[] byteCode = classfileBuffer;
            try {
                ClassPool classPool = new ClassPool(true);
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod method = ctClass.getDeclaredMethod("processTransaction");
                method.addLocalVariable("startTime", CtClass.longType);
                method.insertBefore("startTime = System.currentTimeMillis();");
                method.insertAfter("System.out.println(\" Time: \"+ (System.currentTimeMillis() - startTime));");
                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (IOException | CannotCompileException | NotFoundException e) {
                e.printStackTrace();
            }
            return byteCode; // todo:
        }
    }

    public static void premain(String argument, Instrumentation inst) {
        inst.addTransformer(new MyTransformer());
        //        Agent.inst = inst;
//        new AgentBuilder.Default()
//                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
//                .type(ElementMatchers.any())
//                .transform((builder, typeDesciption, classLoader, module)->builder
//                        .method(ElementMatchers.any())
//                        .intercept(Advice.to(TimerAdvice.class))
//                ).installOn(inst);
//
//        new AgentBuilder.Default()
//                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
//                .type(ElementMatchers.any())
//                .transform((builder, typeDesciption, classLoader, module)->builder
//                        .method(ElementMatchers.hasMestatic thodName("main"))
//                        .intercept(Advice.to(PrinterNumberClasses.class))
//                ).installOn(inst);
    }
}
