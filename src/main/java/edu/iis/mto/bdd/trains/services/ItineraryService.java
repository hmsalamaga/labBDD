package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryService {
    TimetableService timetableService;

    public ItineraryService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime startTime) {
        List<Line> lines = timetableService.findLinesThrough(departure, destination);
        List<LocalTime> result = new ArrayList<>();

        List<LocalTime> temp;
        for (Line line : lines) {
            temp = timetableService.findArrivalTimes(line, departure);

            for (LocalTime localTime : temp) {
                if (startTime.isBefore(localTime)) {
                    result.add(localTime);
                }
            }
        }

        return result;
    }
}
