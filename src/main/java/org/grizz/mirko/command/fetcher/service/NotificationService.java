package org.grizz.mirko.command.fetcher.service;

import com.crozin.wykop.sdk.Command;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.grizz.mirko.command.fetcher.model.Counter;
import org.grizz.mirko.command.fetcher.model.Notification;
import org.grizz.mirko.command.fetcher.model.PrivateMessage;
import org.grizz.mirko.command.fetcher.model.WykopNotification;
import org.grizz.mirko.command.fetcher.model.converter.PrivateMessageToNotificationConverter;
import org.grizz.mirko.command.fetcher.sesssion.RefreshableSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private RefreshableSession session;
    @Autowired
    private PrivateMessageToNotificationConverter privateMessageConverter;

    public List<Notification> download() {
        ArrayList<Notification> notifications = Lists.newArrayList();

        if (hasNewNotifications()) {
            List<WykopNotification> newPrivateMessagesNotifications = getNewPrivateMessageNotifications();
            List<Notification> newNotifications = newPrivateMessagesNotifications.stream()
                    .map(notification -> getPrivateMessages(notification))
                    .flatMap(messages -> messages.stream())
                    .filter(message -> "received".equals(message.getDirection()))
                    .filter(message -> "new".equals(message.getStatus()))
                    .map(message -> privateMessageConverter.convert(message))
                    .collect(Collectors.toList());
            log.info("New notifications found! Amount: {}", newNotifications.size());
            notifications.addAll(newNotifications);
        }

        return notifications;
    }

    private List<PrivateMessage> getPrivateMessages(WykopNotification notification) {
        Gson gson = new Gson();
        String json = session.execute(conversationCommand(notification.getAuthor()));
        PrivateMessage[] privateMessages = gson.fromJson(json, PrivateMessage[].class);
        return Lists.newArrayList(privateMessages);
    }

    private Command conversationCommand(String author) {
        return new Command("pm", "conversation", author);
    }

    private List<WykopNotification> getNewPrivateMessageNotifications() {
        return getWykopNotifications().stream()
                .filter(wn -> wn.isUnread())
                .filter(wn -> "pm".equals(wn.getType()))
                .collect(Collectors.toList());
    }

    private List<WykopNotification> getWykopNotifications() {
        Gson gson = new Gson();
        String json = session.execute(notificationCommand());
        WykopNotification[] notifications = gson.fromJson(json, WykopNotification[].class);
        return Lists.newArrayList(notifications);
    }

    private Command notificationCommand() {
        return new Command("Mywykop", "Notifications");
    }

    private boolean hasNewNotifications() {
        return getNotificationCount() > 0;
    }

    private int getNotificationCount() {
        Gson gson = new Gson();
        String json = session.execute(notificationCountCommand());
        Counter counter = gson.fromJson(json, Counter.class);
        return counter.getCount();
    }

    private Command notificationCountCommand() {
        return new Command("Mywykop", "NotificationsCount");
    }
}
