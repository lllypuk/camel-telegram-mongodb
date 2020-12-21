package ru.shatrov.routes;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;
import ru.shatrov.App;

/**
 * Created on 19.12.2020.
 *
 * @author Shatrov Aleksandr
 */
public class MongoInsertRouteBuilder extends RouteBuilder {

    private static final Processor telegramProcessor = exchange -> {
        IncomingMessage incomingMessage = (IncomingMessage) exchange.getIn().getBody();

        incomingMessage.setText("Сообщение пользователя:\n\n" + incomingMessage.getText());

        exchange.getIn().setBody(incomingMessage);
    };

    @Override
    public void configure() {
        from("telegram:bots/?authorizationToken=" + App.AUTHORIZATION_TOKEN)
                .process(telegramProcessor)
                .to("mongodb:telegram?database=telegram&collection=chat_message&operation=insert")
                .setBody(simple("${body}"))
                .to("log:INFO")
                .setBody(simple("Ваше сообщение сохранено"))
                .to("telegram:bots/?authorizationToken=" + App.AUTHORIZATION_TOKEN);
    }
}
