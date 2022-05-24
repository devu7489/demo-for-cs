package com.cs.test.demo;

import com.cs.test.demo.entity.Event;
import com.cs.test.demo.enums.State;
import com.cs.test.demo.models.EventLog;
import com.cs.test.demo.repository.EventRepository;
import com.cs.test.demo.service.EventLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	EventLogService service;

	@Mock
	EventRepository repo;

	@Test
	public void whenSameEventPassed_thenAlertIsGenerated() throws ExecutionException, InterruptedException {
		EventLog log1 = new EventLog();
		log1.setId("1");
		log1.setState(State.STARTED);
		log1.setEventTime(10);

		EventLog log2 = new EventLog();
		log2.setId("1");
		log2.setState(State.STARTED);
		log2.setEventTime(13);

		Event event = new Event();
		long duration = log2.getEventTime() - log1.getEventTime();
		Mockito.when(repo.save(new Event(log2.getId(), duration, ""))).thenReturn(new Event("1",3,""));

		service.processLogs(log1);
		Future<Event> future2 = service.processLogs(log1);
		future2.isDone();
		assertThat(future2.get().getId()).isEqualTo("1");
	}
}
