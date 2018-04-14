package io.github.wheezygold7931.discordthemer;

import io.github.wheezygold7931.discordthemer.util.ParserVersion;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class ThemeToken {

    private final String themeName;

    /**
     * Key: Role ID
     * Value: Desired Role Name
     */
    private HashMap<String, String> themeData = new HashMap<>();
    private HashMap<String, String> themeMetaData = new HashMap<>();

    private String themeDisplayName;
    @Nullable
    private String serverTitle;
    @Nullable
    private String serverIconName;
    @Nullable
    private String botNickname;
    @Nullable
    private String botIconName;
    private ParserVersion parserVersion;

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
        botIconName = themeMetaData.get("avatar");
        parserVersion = ParserVersion.getFromVersionString(themeMetaData.get("parser"));
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

    @CheckForNull
    public String getServerTitle() {
        return serverTitle;
    }

    @CheckForNull
    public String getServerIconName() {
        return serverIconName;
    }

    @CheckForNull
    public String getBotNickname() {
        return botNickname;
    }

    @CheckForNull
    public String getBotIconName() {
        return botIconName;
    }

    public ParserVersion getParserVersion() {
        return parserVersion;
    }
}
