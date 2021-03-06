package red.mohist.down;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import red.mohist.Mohist;
import red.mohist.configuration.MohistConfigUtil;
import red.mohist.util.IOUtil;
import red.mohist.util.i18n.Message;

public class Update {

    public static void hasLatestVersion() {
        String str = "https://api.github.com/repos/Mohist-Community/Mohist/commits";
        String ver = "https://raw.githubusercontent.com/Mohist-Community/Mohist/1.12.2/mohist.ver";
        try {
            System.out.println(Message.getString("update.check"));
            System.out.println(Message.getString("update.stopcheck"));
            URL url = new URL(str);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            String commits = IOUtil.readContent(is, "UTF-8");
            String sha = "\"sha\":\"";
            String date = "\"date\":\"";

            String s0 = commits.substring(commits.indexOf(sha));
            String s1 = s0.substring(s0.indexOf(sha) + 7);
            String s2 = s1.substring(0, 7);

            String oldver = Update.class.getPackage().getImplementationVersion();
            String time = commits.substring(commits.indexOf(date));
            String time1 = time.substring(time.indexOf(date) + 8);
            String time2 = time1.substring(0, 20);

            String newversion = MohistConfigUtil.getUrlString(ver, Mohist.VERSION);
            String oldversion = Mohist.VERSION;
            if (oldver.contains(s2)) {
                System.out.println(Message.getFormatString("update.latest", new Object[]{oldversion, s2, oldver}));
            } else {
                System.out.println(Message.getFormatString("update.old", new Object[]{oldversion, newversion, s2, time2, oldver}));
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isCheckVersion() {
        return MohistConfigUtil.getBoolean("check_update:", true);
    }
}
