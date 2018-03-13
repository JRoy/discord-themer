package io.github.wheezygold7931.discordthemer;

import io.github.wheezygold7931.discordthemer.exceptions.ThemeNotFoundException;
import io.github.wheezygold7931.discordthemer.util.DiscordThemerLogger;
import io.github.wheezygold7931.discordthemer.util.RunRestAction;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class DiscordThemer extends ListenerAdapter {

    private final JDA jda;
    private final Guild guild;
    private final File themeDir;
    private final ActionMode actionMode;
    private final DiscordThemerLogger logger;

    private HashMap<String, ThemeToken> themeMap = new HashMap<>();

    protected DiscordThemer(JDA jda, Guild guild, File themeDir, ActionMode actionMode, DiscordThemerLogger discordThemerLogger) {
        this.jda = jda;
        this.guild = guild;
        this.themeDir = themeDir;
        this.actionMode = actionMode;
        this.logger = discordThemerLogger;
        logger.info("Discord-Themer Initialized!");
        processThemes();
    }

    private void processThemes() {
        File[] rawThemes = themeDir.listFiles();

        logger.info("Loading and Parsing Themes...");

        if (rawThemes == null || rawThemes.length == 0) {
            logger.error("No themes are in the theme directory!");
            return;
        }

        for (File theme : rawThemes) {
            if (!theme.isDirectory()) {
                if (theme.getName().endsWith(".dat")) {
                    logger.debug("Sending file to parser: " + theme.getName());
                    if (validateTheme(theme)) {
                        parseTheme(theme);
                    } else {
                        logger.debug("Theme exited validator with an invalid status!");
                    }
                }
            }
        }

        logger.info("All themes have been loaded and parsed!");
        logger.debug("Loaded Themes:");
        for (HashMap.Entry<String, ThemeToken> entry : themeMap.entrySet()) {
            ThemeToken token = entry.getValue();
            logger.debug("    - " + token.getThemeName() + " (" + token.getThemeDisplayName() + ")");
        }
    }

    /**
     *
     * The parser is basically blind, it does not know if the right amount of tokens per line, etc.
     * This method makes sure the parser will not throw an {@link IndexOutOfBoundsException} or causes other issues as the parser will break if a PERFECT theme file is not given to it.
     *
     * @param file The theme file in question.
     * @return Returns true if the theme file is okay for the parser.
     */
    private boolean validateTheme(File file) {
        String fileName = file.getName(); //Has file extension
        String filePath = file.getPath();

        logger.debug("Validating Theme " + fileName);
        logger.debug("File Path for " + fileName + " is " + filePath);

        Scanner validateScanner;
        try {
            validateScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            logger.error("File Mismatch! Did the GC steal the file?");
            return false;
        }

        HashMap<String, String> metaTokens = new HashMap<>();
        int roleCount = 0;
        while (validateScanner.hasNextLine()) {
            String curLine = validateScanner.nextLine();

            if (curLine.startsWith("/") || curLine.startsWith("#"))
                continue; // Ignore comments as they will not be used

            if (curLine.isEmpty())
                continue; //User has decided they like increased parsed times...

            String[] lineTokens = curLine.split("[:]");
            if (lineTokens[0].equalsIgnoreCase("MetaData")) {
                if (lineTokens.length != 3) {
                    logger.error("Unparseable line in \"" + fileName + "\": " + curLine);
                    return false;
                }
                metaTokens.put(lineTokens[1], lineTokens[2]);
            } else {
                if (lineTokens.length != 2) {
                    logger.error("Unparseable line in \"" + fileName + "\": " + curLine);
                    return false;
                }
                if (guild.getRoleById(lineTokens[0]) == null) {
                    logger.warn("Invalid Role ID: " + lineTokens[0] + "! This will not be parsed.");
                    continue;
                }
                roleCount++;
            }
        }

        if (roleCount==0) logger.warn("There were no valid roles detected! The server will only be themed with MetaData.");

        if (metaTokens.containsKey("title") && metaTokens.containsKey("icon") && metaTokens.containsKey("nickname") && metaTokens.containsKey("name")) {
            File image = new File(filePath.substring(0, filePath.lastIndexOf('\\')) + "\\" + metaTokens.get("icon") + ".png");

            if (!image.exists() || image.isDirectory()) {
                logger.error("Invalid Image File: " + metaTokens.get("icon") + ".png");
                logger.error(" ^ If you were trying to specify another directory, start the metadata value with a slash!");
                return false;
            }
            return true;
        }

        logger.error("Too little MetaData! Did you use them all?");
        return false;
    }

    /**
     * The parser reads a theme file from the theme validator and parses it into a {@link ThemeToken}
     * (Note: The parser is extremely stupid, the validator is where the smarts are at. I cannot stress this enough if you're modifying this, run your files through {@link DiscordThemer#validateTheme(File)} FIRST! This method WILL hard fail!)
     * @param file The theme file to be parsed.
     */
    private void parseTheme(File file) {
        String themeName = file.getName().substring(0, file.getName().lastIndexOf('.')); //Remove the file extension from the file

        logger.debug("Parsing Theme " + file.getName() + "!");

        ThemeToken token = new ThemeToken(themeName);

        Scanner dataScanner;
        try {
            dataScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            logger.error("File Mismatch! Did the GC steal the file?");
            return;
        }

        while (dataScanner.hasNextLine()) {
            String curLine = dataScanner.nextLine();
            String[] lineTokens = curLine.split("[:]");

            if (curLine.startsWith("/") || curLine.startsWith("#"))
                continue; //Do not parse comments (Slash, Double-Slash, Hash-Sign)

            if (curLine.isEmpty())
                continue; //User has decided they like increased parsed times...

            if (lineTokens[0].equalsIgnoreCase("MetaData")) {
                token.addMetaData(lineTokens[1], lineTokens[2]);
            } else {
                if (guild.getRoleById(lineTokens[0]) == null) {
                    logger.error("Unparseable Role: " + curLine + " (Invalid Role ID)");
                    continue; //Invalid role ids will cause exceptions from JDA.
                }
                token.addData(lineTokens[0], lineTokens[1]);
            }
        }

        themeMap.put(themeName, token.finalizeToken());

    }

    public boolean isValidTheme(String themeName) {
        return themeMap.containsKey(themeName);
    }

    public void setServerTheme(String themeName) throws ThemeNotFoundException {
        if (!themeMap.containsKey(themeName)) {
            throw new ThemeNotFoundException("Invalid or Unparsed Theme-File: " + themeName + "!");
        }

        ThemeToken token = themeMap.get(themeName);

        logger.info("Switching to Theme: " + token.getThemeDisplayName());

        try {
            new RunRestAction(guild.getManager().setIcon(Icon.from(new File(themeDir.getPath() + "\\" + token.getServerIconName() + ".png"))), actionMode);
        } catch (IOException e) {
            logger.error("Your icon file is invalid!");
            e.printStackTrace();
        } finally {
            new RunRestAction(guild.getManager().setName(token.getServerTitle()), actionMode);
            new RunRestAction(guild.getController().setNickname(guild.getMemberById(jda.getSelfUser().getId()), token.getBotNickname()), actionMode);
            for (HashMap.Entry<String, String> entry : token.getThemeRoleData().entrySet()) {
                new RunRestAction(guild.getRoleById(entry.getKey()).getManager().setName(entry.getValue()), actionMode);
            }
            logger.info("Server's Theme has been Updated!");
        }

    }

}
