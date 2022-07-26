package com.eventcafecloud.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailTO {
    private String address;
    private String title;
    private String message;
}
