package org.yunghegel.gdx.utils.console;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.strongjoshua.console.Console;
import org.yunghegel.gdx.utils.system.OutputStreamInterceptor;


import java.io.*;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Log extends LogFormatter implements ConsoleLogFormatter {
    private Array<LogEntry> logEntries;
    private int numEntries = Console.UNLIMITED_ENTRIES;

    public LogFormatter formatter = new LogFormatter();
    OutputStreamInterceptor interceptor;

    protected Log() {
        super();
        logEntries = new Array<LogEntry>();

    }

    public void setMaxEntries(int numEntries) {
        this.numEntries = numEntries;
    }

    protected void addEntry(String msg, LogLevel level) {
        logEntries.add(new LogEntry(format(msg), level));

    }

    protected void addEntry(LogRecord record) {
        String msg = formatter.format(record);
        logEntries.add(new LogEntry(msg, getLogLevel(record.getLevel())));

    }

    protected void addEntry(String string){
        string = format(string);
        logEntries.add(new LogEntry(string, LogLevel.DEFAULT));
        if (logEntries.size > numEntries && numEntries != Console.UNLIMITED_ENTRIES) {
            logEntries.removeIndex(0);
        }
    }

    protected void addEntry(String msg, Level level){
        msg = format(msg);
        logEntries.add(new LogEntry(msg, getLogLevel(level)));
        if (logEntries.size > numEntries && numEntries != Console.UNLIMITED_ENTRIES) {
            logEntries.removeIndex(0);
        }
    }

    protected void addEntry(String msg, LogRecord record){
        msg = format(msg, record);
        logEntries.add(new LogEntry(msg, getLogLevel(record.getLevel())));
    }

    protected Array<LogEntry> getLogEntries() {
        return logEntries;
    }

    public boolean printToFile(FileHandle fh) {
        if (fh.isDirectory()) {
            throw new IllegalArgumentException("File cannot be a directory!");
        }

        Writer out = null;
        try {
            out = fh.writer(false);
        } catch (Exception e) {
            return false;
        }

        String toWrite = "";
        for (LogEntry l : logEntries) {
            toWrite += l.toString() + "\n";
        }

        try {
            out.write(toWrite);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
        return msg;
    }
}
