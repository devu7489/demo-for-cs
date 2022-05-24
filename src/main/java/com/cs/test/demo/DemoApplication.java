package com.cs.test.demo;

import com.cs.test.demo.entity.Event;
import com.cs.test.demo.models.EventLog;
import com.cs.test.demo.service.EventLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Future;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting the process");
        long startTime = System.currentTimeMillis();
        InputStream is;
        Scanner sc;
        EventLog log;
        ObjectMapper mapper;
        EventLogService service = applicationContext.getBean(EventLogService.class);
        Collection<Future<Event>> futureResult = new ArrayList<>();;

        try {
            is = getClass().getClassLoader().getResourceAsStream("logfile.txt");
            sc = new Scanner(is, StandardCharsets.UTF_8.name());
            mapper = new ObjectMapper();

            while (sc.hasNextLine()) {
                log = mapper.readValue(sc.nextLine(), EventLog.class);
                futureResult.add(service.processLogs(log));
            }

            /*
            To check if all thread are done
             */
            futureResult.forEach(result -> {
                result.isDone();
            });
            long endTime = System.currentTimeMillis();
            // Performance can be checked by commenting @Async is service class

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

}
