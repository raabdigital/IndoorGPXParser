package product.engine.gpxparser.gpx.nodes;

public class GPXWayPointOpeningHours {

	private Weekday weekday;
	private Monday monday;
	private Tuesday tuesday;
	private Wednesday wednesday;
	private Thursday thursday;
	private Friday friday;
	private Saturday saturday;
	private Sunday sunday;

	public GPXWayPointOpeningHours() {

	}

	public Weekday getWeekday() {
		return weekday;
	}

	public Monday getMonday() {
		return monday;
	}

	public Tuesday getTuesday() {
		return tuesday;
	}

	public Wednesday getWednesday() {
		return wednesday;
	}

	public Thursday getThursday() {
		return thursday;
	}

	public Friday getFriday() {
		return friday;
	}

	public Saturday getSaturday() {
		return saturday;
	}

	public Sunday getSunday() {
		return sunday;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public void setMonday(Monday monday) {
		this.monday = monday;
	}

	public void setTuesday(Tuesday tuesday) {
		this.tuesday = tuesday;
	}

	public void setWednesday(Wednesday wednesday) {
		this.wednesday = wednesday;
	}

	public void setThursday(Thursday thursday) {
		this.thursday = thursday;
	}

	public void setFriday(Friday friday) {
		this.friday = friday;
	}

	public void setSaturday(Saturday saturday) {
		this.saturday = saturday;
	}

	public void setSunday(Sunday sunday) {
		this.sunday = sunday;
	}

	public static class Weekday {

		Long startTime;
		Long endTime;

		public Weekday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Monday {

		Long startTime;
		Long endTime;

		public Monday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Tuesday {

		Long startTime;
		Long endTime;

		public Tuesday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Wednesday {

		Long startTime;
		Long endTime;

		public Wednesday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Thursday {

		Long startTime;
		Long endTime;

		public Thursday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Friday {

		Long startTime;
		Long endTime;

		public Friday() {
		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
	}

	public static class Saturday {

		Long startTime;
		Long endTime;

		public Saturday() {

		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}

	}

	public static class Sunday {

		Long startTime;
		Long endTime;

		public Sunday() {

		}

		public Long getStartTime() {
			return startTime;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}

	}

}
