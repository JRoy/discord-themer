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

    private void log(String log) {
        System.out.println(prefix + " " + log);
    }

}
