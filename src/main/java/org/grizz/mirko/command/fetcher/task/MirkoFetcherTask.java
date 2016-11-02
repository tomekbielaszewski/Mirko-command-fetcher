package org.grizz.mirko.command.fetcher.task;

import lombok.extern.slf4j.Slf4j;
import org.grizz.mirko.command.fetcher.model.Notification;
import org.grizz.mirko.command.fetcher.model.converter.NotificationToCommandConverter;
import org.grizz.mirko.command.fetcher.model.PlayerCommand;
import org.grizz.mirko.command.fetcher.model.repo.PlayerCommandRepository;
import org.grizz.mirko.command.fetcher.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MirkoFetcherTask implements Runnable {
    @Autowired
    private PlayerCommandRepository commandRepository;
    @Autowired
    private NotificationToCommandConverter converter;
    @Autowired
    private NotificationService notificationService;

    @Override
    public void run() {
        List<Notification> notifications = downloadNewNotifications();
        List<PlayerCommand> commands = convertToCommands(notifications);
        save(commands);
    }

    private List<Notification> downloadNewNotifications() {
        log.info("downloading notifications");
        return notificationService.download();
    }

    private List<PlayerCommand> convertToCommands(List<Notification> notifications) {
        log.info("converting notifications to commands");
        return notifications.stream()
                .map(n -> converter.convert(n))
                .collect(Collectors.toList());
    }

    private void save(List<PlayerCommand> commands) {
        log.info("saving {} new commands", commands.size());
        commandRepository.save(commands);
    }
}
