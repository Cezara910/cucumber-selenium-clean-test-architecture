package framework;

import org.slf4j.LoggerFactory;

public class Logger {
    private static final StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    static org.slf4j.Logger getLogger() {
        String loggerName = stackWalker.walk(frames -> frames
                .skip(1)
                .findFirst()
                .map(frame -> frame.getClassName() + "[" + frame.getMethodName() + "]")
                .orElse(Logger.class.getName()));
        return LoggerFactory.getLogger(loggerName);

    }
}
