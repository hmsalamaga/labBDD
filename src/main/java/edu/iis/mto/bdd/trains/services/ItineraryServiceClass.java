package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryServiceClass implements ItineraryService {

    private TimetableService timetableService;
    private final int MINUTES = 15;

    public ItineraryServiceClass(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime time) {
        List<Line> lines = timetableService.findLinesThrough(departure,destination);
        List<LocalTime> results = new ArrayList<>();

        List<LocalTime> departures;
        for (Line line : lines) {
            departures = timetableService.findArrivalTimes(line,departure);

            for (LocalTime localTime : departures) {
                // jesli odjazd jest pozniej niz godzina o ktorej chce podrozowac
                if(localTime.isAfter(time)){
                    //jesli odjazd jest w ciagu 15 minut
                    if(localTime.minusMinutes(MINUTES).isBefore(time)){
                        results.add(localTime); //dodaj odjazd do zbioru szukanych odjazdow
                    }
                }
            }

        }
        return results;
    }
}
