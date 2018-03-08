package io.github.wheezygold7931;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;

public class DiscordThemer extends ListenerAdapter {

    private final JDA jda;
    private final Guild guild;
    private final File themeDir;
    private final boolean isDebug;
    private final ActionMode actionMode;

    protected DiscordThemer(JDA jda, Guild guild, File themeDir, boolean isDebug, ActionMode actionMode) {
        this.jda = jda;
        this.guild = guild;
        this.themeDir = themeDir;
        this.isDebug = isDebug;
        this.actionMode = actionMode;
    }



}
