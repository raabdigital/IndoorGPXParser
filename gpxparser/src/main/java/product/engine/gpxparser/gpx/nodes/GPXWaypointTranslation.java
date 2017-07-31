package product.engine.gpxparser.gpx.nodes;

import java.util.Map;

public class GPXWaypointTranslation {

	private String languageCode;
	private String languageName;
	private String name;
	private String description;
	private String audioFileName;
	private String videoFileName;
	private String externalVideoFileName;
	private Map<String, String> gpxWaypointImageDescriptionMap;


	public String getLanguageCode() {
		return languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAudioFileName() {
		return audioFileName;
	}

	public void setAudioFileName(String audioFileName) {
		this.audioFileName = audioFileName;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}

	public String getExternalVideoFileName() {
		return externalVideoFileName;
	}

	public void setExternalVideoFileName(String externalVideoFileName) {
		this.externalVideoFileName = externalVideoFileName;
	}

	public Map<String, String> getGpxWaypointImageDescriptionMap() {
		return gpxWaypointImageDescriptionMap;
	}

	public void setGpxWaypointImageDescriptionMap(Map<String, String> gpxWaypointImageDescriptionMap) {
		this.gpxWaypointImageDescriptionMap = gpxWaypointImageDescriptionMap;
	}
}
