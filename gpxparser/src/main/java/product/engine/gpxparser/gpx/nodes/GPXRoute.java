package product.engine.gpxparser.gpx.nodes;


import java.util.ArrayList;


public class GPXRoute {

	private String routeName;
	private String routeComment;
	private String routeDescription;
	private String routeSrc;
	private Integer routeNumber;
	private String routeType;
	private ArrayList<GPXWaypoint> gpxRoutePoints;


	public GPXRoute() {
	}


	public String getRouteName() {
		return routeName;
	}


	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}


	public String getRouteComment() {
		return routeComment;
	}


	public void setRouteComment(String routeComment) {
		this.routeComment = routeComment;
	}


	public String getRouteDescription() {
		return routeDescription;
	}


	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}


	public String getRouteSrc() {
		return routeSrc;
	}


	public void setRouteSrc(String routeSrc) {
		this.routeSrc = routeSrc;
	}


	public Integer getRouteNumber() {
		return routeNumber;
	}


	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}


	public String getRouteType() {
		return routeType;
	}


	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}


	public ArrayList<GPXWaypoint> getGpxRoutePoints() {
		return gpxRoutePoints;
	}


	public void setGpxRoutePoints(ArrayList<GPXWaypoint> gpxRoutePoints) {
		this.gpxRoutePoints = gpxRoutePoints;
	}


	public void addGPXRoutePoint(GPXWaypoint gpxWaypoint){
		if (gpxRoutePoints == null) {
			gpxRoutePoints = new ArrayList<GPXWaypoint>();
		}

		gpxRoutePoints.add(gpxWaypoint);
	}

}
