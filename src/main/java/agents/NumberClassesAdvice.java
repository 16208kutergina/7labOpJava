package agents;


import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

public class NumberClassesAdvice {
    public static int numberClasses = 0;

    @Advice.OnMethodEnter
    static void enter(@Advice.Origin String method) throws Exception {
        numberClasses++;
    }
}
