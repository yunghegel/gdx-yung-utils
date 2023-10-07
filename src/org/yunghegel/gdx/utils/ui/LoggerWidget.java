package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

import org.yunghegel.gdx.utils.console.CommandExecutor;
import org.yunghegel.gdx.utils.console.GUIConsole;
import org.yunghegel.gdx.utils.console.LogLevel;
import org.yunghegel.gdx.utils.system.OutputStreamInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerWidget extends VisTable {

    public GUIConsole console;
    Stage stage;
    CommandExecutor commandExecutor;
    OutputStreamInterceptor interceptor;
    Map<LogLevel, Level> levelMap = new HashMap<>();
    Logger logger = Logger.getGlobal();

    {
        levelMap.put(LogLevel.ERROR, Level.SEVERE);
        levelMap.put(LogLevel.ERROR, Level.WARNING);
        levelMap.put(LogLevel.DEFAULT, Level.INFO);
        levelMap.put(LogLevel.ERROR, Level.FINE);
        levelMap.put(LogLevel.ERROR, Level.FINEST);
    }

    public LogLevel getLogLevel(Level level) {
        for (Map.Entry<LogLevel, Level> entry : levelMap.entrySet()) {
            if (entry.getValue().equals(level)) {
                return entry.getKey();
            }
        }
        return LogLevel.DEFAULT;
    }

    public LoggerWidget(Skin skin) {
        console = new GUIConsole(skin,false, Input.Keys.APOSTROPHE, VisWindow.class,VisTable.class,"list-background", TextField.class, TextButton.class, Label.class, VisScrollPane.class);
        add(console).expand().fill();
        commandExecutor = new CommandExecutor();
        console.setCommandExecutor(commandExecutor);


        console.refresh();


        interceptor = new OutputStreamInterceptor(System.out);
        OutputStreamInterceptor.GlobalLoggingHandler.OnLogListener listener = (record) -> {
            log(record);
        };
        OutputStreamInterceptor.OnPrintListener printListener = (message) -> {
            log(message);
        };
        interceptor.setup(printListener, listener);
        interceptor.setGlobalLoggingFormatter(console.log.formatter);
        interceptor.setGlobalLoggingHandler(interceptor.globalLoggingHandler);

        interceptor.setOnPrintListener(log->{
            console.log(log);
        });
        interceptor.setOnLogListener(log->{
            console.log(log);
        });
    }

    void createInputListener() {
        stage.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                getStage().cancelTouchFocus();
                getStage().setKeyboardFocus(null);
                getStage().setScrollFocus(null);
            }
        });
    }



    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        console.setCommandExecutor(commandExecutor);
    }

    public void log(LogLevel level, String message) {
        console.log(message, level);
        System.out.println(message);
    }

    public void log(String message){
        console.log(message);
        System.out.println(message);
    }

    public void log(LogRecord record) {
        console.log(record);
        System.out.println(record.getMessage());
    }

}
