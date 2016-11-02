package org.grizz.mirko.command.fetcher.service;

import com.crozin.wykop.sdk.Command;
import com.google.common.collect.Lists;
import org.grizz.mirko.command.fetcher.model.Notification;
import org.grizz.mirko.command.fetcher.sesssion.RefreshableSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private RefreshableSession session;

    public List<Notification> download() {
        ArrayList<Notification> notifications = Lists.newArrayList();
        session.startNew();

        //TODO remove...
        boolean flag = true;
        while (flag) {
            session.execute(new Command("stream", "index", new String[]{"page", "1"}));
        }
        // ...till here

        return notifications;
    }

    private boolean hasNewNotifications() {
        return false;
    }
}
