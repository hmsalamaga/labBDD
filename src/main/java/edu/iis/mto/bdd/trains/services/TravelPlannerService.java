package edu.iis.mto.bdd.trains.services;

import org.joda.time.LocalTime;

import java.util.List;

public interface TravelPlannerService {
    List<LocalTime> findNextDepartures(String origin, String destination, LocalTime time);
}
