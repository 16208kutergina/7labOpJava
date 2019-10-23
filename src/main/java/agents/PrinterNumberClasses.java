package agents;

import net.bytebuddy.asm.Advice;

public class PrinterNumberClasses {

    @Advice.OnMethodExit
    static void exit() throws Exception {
        System.out.println("Common classes load "+Agent.inst.getAllLoadedClasses().length);
    }
}
