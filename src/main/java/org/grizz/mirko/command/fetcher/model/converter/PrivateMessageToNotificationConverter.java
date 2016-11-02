package org.grizz.mirko.command.fetcher.model.converter;

import org.grizz.mirko.command.fetcher.model.Notification;
import org.grizz.mirko.command.fetcher.model.PrivateMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class PrivateMessageToNotificationConverter implements Converter<PrivateMessage, Notification> {
    @Override
    public Notification convert(PrivateMessage privateMessage) {
        Notification notification = Notification.builder()
                .author(privateMessage.getAuthor())
                .body(privateMessage.getBody())
                .date(privateMessage.getDate())
                .type("pm")
                .build();
        return notification;
    }
}
