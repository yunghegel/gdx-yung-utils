package org.yunghegel.gdx.utils.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;


import com.kotcrab.vis.ui.widget.PopupMenu;
import com.strongjoshua.console.CommandHistory;
import org.yunghegel.gdx.utils.system.OutputStreamInterceptor;

import java.util.logging.LogRecord;


public class GUIConsole extends AbstractConsole {
    private int keyID;

    private ConsoleDisplay display;
    private boolean hidden = false;
    private boolean usesMultiplexer;
    private InputProcessor appInput;
    private InputMultiplexer multiplexer;
    private Stage stage;
    private CommandHistory commandHistory;
    private CommandCompleter commandCompleter;
    private Window consoleWindow;
    private Table consoleTable;
    private boolean hasHover;
    private Color hoverColor, noHoverColor;
    private Vector3 stageCoords = new Vector3();
    private ScrollPane scroll;

    private Class<? extends Table> tableClass;
    private String tableBackground;

    private Class<? extends TextField> textFieldClass;
    private Class<? extends TextButton> textButtonClass;
    private Class<? extends Label> labelClass;
    private Class<? extends ScrollPane> scrollPaneClass;

    OutputStreamInterceptor interceptor;
    LogFormatter formatter;

    public GUIConsole() {
        this(new Skin(Gdx.files.classpath("default_skin/uiskin.json")));
    }


    public GUIConsole(Skin skin) {
        this(skin, true);
    }

    public GUIConsole(boolean useMultiplexer) {
        this(new Skin(Gdx.files.classpath("default_skin/uiskin.json")), useMultiplexer);
    }

    public GUIConsole(Skin skin, boolean useMultiplexer) {
        this(skin, useMultiplexer, Input.Keys.APOSTROPHE);
    }

    public GUIConsole(Skin skin, boolean useMultiplexer, int keyID) {
        this(skin, useMultiplexer, keyID, Window.class, Table.class, "default-rect", TextField.class, TextButton.class, Label.class, ScrollPane.class);
    }

    public GUIConsole(Skin skin, boolean useMultiplexer, int keyID, Class<? extends Window> windowClass, Class<? extends Table> tableClass, String tableBackground, Class<? extends TextField> textFieldClass, Class<? extends TextButton> textButtonClass, Class<? extends Label> labelClass, Class<? extends ScrollPane> scrollPaneClass) {
        super();

        formatter = new LogFormatter();

        this.tableClass = tableClass;
        this.tableBackground = tableBackground;
        this.textFieldClass = textFieldClass;
        this.textButtonClass = textButtonClass;
        this.labelClass = labelClass;
        this.scrollPaneClass = scrollPaneClass;



        this.keyID = keyID;
        stage = new Stage();
        display = new ConsoleDisplay(skin);
        commandHistory = new CommandHistory();
        commandCompleter = new CommandCompleter();
        logToSystem = false;

        usesMultiplexer = useMultiplexer;
        if (useMultiplexer) {
            resetInputProcessing();
        }

        display.root.pad(4);
        display.root.padTop(22);
//        display.root.setFillParent(true);
        display.showSubmit(false);

        try {
            consoleWindow = windowClass.getConstructor(String.class, Skin.class).newInstance("Console", skin);
        } catch (Exception e) {
            try {
                consoleWindow = windowClass.getConstructor(String.class).newInstance("Console");
            } catch (Exception e2) {
                throw new RuntimeException("Window class does not support either (<String>, <Skin>) or (<String>) constructors.");
            }
        }

        try {
            consoleTable = tableClass.getConstructor(Skin.class).newInstance(skin);
        } catch (Exception e) {
            try {
                consoleTable = tableClass.getConstructor().newInstance();
            } catch (Exception e2) {
                throw new RuntimeException("Table class does not support either (<Skin>) or () constructors.");
            }
        }

        /*consoleWindow.setMovable(true);
        consoleWindow.setResizable(true);
        consoleWindow.setKeepWithinStage(true);
        consoleWindow.addActor(display.root);
        consoleWindow.setTouchable(Touchable.disabled);*/

        add(display.root).expand().fill();

        hoverColor = new Color(1, 1, 1, 1);
        noHoverColor = new Color(1, 1, 1, 1);

       /* stage.addListener(new GUIConsole.DisplayListener());
        stage.addActor(consoleWindow);
        stage.setKeyboardFocus(display.root);*/

        setSizePercent(50, 50);
        setPositionPercent(50, 50);

        enableSubmitButton(true);
        setSubmitText(" > ");
    }

    @Override
    public void setMaxEntries(int numEntries) {
        if (numEntries > 0 || numEntries == UNLIMITED_ENTRIES) {
            log.setMaxEntries(numEntries);
        } else {
            throw new IllegalArgumentException("Maximum entries must be greater than 0 or use Console.UNLIMITED_ENTRIES.");
        }
    }

    public ConsoleDisplay getDisplay() {
        return display;
    }

    @Override
    public void clear() {
        log.getLogEntries().clear();
        display.refresh();
    }

    @Override
    public void setSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Pixel size must be greater than 0.");
        }
//        consoleWindow.setSize(width, height);
        consoleTable.getCell(display.root).size(width, height);
    }

    @Override
    public void setSizePercent(float wPct, float hPct) {
        if (wPct <= 0 || hPct <= 0) {
            throw new IllegalArgumentException("Size percentage must be greater than 0.");
        }
        if (wPct > 100 || hPct > 100) {
            throw new IllegalArgumentException("Size percentage cannot be greater than 100.");
        }
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        consoleWindow.setSize(w * wPct / 100.0f, h * hPct / 100.0f);
    }

    @Override
    public void setPosition(int x, int y) {
        consoleWindow.setPosition(x, y);
    }

    @Override
    public void setPositionPercent(float xPosPct, float yPosPct) {
        if (xPosPct > 100 || yPosPct > 100) {
            throw new IllegalArgumentException("Error: The console would be drawn outside of the screen.");
        }
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        consoleWindow.setPosition(w * xPosPct / 100.0f, h * yPosPct / 100.0f);
    }

    @Override
    public void resetInputProcessing() {
      /*  usesMultiplexer = true;
        appInput = Gdx.input.getInputProcessor();
        if (appInput != null) {
            if (hasStage(appInput)) {
                log("Console already added to input processor!", LogLevel.ERROR);
                Gdx.app.log("Console", "Already added to input processor!");
                return;
            }
            multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(stage);
            multiplexer.addProcessor(appInput);
            Gdx.input.setInputProcessor(multiplexer);
        } else {
            Gdx.input.setInputProcessor(stage);
        }*/
    }

    /**
     * Compares the given processor to the console's stage. If given a multiplexer, it is iterated through recursively to check all
     * of the multiplexer's processors for comparison.
     *
     * @param processor
     * @return processor == this.stage
     */
    private boolean hasStage(InputProcessor processor) {
        if (!(processor instanceof InputMultiplexer)) {
            return processor == stage;
        }
        InputMultiplexer im = (InputMultiplexer) processor;
        SnapshotArray<InputProcessor> ips = im.getProcessors();
        for (InputProcessor ip : ips) {
            if (hasStage(ip)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    @Override
    public void draw() {
//        if (disabled) {
//            return;
//        }
//        stage.act();
//
//        if (hidden) {
//            return;
//        }
//        stage.draw();
    }

    @Override
    public void refresh() {
        this.refresh(true);
    }

    @Override
    public void refresh(boolean retain) {
        float oldWPct = 0, oldHPct = 0, oldXPosPct = 0, oldYPosPct = 0;
        if (retain) {
            oldWPct = consoleWindow.getWidth() / stage.getWidth() * 100;
            oldHPct = consoleWindow.getHeight() / stage.getHeight() * 100;
            oldXPosPct = consoleWindow.getX() / stage.getWidth() * 100;
            oldYPosPct = consoleWindow.getY() / stage.getHeight() * 100;
        }
        int width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
        stage.getViewport().setWorldSize(width, height);
        stage.getViewport().update(width, height, true);
        if (retain) {
            this.setSizePercent(oldWPct, oldHPct);
            this.setPositionPercent(oldXPosPct, oldYPosPct);
        }
    }

    @Override
    public void log(String msg, LogLevel level) {
        super.log(msg, level);
        display.refresh();
    }

    @Override
    public void log(Throwable exception, LogLevel level) {
        super.log(exception, level);
        display.refresh();
    }

    @Override
    public void log(LogRecord record) {
        super.log(formatter.format(record));
        display.refresh();
    }

    public void log(String msg)  {
        	super.log(formatter.format(msg));
        	display.refresh();
    }


    @Override
    public void setDisabled(boolean disabled) {
        if (disabled) {
            display.setHidden(true);
        }
        this.disabled = disabled;
    }

    @Override
    public int getDisplayKeyID() {
        return keyID;
    }

    @Override
    public void setDisplayKeyID(int code) {
        if (code == Input.Keys.ENTER) {
            return;
        }
        keyID = code;
    }

    @Override
    public boolean hitsConsole(float screenX, float screenY) {
        if (disabled || hidden) {
            return false;
        }
        stage.getCamera().unproject(stageCoords.set(screenX, screenY, 0));
        return stage.hit(stageCoords.x, stageCoords.y, true) != null;
    }

    @Override
    public void dispose() {
        if (usesMultiplexer && appInput != null) {
            Gdx.input.setInputProcessor(appInput);
        }
        stage.dispose();
    }

    @Override
    public boolean isVisible() {
        return !hidden;
    }

    @Override
    public void setVisible(boolean visible) {
        display.setHidden(!visible);
    }

    @Override
    public void select() {
        display.select();
    }

    @Override
    public void deselect() {
        display.deselect();
    }

    @Override
    public void setTitle(String title) {
        consoleWindow.getTitleLabel().setText(title);
    }

    private void refreshWindowColor() {
        consoleWindow.setColor(hasHover ? hoverColor : noHoverColor);
    }

    @Override
    public void setHoverAlpha(float alpha) {
        hoverColor.a = alpha;
        refreshWindowColor();
    }

    @Override
    public void setNoHoverAlpha(float alpha) {
        noHoverColor.a = alpha;
        refreshWindowColor();
    }

    @Override
    public void setHoverColor(Color color) {
        hoverColor = color;
        refreshWindowColor();
    }

    @Override
    public void setNoHoverColor(Color color) {
        noHoverColor = color;
        refreshWindowColor();
    }

    @Override
    public void enableSubmitButton(boolean enable) {
        display.showSubmit(enable);
    }

    @Override
    public void setSubmitText(String text) {
        display.setSubmitText(text);
    }

    @Override
    public Window getWindow() {
        return this.consoleWindow;
    }


    private class ConsoleDisplay {
        private Table root, logEntries;
        private TextField input;
        private TextButton submit;
        private TextButton directory;
        private Skin skin;
        private Array<Label> labels;
        private String fontName;
        private boolean selected = true;
        private ConsoleContext context;
        private Cell<TextButton> submitCell;
        private Cell<TextButton> directoryCell;
        private PopupMenu directories;

        ConsoleDisplay(Skin skin) {
            try {
                root = tableClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Table class does not support empty constructor.");
            }
            this.skin = skin;
            context = new ConsoleContext(tableClass, labelClass, skin, tableBackground);

            if (skin.has("small-font-normal", BitmapFont.class)) fontName = "small-font-normal";
            else fontName = "default";

            TextField.TextFieldStyle tfs = skin.get(TextField.TextFieldStyle.class);
            tfs.font = skin.getFont(fontName);

            labels = new Array<Label>();

            try {
                logEntries = tableClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Table class does not support empty constructor.");
            }

            try {
                input = textFieldClass.getConstructor(String.class, TextField.TextFieldStyle.class).newInstance("", tfs);
            } catch (Exception e) {
                throw new RuntimeException("TextField class does not support (<String>, <Skin>) constructor.");
            }
            input.setTextFieldListener(new FieldListener());

            directory = new TextButton(" / ", skin);

            try {
                submit = textButtonClass.getConstructor(String.class, Skin.class).newInstance("Submit", skin);
            } catch (Exception e) {
                try {
                    submit = textButtonClass.getConstructor(String.class).newInstance("Submit");
                } catch (Exception e2) {
                    throw new RuntimeException("TextButton class does not support either (<String>, <Skin>) or (<String>) constructors.");
                }
            }
            submit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    submit();
                }
            });

            try {
                scroll = scrollPaneClass.getConstructor(Actor.class, Skin.class).newInstance(logEntries, skin);
            } catch (Exception e) {
                try {
                    scroll = scrollPaneClass.getConstructor(Actor.class).newInstance(logEntries);
                } catch (Exception e2) {
                    throw new RuntimeException("ScrollPane class does not support either (<Actor>, <Skin>) or (<Actor>) constructors.");
                }
            }
            scroll.setFadeScrollBars(false);
            scroll.setScrollbarsOnTop(false);
            scroll.setOverscroll(false, false);
            scroll.addListener(new InputListener() {
                @Override
                public boolean scrolled(InputEvent event, float x, float y, float scrollAmountX, float scrollAmountY) {
                    closeContext();
                    return super.scrolled(event, x, y, scrollAmountX, scrollAmountY);
                }
            });

            root.add(scroll).colspan(3).expand().fill().pad(4).row();
            directoryCell = root.add(directory).padLeft(2).padRight(2);
            root.add(input).expandX().fillX().pad(4);
            submitCell = root.add(submit);
            root.addListener(new KeyListener(input));
            Label l = new Label(">Type 'help' for help.", skin, fontName, LogLevel.DEFAULT.getColor());
            log.addEntry("'help' for a list of possible commands", LogLevel.SUCCESS);
            logEntries.add(l).expand().fill().row();

            refresh();
        }

        void refresh() {
            Array<LogEntry> entries = log.getLogEntries();
            logEntries.clear();

            // expand first so labels start at the bottom
            logEntries.add().expand().fill().row();
            int size = entries.size;
            for (int i = 0; i < size; i++) {
                LogEntry le = entries.get(i);
                Label l;
                // recycle the labels so we don't create new ones every refresh
                if (labels.size > i) {
                    l = labels.get(i);
                } else {
                    try {
                        l = labelClass.getConstructor(CharSequence.class, Skin.class, String.class, Color.class).newInstance("", skin, fontName, LogLevel.DEFAULT.getColor());
                    } catch (Exception e) {
                        try {
                            l = labelClass.getConstructor(CharSequence.class, String.class, Color.class).newInstance("", fontName, LogLevel.DEFAULT.getColor());
                        } catch (Exception e2) {
                            throw new RuntimeException("Label class does not support either (<String>, <Skin>, <String>, <Ansi>) or (<String>, <String>, <Ansi>) constructors.");
                        }
                    }
                    l.setWrap(true);
                    labels.add(l);
                    l.addListener(new LogListener(l, skin.getDrawable(tableBackground)));
                }
                // I'm not sure about the extra space, but it makes the label highlighting look much better with VisUI
                l.setText(le.toConsoleString());

                l.setColor(le.getColor());
                if(le.getLevel() != LogLevel.DEFAULT) {
                    l.setStyle(skin.get("default",Label.LabelStyle.class));
                }
                logEntries.add(l).expandX().fillX().top().left().row();
            }
            scroll.validate();
            scroll.setScrollPercentY(1);
        }

        private void setHidden(boolean h) {
            hidden = h;
            if (hidden) {
                consoleWindow.setTouchable(Touchable.disabled);
                stage.setKeyboardFocus(null);
                stage.setScrollFocus(null);
            } else {
                input.setText("");
                consoleWindow.setTouchable(Touchable.enabled);
                if (selected) {
                    select();
                }
            }
        }

        void select() {
            selected = true;
            if (!hidden) {
                stage.setKeyboardFocus(input);
                stage.setScrollFocus(scroll);
            }
        }

        void deselect() {
            selected = false;
            stage.setKeyboardFocus(null);
            stage.setScrollFocus(null);
        }

        void openContext(Label label, float x, float y) {
            context.setLabel(label);
            context.setPosition(x, y);
            context.setStage(stage);
        }

        void closeContext() {
            context.remove();
        }

        boolean submit() {
            String s = input.getText();
            if (s.length() == 0 || s.split(" ").length == 0) {
                return false;
            }
            if (exec != null) {
                commandHistory.store(s);
                execCommand(s);
            } else {
                log("No command executor has been set. " + "Please call setCommandExecutor for this console in your code and restart.", LogLevel.ERROR);
            }
            input.setText("");
            return true;
        }

        void showSubmit(boolean show) {
            submit.setVisible(show);
            submitCell.size(show ? submit.getPrefWidth() : 0, show ? submit.getPrefHeight() : 0);
        }

        void setSubmitText(String text) {
            submit.setText(text);
            showSubmit(submit.isVisible());
        }
    }

    private class FieldListener implements TextField.TextFieldListener {
        @Override
        public void keyTyped(TextField textField, char c) {
            if ((String.valueOf(c)).equalsIgnoreCase(Input.Keys.toString(keyID))) {
                String s = textField.getText();
                textField.setText(s.substring(0, s.length() - 1));
            }
        }
    }

    private class KeyListener extends InputListener {
        public TextField input;

        protected KeyListener(TextField tf) {
            input = tf;
        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            if (disabled) return false;

            // reset command completer because input string may have changed
            if (keycode != Input.Keys.TAB) {
                commandCompleter.reset();
            }

            if (keycode == Input.Keys.ENTER && !hidden) {
                commandHistory.getNextCommand(); // Makes up arrow key repeat the same command after pressing enter
                return display.submit();
            } else if (keycode == Input.Keys.UP && !hidden) {
                input.setText(commandHistory.getPreviousCommand());
                input.setCursorPosition(input.getText().length());
                return true;
            } else if (keycode == Input.Keys.DOWN && !hidden) {
                input.setText(commandHistory.getNextCommand());
                input.setCursorPosition(input.getText().length());
                return true;
            } else if (keycode == Input.Keys.TAB && !hidden) {
                String s = input.getText();
                if (s.length() == 0) {
                    return false;
                }
                if (commandCompleter.isNew()) {
                    commandCompleter.set(exec, s);
                }
                input.setText(commandCompleter.next());
                input.setCursorPosition(input.getText().length());
                return true;
            }
            return false;
        }
    }

    public class DisplayListener extends InputListener {
        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            if (disabled) return false;
            if (keycode == keyID) {
                display.setHidden(!hidden);
                return true;
            }
            return false;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (pointer != -1) return;
            hasHover = true;
            refreshWindowColor();
            getStage().setScrollFocus(scroll);
            getStage().setKeyboardFocus(getDisplay().input);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            if (pointer != -1) return;
            hasHover = false;
            refreshWindowColor();
            getStage().setScrollFocus(null);
            getStage().setKeyboardFocus(null);
            getStage().cancelTouchFocus();
        }
    }

    private class LogListener extends ClickListener {
        private Label self;
        private Drawable highlighted;

        LogListener(Label label, Drawable highlighted) {
            self = label;
            this.highlighted = highlighted;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            Vector2 pos = self.localToStageCoordinates(new Vector2(x, y));
            display.openContext(self, pos.x, pos.y);
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (pointer != -1) return;
            self.getStyle().background = highlighted;
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            if (pointer != -1) return;
            self.getStyle().background = null;
        }
    }
}
