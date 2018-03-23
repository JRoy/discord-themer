package io.github.wheezygold7931.discordthemer;

import io.github.wheezygold7931.discordthemer.util.DiscordThemerLogger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.io.File;

/**
 * The builder for a Discord-Themer instance.
 */
@SuppressWarnings("WeakerAccess")
public class DiscordThemerBuilder {

    private final JDA jda;
    private Guild guild;
    private File file;

    private boolean debugMode = false;
    private ActionMode actionMode = ActionMode.QUEUE;

    private String logPrefix = "[discord-themer]";
    private boolean logDisplayingInfo = true;
    private boolean logDisplayingWarnings = false;
    private boolean logDisplayingErrors = true;


    /**
     * @param jda Your JDA Instance for the Themer
     * @throws IllegalArgumentException Throws when JDA is null.
     */
    public DiscordThemerBuilder(JDA jda) throws IllegalArgumentException {
        if (jda == null)
            throw new IllegalArgumentException("JDA cannot be null");
        this.jda = jda;
    }

    /**
     * Sets the guild for the themer.
     * @param guildId The id of the guild to be themed.
     * @throws IllegalArgumentException Throws if the guild does not exist.
     */
    public DiscordThemerBuilder setGuild(String guildId) throws IllegalArgumentException {
        Guild guild = jda.getGuildById(guildId);
        if (guild != null) {
            this.guild = guild;
            return this;
        }
        throw new IllegalArgumentException("Invalid Guild-ID Provided (Cannot Be Found)");
    }


    /**
     * Sets the folder where your theme files are stored.
     * @param path The path to the theme folder; Relative to your jar file by default.
     * @throws IllegalArgumentException Throws when the path is invalid.
     */
    public DiscordThemerBuilder setThemeFolder(String path) throws IllegalArgumentException {
        File file = new File(path);

        if (file.exists() && file.isDirectory()) {
            this.file = file;
            return this;
        }

        throw new IllegalArgumentException("Invalid Theme Directory");
    }

    /**
     * Sets the prefix for any console output.
     * @param prefix The new prefix
     */
    public DiscordThemerBuilder setLogPrefix(String prefix) {
        this.logPrefix = prefix;
        return this;
    }

    /**
     * Sets if you should receive errors in console.
     */
    public DiscordThemerBuilder setLogDisplayErrors(boolean logDisplayingErrors) {
        this.logDisplayingErrors = logDisplayingErrors;
        return this;
    }

    /**
     * Sets if you should receive info in console.
     */
    public DiscordThemerBuilder setLogDisplayInfo(boolean logDisplayingInfo) {
        this.logDisplayingInfo = logDisplayingInfo;
        return this;
    }

    /**
     * Sets if you should receive warnings in console.
     */
    public DiscordThemerBuilder setLogDisplayWarnings(boolean logDisplayingWarnings) {
        this.logDisplayingWarnings = logDisplayingWarnings;
        return this;
    }

    /**
     * Sets if you should receive debug messages in console.
     */
    public DiscordThemerBuilder setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    /**
     * Sets the action mode to use.
     * @param actionMode The target action mode.
     */
    public DiscordThemerBuilder setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
        return this;
    }

    /**
     * Builds the themer.
     * @return The built themer.
     */
    public DiscordThemer build() {
        if (guild != null && file != null)
            return new DiscordThemer(jda, guild, file, actionMode, new DiscordThemerLogger(logPrefix, debugMode, logDisplayingInfo, logDisplayingWarnings, logDisplayingErrors));
        throw new IllegalStateException("All values must be set");
    }

}
