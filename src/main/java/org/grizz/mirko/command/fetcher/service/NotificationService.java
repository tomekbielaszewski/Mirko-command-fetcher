package org.grizz.mirko.command.fetcher.service;

import org.grizz.mirko.command.fetcher.model.Notification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    public List<Notification> download() {
        return new ArrayList<>();
    }
}
