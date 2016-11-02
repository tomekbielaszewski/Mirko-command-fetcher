package org.grizz.mirko.command.fetcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PrivateMessage {
    private String author;
    private String body;
    private String direction;
    private String status;
    private Date date;
}
