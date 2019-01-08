package share.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ShareData {

    public Properties properties = new Properties();

    public String user, password, jsonPath;

    public ShareData() throws IOException {

        properties.load(new FileInputStream("testData.properties"));
        this.load();
    }

    public void load() {
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        jsonPath = properties.getProperty("jsonPath");
    }
}

