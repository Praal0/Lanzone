package dorian.guerrero.lanzone.di;

import java.util.List;

import dorian.guerrero.lanzone.model.Meeting;
import dorian.guerrero.lanzone.service.DummyMeetingApiService;
import dorian.guerrero.lanzone.service.MeetingApiService;

public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     * @return
     */
    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }

    public static void initializeMeetingApiService(List<Meeting> meetings) {
        // Purge
        service = new DummyMeetingApiService();

        for (Meeting meeting: meetings)
            service.createMeeting(meeting);
    }


}
