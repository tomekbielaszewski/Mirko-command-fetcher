package org.grizz.mirko.command.fetcher.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class WykopNotification {
    private String id;
    private String author;
    @SerializedName("new")
    private boolean unread;
    private Date date;
    private String type;
}
