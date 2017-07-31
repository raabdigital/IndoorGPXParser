package product.engine.gpxparser.gpx;

import java.util.HashSet;

import product.engine.gpxparser.gpx.nodes.GPXBeaconDevice;
import product.engine.gpxparser.gpx.nodes.GPXMetadata;
import product.engine.gpxparser.gpx.nodes.GPXRoute;
import product.engine.gpxparser.gpx.nodes.GPXTrack;
import product.engine.gpxparser.gpx.nodes.GPXTrackSegment;
import product.engine.gpxparser.gpx.nodes.GPXWaypoint;

public class GPX {

	private String version;
	private String creator;
	private HashSet<GPXWaypoint> gpxWaypoints;
	private HashSet<GPXTrack> gpxTracks;
	private HashSet<GPXRoute> gpxRoutes;
	private HashSet<GPXMetadata> gpxMetadatas;
	private HashSet<GPXTrackSegment> gpxTrackSegments;
	private HashSet<GPXBeaconDevice> beaconDeviceOnTheTours;

	public GPX() {
	}



	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public HashSet<GPXWaypoint> getGpxWaypoints() {
		return gpxWaypoints;
	}


	public void setGpxWaypoints(HashSet<GPXWaypoint> gpxWaypoints) {
		this.gpxWaypoints = gpxWaypoints;
	}


	public HashSet<GPXTrack> getGpxTracks() {
		return gpxTracks;
	}


	public void setGpxTracks(HashSet<GPXTrack> gpxTracks) {
		this.gpxTracks = gpxTracks;
	}


	public HashSet<GPXRoute> getGpxRoutes() {
		return gpxRoutes;
	}


	public void setGpxRoutes(HashSet<GPXRoute> gpxRoutes) {
		this.gpxRoutes = gpxRoutes;
	}


	public HashSet<GPXMetadata> getGpxMetadatas() {
		return gpxMetadatas;
	}


	public void setGpxMetadatas(HashSet<GPXMetadata> gpxMetadatas) {
		this.gpxMetadatas = gpxMetadatas;
	}


	public HashSet<GPXTrackSegment> getGpxTrackSegments() {
		return gpxTrackSegments;
	}


	public void setGpxTrackSegments(HashSet<GPXTrackSegment> gpxTrackSegments) {
		this.gpxTrackSegments = gpxTrackSegments;
	}

	public HashSet<GPXBeaconDevice> getBeaconDeviceOnTheTours() {
		return beaconDeviceOnTheTours;
	}

	public void setBeaconDeviceOnTheTours(HashSet<GPXBeaconDevice> beaconDeviceOnTheTours) {
		this.beaconDeviceOnTheTours = beaconDeviceOnTheTours;
	}

	public void addGPXWaypoint(GPXWaypoint gpxWaypoint){
		if (gpxWaypoints == null) {
			gpxWaypoints = new HashSet<GPXWaypoint>();
		}

		gpxWaypoints.add(gpxWaypoint);
	}

	public void addGPXRoute(GPXRoute gpxRoute){
		if (gpxRoutes == null) {
			gpxRoutes = new HashSet<GPXRoute>();
		}

		gpxRoutes.add(gpxRoute);
	}

	public void addGPXTrack(GPXTrack gpxTrack){
		if (gpxTracks == null) {
			gpxTracks = new HashSet<GPXTrack>();
		}

		gpxTracks.add(gpxTrack);
	}

	public void addGPXMetada(GPXMetadata gpxMetadata){
		if (gpxMetadatas == null) {
			gpxMetadatas = new HashSet<GPXMetadata>();
		}

		gpxMetadatas.add(gpxMetadata);
	}

	public void addBeaconDeviceOnTheTour(GPXBeaconDevice beaconDeviceOnTheTour){
		if(beaconDeviceOnTheTours == null){
			beaconDeviceOnTheTours = new HashSet<GPXBeaconDevice>();
		}

		beaconDeviceOnTheTours.add(beaconDeviceOnTheTour);
	}


}

