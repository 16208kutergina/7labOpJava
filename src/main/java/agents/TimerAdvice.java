package agents;

import net.bytebuddy.asm.Advice;

public class TimerAdvice {

    @Advice.OnMethodEnter
    static long enter(@Advice.Origin String method) throws Exception {
        long start = System.currentTimeMillis();
        return start;
    }

    @Advice.OnMethodExit
    static void exit(@Advice.Origin String method, @Advice.Enter long start) throws Exception {
        long end = System.currentTimeMillis();
        System.out.println(method + " took " + (end - start) + " milliseconds ");
    }


}