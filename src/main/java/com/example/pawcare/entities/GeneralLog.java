package com.example.pawcare.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLog {
    private LocalDateTime event_time;
    private String user_host;
    private long thread_id;
    private String server_id;
    private String command_type;
    private String argument;
}
