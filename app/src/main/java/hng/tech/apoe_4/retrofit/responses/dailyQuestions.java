package hng.tech.apoe_4.retrofit.responses;

public class dailyQuestions {
    private String day;
    private String night;
    private String plannedActivities;
    private Boolean reminders;

    public dailyQuestions(String day, String night, String plannedActivities, Boolean reminders) {
        this.day = day;
        this.night = night;
        this.plannedActivities = plannedActivities;
        this.reminders = reminders;
    }


    public String getDay() {
        return day;
    }

    public String getNight() {
        return night;
    }

    public String getPlannedActivities() {
        return plannedActivities;
    }

    public Boolean getReminders() {
        return reminders;
    }

}
