package de.keksuccino.miarpskip;

import de.keksuccino.miarpskip.libs.file.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class PackPromptSkipHandler {

    private static volatile List<String> ips = new ArrayList<>();

    public static void init() {
        new Thread(() -> {
            try {

                String url = MIARPSkip.config.getOrDefault("ip_list_url", "");
                if (!url.equals("") && !url.equals("http://somewebsite.example.com/list.txt")) {
                    ips = downloadIpListFrom(url);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static boolean skipForIp(String ip) {
        return ips.contains(ip);
    }

    public static List<String> downloadIpListFrom(String url) {
        List<String> l = new ArrayList<>();

        try {

            File saveDir = new File(MIARPSkip.MOD_DIRECTORY.getPath() + "/cache");
            saveDir.mkdirs();

            File f = new File(saveDir.getPath() + "/iplist.txt");

            InputStream in = new URL(url).openStream();
            java.nio.file.Files.copy(in, Paths.get(f.toURI()), StandardCopyOption.REPLACE_EXISTING);

            if (f.isFile()) {
                l = FileUtils.getFileLines(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;
    }

}
