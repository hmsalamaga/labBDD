package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;
import java.util.List;

public interface ItineraryService {
    int DEFAULT_PERIOD_TIME = 30;

    List<LocalTime> findNextDepartures(String departure, String destination, LocalTime startTime);
}
