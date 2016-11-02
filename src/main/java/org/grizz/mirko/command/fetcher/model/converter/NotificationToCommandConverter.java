package org.grizz.mirko.command.fetcher.model.converter;

import org.grizz.mirko.command.fetcher.exception.IllegalTypeException;
import org.grizz.mirko.command.fetcher.model.Notification;
import org.grizz.mirko.command.fetcher.model.PlayerCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class NotificationToCommandConverter implements Converter<Notification, PlayerCommand> {
    @Override
    public PlayerCommand convert(Notification notification) {
        switch (notification.getType()) {
            case "pm": return forPrivateMessage(notification);
            default: throw new IllegalTypeException(notification.getType());
        }
    }

    private PlayerCommand forPrivateMessage(Notification notification) {
        PlayerCommand command = PlayerCommand.builder()
                .player(notification.getAuthor())
                .command(notification.getBody())
                .timestamp(notification.getDate().getTime())
                .mirkoType(notification.getType())
                .mirkoId(notification.getId())
                .processed(false)
                .build();
        return command;
    }
}
