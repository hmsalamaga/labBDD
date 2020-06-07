package edu.iis.mto.bdd.trains.services;

import java.time.LocalTime;
import java.util.List;

public interface ItineraryService {
        List<LocalTime> findNextDepartures(String departure, String destination, LocalTime time);
}
