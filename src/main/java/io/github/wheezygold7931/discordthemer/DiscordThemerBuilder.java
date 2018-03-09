package io.github.wheezygold7931.discordthemer;

import io.github.wheezygold7931.discordthemer.util.DiscordThemerLogger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.io.File;

/**
 * The builder for a Discord-Themer instance.
 */
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


    public DiscordThemerBuilder(JDA jda) throws IllegalArgumentException {
        if (jda == null)
            throw new IllegalArgumentException("JDA cannot be null");
        this.jda = jda;
    }

    public DiscordThemerBuilder setGuild(String guildId) throws IllegalArgumentException {
        Guild guild = jda.getGuildById(guildId);
        if (guild != null) {
            this.guild = guild;
            return this;
        }
        throw new IllegalArgumentException("Invalid Guild-ID Provided (Cannot Be Found)");
    }


    public DiscordThemerBuilder setThemeFolder(String path) throws IllegalArgumentException {
        File file = new File(path);

        if (file.exists() && file.isDirectory()) {
            this.file = file;
            return this;
        }

        throw new IllegalArgumentException("Invalid Theme Directory");
    }

    private DiscordThemerBuilder setLogPrefix(String prefix) {
        this.logPrefix = prefix;
        return this;
    }

    public DiscordThemerBuilder setLogDisplayErrors(boolean logDisplayingErrors) {
        this.logDisplayingErrors = logDisplayingErrors;
        return this;
    }

    public DiscordThemerBuilder setLogDisplayInfo(boolean logDisplayingInfo) {
        this.logDisplayingInfo = logDisplayingInfo;
        return this;
    }

    public DiscordThemerBuilder setLogDisplayWarnings(boolean logDisplayingWarnings) {
        this.logDisplayingWarnings = logDisplayingWarnings;
        return this;
    }

    public DiscordThemerBuilder setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    public DiscordThemerBuilder setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
        return this;
    }

    public DiscordThemer build() {
        if (guild != null && file != null)
            return new DiscordThemer(jda, guild, file, actionMode, new DiscordThemerLogger(logPrefix, debugMode, logDisplayingInfo, logDisplayingWarnings, logDisplayingErrors));
        throw new IllegalStateException("All values must be set");
    }

}
