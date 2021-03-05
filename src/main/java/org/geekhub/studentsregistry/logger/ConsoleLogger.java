package org.geekhub.studentsregistry.logger;

public class ConsoleLogger {

    private final String loggingClass;

    public ConsoleLogger(String loggingClass) {
        this.loggingClass = loggingClass;
    }

    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    public void warning(String message, Throwable error) {
        System.out.println(loggingClass);
        System.out.println("WARNING: " + message);
        System.out.println(error.getLocalizedMessage() + '\n');
    }

    public void error(String message, Throwable error) {
        System.out.println(loggingClass);
        System.out.println("ERROR: " + message);
        error.printStackTrace(System.out);
    }

}

