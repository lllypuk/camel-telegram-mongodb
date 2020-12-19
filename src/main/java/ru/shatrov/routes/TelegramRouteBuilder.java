package ru.shatrov.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import ru.shatrov.App;

/**
 * Created on 19.12.2020.
 *
 * @author Shatrov Aleksandr
 */
public class TelegramRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        fromF("telegram:bots/?authorizationToken=%s", App.AUTHORIZATION_TOKEN)
                .to("log:INFO")
                .process(exchange -> {
                    IncomingMessage incomingMessage = (IncomingMessage) exchange.getIn().getBody();

                    OutgoingTextMessage outgoingTextMessage = new OutgoingTextMessage();
                    outgoingTextMessage.setText("Ваше сообщение:\n\n" + incomingMessage.getText());

                    exchange.getIn().setBody(outgoingTextMessage);
                })
                .toF("telegram:bots/?authorizationToken=%s", App.AUTHORIZATION_TOKEN);
    }
}
