package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;

import java.util.List;

public interface ItineraryService {
    int MAX_TRAVEL_TIME = 30;

    List<LocalTime> findNextDepartures(String origin, String destination, LocalTime time);
}
