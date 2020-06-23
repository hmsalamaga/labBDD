package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;

import java.util.List;

public class InMemoryItineraryService implements ItineraryService {

    @Override
    public List<LocalTime> findNextDepartures(String origin, String destination, LocalTime time) {
        return null;
    }
}
