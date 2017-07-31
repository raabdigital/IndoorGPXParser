package product.engine.gpxparser.gpx.nodes;

import java.util.ArrayList;
import java.util.List;

public class GPXWaypointExtension {

	private Long poiId;

	private List<String> poiImages;

	private String audioFileName;

	private String videoFileName;

	private String externalVideoLink;

	private String qrCode;

	private String poiType;

	private List<GPXWaypointTranslation> gpxWaypointTranslations;

	/** add from 2014.11.17 */
	private String poiCategory;

	private String address;

	private String phoneNumber;

	private String webPage;

	private String facebookPage;

	private GPXWayPointOpeningHours gpxWayPointOpeningHours;

	private Long eventStartTime;

	private Long eventEndTime;

	/** add 2015.04.02 */
	private String email;

	/** add 2015.06.08 */
	private List<GPXBeaconDevice> beaconDevices;

	public GPXWaypointExtension() {
	}

	public GPXWaypointExtension(ArrayList<String> wayPointPhotos) {
		this.poiImages = wayPointPhotos;
		// TODO Auto-generated constructor stub
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public List<String> getPoiImages() {
		return poiImages;
	}

	public void setPoiImages(List<String> poiImages) {
		this.poiImages = poiImages;
	}

	public String getAudioFileName() {
		return audioFileName;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setAudioFileName(String audioFileName) {
		this.audioFileName = audioFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}

	public List<GPXWaypointTranslation> getGpxWaypointTranslations() {
		return gpxWaypointTranslations;
	}

	public void setGpxWaypointTranslations(List<GPXWaypointTranslation> gpxWaypointTranslations) {
		this.gpxWaypointTranslations = gpxWaypointTranslations;
	}

	public String getPoiCategory() {
		return poiCategory;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getWebPage() {
		return webPage;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public GPXWayPointOpeningHours getGpxWayPointOpeningHours() {
		return gpxWayPointOpeningHours;
	}

	public Long getEventStartTime() {
		return eventStartTime;
	}

	public Long getEventEndTime() {
		return eventEndTime;
	}

	public void setPoiCategory(String poiCategory) {
		this.poiCategory = poiCategory;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public void setGpxWayPointOpeningHours(GPXWayPointOpeningHours gpxWayPointOpeningHours) {
		this.gpxWayPointOpeningHours = gpxWayPointOpeningHours;
	}

	public void setEventStartTime(Long eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public void setEventEndTime(Long eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<GPXBeaconDevice> getBeaconDevices() {
		return beaconDevices;
	}

	public void setBeaconDevices(List<GPXBeaconDevice> beaconDevices) {
		this.beaconDevices = beaconDevices;
	}

	public String getExternalVideoLink() {
		return externalVideoLink;
	}

	public void setExternalVideoLink(String externalVideoLink) {
		this.externalVideoLink = externalVideoLink;
	}

}
