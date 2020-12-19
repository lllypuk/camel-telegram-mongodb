package ru.shatrov;

import com.mongodb.client.MongoClients;
import org.apache.camel.main.Main;
import ru.shatrov.routes.MongoInsertRouteBuilder;
import ru.shatrov.routes.TelegramRouteBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created on 19.12.2020.
 *
 * @author Shatrov Aleksandr
 */
public final class App {

    public static final String AUTHORIZATION_TOKEN;

    static {
        Properties properties = new Properties();
        ClassLoader loader = App.class.getClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AUTHORIZATION_TOKEN = properties.getProperty("authorizationToken");
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    private void start() throws Exception {
        Main main = new Main();
        main.bind("telegram", MongoClients.create("mongodb://localhost:27017"));

//        main.configure().addRoutesBuilder(new TelegramRouteBuilder());
        main.configure().addRoutesBuilder(new MongoInsertRouteBuilder());

        System.out.println("Starting Camel. Use CTRL + C to terminate the process.\n");
        main.run();
    }

    private App() {
    }
}
