package product.engine.gpxparser.gpx.nodes;

import java.util.ArrayList;

public class GPXTrackSegment {
	private ArrayList<GPXWaypoint> gpxTrackPoints;

	public GPXTrackSegment() {
	}


	public ArrayList<GPXWaypoint> getGpxTrackPoints() {
		return gpxTrackPoints;
	}


	public void setGpxTrackPoints(ArrayList<GPXWaypoint> gpxTrackPoints) {
		this.gpxTrackPoints = gpxTrackPoints;
	}


	public void addGPXTrackPoint(GPXWaypoint trackPoint){
		if (gpxTrackPoints == null) {
			gpxTrackPoints = new ArrayList<GPXWaypoint>();
		}

		gpxTrackPoints.add(trackPoint);
	}
}
