package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;

import java.util.List;

public interface ItineraryService {
    int MAX_TIME = 30;
    List<LocalTime> findNextDepartures(String from, String to, LocalTime time);
}
