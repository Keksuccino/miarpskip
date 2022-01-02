package de.keksuccino.miarpskip;

import de.keksuccino.miarpskip.libs.config.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class MIARPSkip implements ModInitializer {

    public static final File MOD_DIRECTORY = new File("config/miarpskip");
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogManager.getLogger("miarpskip");

    public static Config config;

    @Override
    public void onInitialize() {

        try {

            if (!MOD_DIRECTORY.isDirectory()) {
                MOD_DIRECTORY.mkdirs();
            }

            updateConfig();

            PackPromptSkipHandler.init();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateConfig() {

        try {

            config = new Config(MOD_DIRECTORY.getPath() + "/config.cfg");

            config.registerValue("ip_list_url", "http://somewebsite.example.com/list.txt", "The IPs in this list will have the resource pack prompt skipped. 1 IP per line.");

            config.clearUnusedValues();

            config.syncConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
