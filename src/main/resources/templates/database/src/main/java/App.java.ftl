package ${package};

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.apache.ibatis.jdbc.ScriptRunner;

public class App {

    private static Connection connection;

    private static void runScripts(String scriptsPath) throws Exception {

        ScriptRunner sr = new ScriptRunner(connection);
        sr.setStopOnError(true);
        sr.setSendFullScript(true);

        if (scriptsPath != null) {
            File scripts = new File(scriptsPath);
            if (scripts.exists()) {
                if (scripts.isDirectory()) {
                    for (File file : scripts.listFiles()) {
                        App.runScripts(file.getPath());
                    }
                } else {
                    sr.runScript(new FileReader(scriptsPath));
                }
            }
        }
    }

    /**
     * App main function.
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("url");
            connection = DriverManager.getConnection(url, prop);

            System.out.println("Connection established......");

            for (String command : prop.getProperty("deploymentScriptCommands").split("\\|")) {
                App.runScripts(prop.getProperty("projectPath") + prop.getProperty(command));
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
        }
    }
}
