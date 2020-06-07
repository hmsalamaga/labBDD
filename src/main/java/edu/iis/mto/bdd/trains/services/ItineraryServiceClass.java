package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryServiceClass implements ItineraryService {

    private TimetableService timetableService;
    private int minutes = 15;

    public ItineraryServiceClass(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public ItineraryServiceClass(TimetableService timetableService, int minutes) {
        this.timetableService = timetableService;
        this.minutes = minutes;
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
                    if(localTime.minusMinutes(minutes).isBefore(time)){
                        results.add(localTime); //dodaj odjazd do zbioru szukanych odjazdow
                    }
                }
            }

        }
        return results;
    }
}
