package org.yunghegel.gdx.utils.console;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public interface ConsoleLogFormatter {

        String format(String msg, LogRecord record);

        String format(String msg, Level level);

        String format(String msg) throws Exception;


}
