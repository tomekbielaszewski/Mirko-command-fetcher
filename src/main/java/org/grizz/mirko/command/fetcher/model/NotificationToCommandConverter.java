package org.grizz.mirko.command.fetcher.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class NotificationToCommandConverter implements Converter<Notification, PlayerCommand> {
    @Override
    public PlayerCommand convert(Notification notification) {
        return new PlayerCommand();
    }
}
