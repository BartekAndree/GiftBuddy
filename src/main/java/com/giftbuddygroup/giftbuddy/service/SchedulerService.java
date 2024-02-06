package com.giftbuddygroup.giftbuddy.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final EventService eventService;

    @Scheduled(cron = "0 1 0 * * ?") // every day at 00:01
    public void checkAndUpdateEventStatus() {
        eventService.checkAndUpdateEventStatus();
    }
}
