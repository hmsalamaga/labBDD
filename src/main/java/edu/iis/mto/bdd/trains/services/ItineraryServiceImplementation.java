package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;
import java.util.List;

public class ItineraryServiceImplementation implements ItineraryService {
    private TimetableService timetableService;

    public ItineraryServiceImplementation(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime time) {
        return null;
    }
}
