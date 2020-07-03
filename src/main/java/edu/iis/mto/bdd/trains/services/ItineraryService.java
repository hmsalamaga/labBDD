package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryService {
    TimetableService timetableService;
    int TIME_TO_NEXT_TRAIN = 30;


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
                    if(localTime.minusMinutes(TIME_TO_NEXT_TRAIN).isBefore(startTime)) {
                        result.add(localTime);
                    }
                }
            }
        }

        return result;
    }
}
