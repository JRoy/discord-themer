package io.github.wheezygold7931.discordthemer.util;

public enum ParserVersion {

    V1("1");

    private String version;
    public static ParserVersion currentVersion = V1;

    ParserVersion(String version) {
        this.version = version;
    }

    public String getVersionString() {
        return version;
    }

    public static boolean isVersion(String versionString) {
        for (ParserVersion parserVersion : ParserVersion.values()) {
            if (parserVersion.getVersionString().equalsIgnoreCase(versionString)) return true;
        }
        return false;
    }

    public static ParserVersion getFromVersionString(String version) {
        for (ParserVersion parserVersion : ParserVersion.values()) {
            if (parserVersion.getVersionString().equalsIgnoreCase(version)) return parserVersion;
        }
        return currentVersion;
    }

}
