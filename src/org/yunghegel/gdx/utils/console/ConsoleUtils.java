package org.yunghegel.gdx.utils.console;

import com.badlogic.gdx.utils.reflect.Method;

import com.strongjoshua.console.annotation.HiddenCommand;

public final class ConsoleUtils {
    public static boolean canExecuteCommand (Console console, Method method) {
        return console.isExecuteHiddenCommandsEnabled() || !method.isAnnotationPresent(HiddenCommand.class);
    }

    public static boolean canDisplayCommand (Console console, Method method) {
        return console.isDisplayHiddenCommandsEnabled() || !method.isAnnotationPresent(HiddenCommand.class);
    }

    public static String exceptionToString (final Throwable throwable) {
        StringBuilder result = new StringBuilder();
        Throwable cause = throwable;

        while (cause != null) {
            if (result.length() == 0) {
                result.append("\nException in thread \"").append(Thread.currentThread().getName()).append("\" ");
            } else {
                result.append("\nCaused by: ");
            }
            result.append(cause.getClass().getCanonicalName()).append(": ").append(cause.getMessage());

            for (final StackTraceElement traceElement : cause.getStackTrace()) {
                result.append("\n\tat ").append(traceElement.toString());
            }
            cause = cause.getCause();
        }
        return result.toString();
    }

    public static String getCallerCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(ConsoleUtils.class.getName())&& ste.getClassName().indexOf("java.lang.Thread")!=0) {
                if (callerClassName==null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste.getClassName();
                }
            }
        }
        return null;
    }
    public static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(GUIConsole.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
    }

    public static String getMethodName(final int depth)
    {
        final StackTraceElement[] ste = new Throwable().getStackTrace();

        //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
        return ste[ste.length - depth].getMethodName();
    }

    public static String getCallingMethodName() {

        StackTraceElement stack = Thread.currentThread().getStackTrace()[3];

        return stack.getClassName() + "." + stack.getMethodName();

    }

    /**
     * Returns the first "[class#method(line)]: " of the first class not equal to "StackTraceUtils". <br />
     * From the Stack Trace.
     * @return "[class#method(line)]: " (never empty, first class past StackTraceUtils)
     */
    public static String getClassMethodLine(){
        return getClassMethodLine(null);
    }

    /**
     * Returns the first "[class#method(line)]: " of the first class not equal to "StackTraceUtils" and aclass. <br />
     * Allows to get past a certain class.
     * @param aclass class to get pass in the stack trace. If null, only try to get past StackTraceUtils.
     * @return "[class#method(line)]: " (never empty, because if aclass is not found, returns first class past StackTraceUtils)
     */
    public static String getClassMethodLine(final Class aclass) {
        final StackTraceElement st = getCallingStackTraceElement(aclass);
        String classname = st.getClassName();
        String trimmedClassname = classname.substring(classname.lastIndexOf('.') + 1);
        final String amsg = "[" + trimmedClassname + "." + st.getMethodName() + "()]";
        return amsg;
    }

    /**
     * Returns the first stack trace element of the first class not equal to "StackTraceUtils" or "LogUtils" and aClass. <br />
     * Stored in array of the callstack. <br />
     * Allows to get past a certain class.
     * @param aclass class to get pass in the stack trace. If null, only try to get past StackTraceUtils.
     * @return stackTraceElement (never null, because if aClass is not found, returns first class past StackTraceUtils)
     * @throws AssertionFailedException if resulting statckTrace is null (RuntimeException)
     */
    public static StackTraceElement getCallingStackTraceElement(final Class aclass) {
        final Throwable           t         = new Throwable();
        final StackTraceElement[] ste       = t.getStackTrace();
        int index = 2;
        final int limit = ste.length-2;
        StackTraceElement   st        = ste[index];
        String              className = st.getClassName();
        boolean aclassfound = aclass == null;
        StackTraceElement   resst = null;
        while(index < limit)
        {
            if(shouldExamine(className, aclass))
            {
                if(resst == null)
                {
                    resst = st;
                }
                if(aclassfound)
                {
                    final StackTraceElement ast = onClassfound(aclass, className, st);
                    if(ast != null)
                    {
                        resst = ast;
                        break;
                    }
                }
                else
                {
                    if(aclass != null && aclass.getName().equals(className))
                    {
                        aclassfound = true;
                    }
                }
            }
            index = index + 1;
            st        = ste[index];
            className = st.getClassName();
        }
        if(resst == null)
        {
            //Assert.isNotNull(resst, "stack trace should null"); //NO OTHERWISE circular dependencies
            System.err.println("StacktraceElement not found"); //$NON-NLS-1$
        }
        return resst;
    }

    static private boolean shouldExamine(String className, Class aclass)
    {
        final boolean res = !ConsoleUtils.class.getName().equals(className) && (!className.endsWith("LogFormatter"
        ) || (aclass !=null && aclass.getName().endsWith("LogUtils")));
        return res;
    }

    static private StackTraceElement onClassfound(Class aclass, String className, StackTraceElement st)
    {
        StackTraceElement   resst = null;
        if(aclass != null && !aclass.getName().equals(className))
        {
            resst = st;
        }
        if(aclass == null)
        {
            resst = st;
        }
        return resst;
    }
}