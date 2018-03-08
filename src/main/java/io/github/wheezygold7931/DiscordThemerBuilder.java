package io.github.wheezygold7931;

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
            return new DiscordThemer(jda, guild, file, debugMode, actionMode);
        throw new IllegalStateException("All values must be set");
    }

}
