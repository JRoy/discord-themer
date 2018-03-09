package io.github.wheezygold7931.discordthemer;

import java.util.HashMap;

public class ThemeToken {

    private final String themeName;

    /**
     * Key: Role ID
     * Value: Desired Role Name
     */
    private HashMap<String, String> themeData = new HashMap<>();
    private HashMap<String, String> themeMetaData = new HashMap<>();

    private String themeDisplayName;
    private String serverTitle;
    private String serverIconName;
    private String botNickname;

    /**
     * @param themeName The internal theme name. This is also the file name and what will be used to switch themes.
     */
    public ThemeToken(String themeName) {
        this.themeName = themeName;
    }

    protected void addData(String key, String value) {
        themeData.put(key, value);
    }

    protected void addMetaData(String key, String value) {
        themeMetaData.put(key, value);
    }

    protected ThemeToken finalizeToken() {
        themeDisplayName = themeMetaData.get("name");
        serverTitle = themeMetaData.get("title");
        serverIconName = themeMetaData.get("icon");
        botNickname = themeMetaData.get("nickname");
        return this;
    }

    public HashMap<String, String> getThemeRoleData() {
        return themeData;
    }

    protected String getThemeName() {
        return themeName;
    }

    public String getThemeDisplayName() {
        return themeDisplayName;
    }

    public String getServerTitle() {
        return serverTitle;
    }

    public String getServerIconName() {
        return serverIconName;
    }

    public String getBotNickname() {
        return botNickname;
    }
}
