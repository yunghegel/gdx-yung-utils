package org.yunghegel.gdx.utils.system;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.*;

public class OutputStreamInterceptor<T extends PrintStream> extends PrintStream {

    public OnPrintListener onPrintListener;
    public GlobalLoggingHandler globalLoggingHandler;
    public GlobalLoggingFormatter globalLoggingFormatter;

    private T printStream;

    public OutputStreamInterceptor(OutputStream out) {
        super(out);
        intercept();
    }

    public OutputStreamInterceptor(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
        intercept();
    }

    public OutputStreamInterceptor(){
        super(System.out);
        intercept();
    }

    public void setup(OnPrintListener printListener, GlobalLoggingHandler.OnLogListener logListener){
        globalLoggingHandler = new GlobalLoggingHandler(logListener);
        globalLoggingFormatter = new GlobalLoggingFormatter(printListener);
        globalLoggingHandler.setFormatter(globalLoggingFormatter);
        setGlobalLoggingHandler(globalLoggingHandler);
    }

    void intercept(){
        System.setOut(this);
        System.out.println("intercepted"+System.out.toString());
        System.setErr(this);
    }




    public void interceptLogger(Logger logger){
        logger.addHandler(globalLoggingHandler);
    }

    public void setOnPrintListener(OnPrintListener onPrintListener) {
        this.onPrintListener = onPrintListener;
    }

    @Override
    public void print(String s) {
        super.print(s);
        if (onPrintListener != null) {
            onPrintListener.onPrint(s);
        }
    }

    @Override
    public void println(String x) {
        super.println(x);
        if (onPrintListener != null) {
            onPrintListener.onPrint(x);
        }
    }

    public interface OnPrintListener {
        void onPrint(String s);
    }

    public void setOnLogListener(GlobalLoggingHandler.OnLogListener onLogListener){
        globalLoggingHandler.setOnLogListener(onLogListener);
    }

    public void setGlobalLoggingFormatter(Formatter formatter){
        globalLoggingHandler.setFormatter(formatter);
    }



    public void setGlobalLoggingHandler(GlobalLoggingHandler handler){
        Logger.getGlobal().addHandler(handler);

    }

    public static class GlobalLoggingHandler extends Handler {

        public interface OnLogListener {
            void onLog(LogRecord record);
        }

        OnLogListener onLogListener;

        public GlobalLoggingHandler(OnLogListener onLogListener) {
            this.onLogListener = onLogListener;
        };

        public GlobalLoggingHandler() {
        }

        public void setOnLogListener(OnLogListener onLogListener) {
            this.onLogListener = onLogListener;
        }

        @Override
        public void publish(LogRecord record) {
            onLogListener.onLog(record);
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {
        }
    }

    public static class GlobalLoggingFormatter extends Formatter {

        public OnPrintListener onPrintListener;

        public GlobalLoggingFormatter(OnPrintListener onPrintListener) {
            this.onPrintListener = onPrintListener;
        }

        @Override
        public String format(LogRecord record) {
            String builder = "[" + record.getLevel() + "] " + record.getSourceClassName() + "." + record.getSourceMethodName() + " " + "\n" + this.formatMessage(record) + System.lineSeparator();
            onPrintListener.onPrint(builder);
            return builder;
        }

        public void setOnPrintListener(OnPrintListener onPrintListener) {
            this.onPrintListener = onPrintListener;
        }
    }

}
