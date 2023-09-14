package org.yunghegel.gdx.utils.console;

import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;




public class LogFormatter extends Formatter implements ConsoleLogFormatter {

    public String sourceClassName= " ";
    public String sourceMethodName = " ";

    boolean needToInferCaller = true;

    Map<LogLevel, Level> levelMap = new HashMap<LogLevel, Level>() {{
        put(LogLevel.ERROR, Level.SEVERE);
        put(LogLevel.ERROR, Level.WARNING);
        put(LogLevel.DEFAULT, Level.INFO);
        put(LogLevel.ERROR, Level.FINE);
        put(LogLevel.ERROR, Level.FINEST);
    }};

    public LogLevel getLogLevel(Level level) {
        for (Map.Entry<LogLevel, Level> entry : levelMap.entrySet()) {
            if (entry.getValue().equals(level)) {
                return entry.getKey();
            }
        }
        return LogLevel.DEFAULT;
    }



    @Override
    public String format(String msg, LogRecord record) {
        String prefix = format(record);
        return prefix + msg;
    }

    @Override
    public String format(String msg, Level level) {
        return "["+getLogLevel(level)+"] "+ msg;
    }

    @Override
    public String format(String msg) {

        StringBuilder builder = new StringBuilder();
        return msg;
    }


    @Override
    public String format(LogRecord record) {
        String builder = "["+ getLogLevel(record.getLevel()) + "] " + record.getSourceClassName() + "." + record.getSourceMethodName() + " " + "\n" + this.formatMessage(record) + System.lineSeparator();
        return builder;
    }




}





