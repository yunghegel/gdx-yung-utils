package org.yunghegel.gdx.utils.console;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.logging.Level;
import java.util.logging.LogRecord;


public class LogEntry {
    private String text;
    private LogLevel level;
    private long timeStamp;

    protected LogEntry (String msg, LogLevel level) {
        this.text = msg;
        this.level = level;
        timeStamp = TimeUtils.millis();
    }

    protected LogEntry(String msg, Level level){

    }

    protected LogEntry(LogRecord record){

    }

    protected LogEntry(String msg){


    }

    public Color getColor () {
        return level.getColor();
    }

    public LogLevel getLevel () {
        return level;
    }

    protected String toConsoleString () {
        String r = "";
        if (level.equals(LogLevel.COMMAND)) {
            r += level.getIdentifier();
        }
        r += text;
        return r;
    }

    @Override public String toString () {
        return timeStamp + ": " + level.getIdentifier() + text;
    }
}