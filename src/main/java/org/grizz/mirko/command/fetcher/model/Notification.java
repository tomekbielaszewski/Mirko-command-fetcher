package org.grizz.mirko.command.fetcher.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Notification {
    private String id;
    private String author;
    private String body;
    private String type;
    private Date date;
}
