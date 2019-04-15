package hng.tech.apoe_4.retrofit.responses;

public class DailyResponseData {
    private String _id;
    private String day;
    private String night;
    private String plannedActivities;
    private Boolean reminders;
    private int __v;

    public DailyResponseData(String _id, String day, String night, String plannedActivities, Boolean reminders, int __v) {
        this._id = _id;
        this.day = day;
        this.night = night;
        this.plannedActivities = plannedActivities;
        this.reminders = reminders;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
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

    public int get__v() {
        return __v;
    }
}
