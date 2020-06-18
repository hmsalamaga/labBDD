package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryServiceImplementation implements ItineraryService {
    private TimetableService timetableService;
    private final int MINUTES_TO_NEXT_DEPARTURE = 15;

    public ItineraryServiceImplementation(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime time) {
        List<Line> lines = timetableService.findLinesThrough(departure, destination);
        List<LocalTime> nextDepartures = new ArrayList<>();

        List<LocalTime> tempDepartures;
        for (Line line : lines) {
            tempDepartures = timetableService.findArrivalTimes(line, departure);

            for (LocalTime localTime : tempDepartures) {
                if (localTime.isAfter(time) && localTime.minusMinutes(MINUTES_TO_NEXT_DEPARTURE).isBefore(time)) {
                    nextDepartures.add(localTime);
                }
            }
        }

        return nextDepartures;
    }
}
