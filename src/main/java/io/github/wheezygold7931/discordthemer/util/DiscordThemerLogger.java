package io.github.wheezygold7931.discordthemer.util;

public class DiscordThemerLogger {

    private final boolean isDebug;
    private final boolean isDisplayingInfo;
    private final boolean isDisplayingWarnings;
    private final boolean isDisplayingErrors;

    private final String prefix;

    public DiscordThemerLogger(String prefix, boolean debugMode, boolean info, boolean warnings, boolean errors) {
        this.prefix = prefix;
        this.isDebug = debugMode;
        this.isDisplayingInfo = info;
        this.isDisplayingWarnings = warnings;
        this.isDisplayingErrors = errors;
    }

    public void info(String infoString) {
        if (isDisplayingInfo)
            log("[INFO] " + infoString);
    }

    public void warn(String warnString) {
        if (isDisplayingWarnings)
            log("[WARN] " + warnString);
    }

    public void error(String errorString) {
        if (isDisplayingErrors)
            log("[ERROR] " + errorString);
    }

    public void debug(String debugString) {
        if (isDebug)
            log("[DEBUG] " + debugString);
    }

    public void pinfo(String infoString, String themeName) {
        if (isDisplayingInfo)
            log("[INFO] [PARSER] [" + themeName + "] " + infoString);
    }

    public void pwarn(String warnString, String themeName) {
        if (isDisplayingWarnings)
            log("[WARN] [PARSER] [" + themeName + "] " + warnString);
    }

    public void perror(String errorString, String themeName) {
        if (isDisplayingErrors)
            log("[ERROR] [PARSER] [" + themeName + "] " + errorString);
    }

    public void pdebug(String debugString, String themeName) {
        if (isDebug)
            log("[DEBUG] [PARSER] [" + themeName + "] " + debugString);
    }

    private void log(String log) {
        System.out.println(prefix + " " + log);
    }

}
