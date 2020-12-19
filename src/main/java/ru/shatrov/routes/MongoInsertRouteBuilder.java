package ru.shatrov.routes;

import org.apache.camel.builder.RouteBuilder;
import ru.shatrov.App;

/**
 * Created on 19.12.2020.
 *
 * @author Shatrov Aleksandr
 */
public class MongoInsertRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        fromF("telegram:bots/?authorizationToken=%s", App.AUTHORIZATION_TOKEN)
                .to("mongodb:telegram?database=telegram&collection=chat_message&operation=insert")
                .setBody(simple("${body}"))
                .to("log:INFO")
                .setBody(simple("Ваше сообщение сохранено"))
                .toF("telegram:bots/?authorizationToken=%s", App.AUTHORIZATION_TOKEN);
    }
}
