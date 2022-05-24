package com.cs.test.demo.service;

import com.cs.test.demo.entity.Event;
import com.cs.test.demo.models.EventLog;
import com.cs.test.demo.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
@EnableAsync
@Slf4j
public class EventLogService {

    @Autowired
    EventRepository repo;

    Event event = new Event();

    ConcurrentHashMap<String, EventLog>  map = new ConcurrentHashMap<>();

    @Async
    public Future<Event> processLogs(EventLog secondLog){
        log.debug("Async Process");
        Event savedEvent = null;
        EventLog firstLog = map.put(secondLog.getId(), secondLog);
        if(Objects.nonNull(firstLog)){
            savedEvent = this.saveEvent(secondLog, Math.abs(firstLog.getEventTime() - secondLog.getEventTime()));
            log.debug("Saved Event ID {}", savedEvent.getId());
        }
        return new AsyncResult<Event>(Optional.ofNullable(savedEvent).orElse(new Event()));
    }

    /*
    * Only 50% cases will require to save the data in DB.
    */
    public synchronized Event saveEvent(EventLog log, long duration){
        event.setId(log.getId());
        event.setHost(log.getHost());
        event.setDuration(duration);
        return repo.save(event);
    }

    public List<Event> findAll(){
        List<Event> target = new ArrayList<>();
        repo.findAll().forEach(target::add);
        return Optional.ofNullable(target).orElse(Collections.emptyList());
    }
}
