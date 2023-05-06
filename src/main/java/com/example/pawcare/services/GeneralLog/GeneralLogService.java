package com.example.pawcare.services.GeneralLog;

import com.example.pawcare.entities.GeneralLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GeneralLogService {

    private final JdbcTemplate jdbcTemplate;

    public GeneralLogService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAllGeneralLogsToCsv(String filePath) throws IOException {
        String sql = "SELECT * FROM mysql.general_log ORDER BY event_time DESC;";
        List<GeneralLog> generalLogs = jdbcTemplate.query(sql, (resultSet, i) -> {
            GeneralLog generalLog = new GeneralLog();
            generalLog.setEvent_time(resultSet.getObject("event_time", LocalDateTime.class));
            generalLog.setUser_host(resultSet.getString("user_host"));
            generalLog.setThread_id(resultSet.getLong("thread_id"));
            generalLog.setServer_id(resultSet.getString("server_id"));
            generalLog.setCommand_type(resultSet.getString("command_type"));
            generalLog.setArgument(resultSet.getString("argument"));
            return generalLog;
        });

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("event_id,event_time,user_host,thread_id,server_id,command_type,argument");
            for (GeneralLog generalLog : generalLogs) {
                writer.println(String.format("%s,%s,%d,%s,%s,%s",
                        generalLog.getEvent_time(),
                        generalLog.getUser_host(),
                        generalLog.getThread_id(),
                        generalLog.getServer_id(),
                        generalLog.getCommand_type(),
                        generalLog.getArgument()));
            }
        }
    }
}


