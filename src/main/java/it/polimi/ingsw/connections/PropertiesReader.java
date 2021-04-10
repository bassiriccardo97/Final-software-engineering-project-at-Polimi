package it.polimi.ingsw.connections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class PropertiesReader {

    private InputStream inputStream;
    //private String ip;
    private String socketPort;
    private String rmiPort;
    //private String lobbyDelay;
    //private String turnTimer;

    public void setPropertiesValue(String whoose) throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config/" + whoose + "Config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            //if (whoose.equals("client")) {
            //    ip = prop.getProperty("ip");
            /*if (whoose.equals("server")){
                lobbyDelay = prop.getProperty("lobbyDelay");
                turnTimer = prop.getProperty("turnTimer");
            }*/
            socketPort = prop.getProperty("socketPort");
            rmiPort = prop.getProperty("rmiPort");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    /*public String getIp() {
        return ip;
    }*/

    public String getSocketPort() {
        return socketPort;
    }

    public String getRmiPort() {
        return rmiPort;
    }

    /*public String getLobbyDelay() {
        return lobbyDelay;
    }

    public String getTurnTimer() {
        return turnTimer;
    }

    public void setIp(String ip){
        this.ip = ip;
    }*/
}
