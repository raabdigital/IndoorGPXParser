package product.engine.gpxparser.gpx.nodes;

import java.util.List;

public class GPXMetdadataExtension {

	private String startplace;
	private String tourTypeCode;
	private String regionCode;
	private String language;
	private Long condition;
	private Long difficulty;
	private Long experience;
	private Long landscape;
	private Long technical;
	private Long fullDuration;
	private Long realDuration;
	private Long distanceLong;
	private Long poiCount;
	private Integer strollerFriendly;
	private String startDescription;

	private String mainCoverImageId;
	private List<String> coverImages;
	private String imageMapFileName;

	public GPXMetdadataExtension() {
	}

	public String getStartplace() {
		return startplace;
	}

	public void setStartplace(String startplace) {
		this.startplace = startplace;
	}

	public String getTourTypeCode() {
		return tourTypeCode;
	}

	public void setTourTypeCode(String tourTypeCode) {
		this.tourTypeCode = tourTypeCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getCondition() {
		return condition;
	}

	public void setCondition(Long condition) {
		this.condition = condition;
	}

	public Long getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Long difficulty) {
		this.difficulty = difficulty;
	}

	public Long getExperience() {
		return experience;
	}

	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public Long getLandscape() {
		return landscape;
	}

	public void setLandscape(Long landscape) {
		this.landscape = landscape;
	}

	public Long getTechnical() {
		return technical;
	}

	public void setTechnical(Long technical) {
		this.technical = technical;
	}

	public Long getFullDuration() {
		return fullDuration;
	}

	public void setFullDuration(Long fullDuration) {
		this.fullDuration = fullDuration;
	}

	public Long getRealDuration() {
		return realDuration;
	}

	public void setRealDuration(Long realDuration) {
		this.realDuration = realDuration;
	}

	public Long getDistanceLong() {
		return distanceLong;
	}

	public void setDistanceLong(Long distanceLong) {
		this.distanceLong = distanceLong;
	}

	public Long getPoiCount() {
		return poiCount;
	}

	public void setPoiCount(Long poiCount) {
		this.poiCount = poiCount;
	}

	public Integer getStrollerFriendly() {
		return strollerFriendly;
	}

	public void setStrollerFriendly(Integer strollerFriendly) {
		this.strollerFriendly = strollerFriendly;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getStartDescription() {
		return startDescription;
	}

	public void setStartDescription(String startDescription) {
		this.startDescription = startDescription;
	}

	public String getMainCoverImageId() {
		return mainCoverImageId;
	}

	public void setMainCoverImageId(String coverImageId) {
		this.mainCoverImageId = coverImageId;
	}

	public List<String> getCoverImages() {
		return coverImages;
	}

	public void setCoverImages(List<String> coverImages) {
		this.coverImages = coverImages;
	}

	public String getImageMapFileName() {
		return imageMapFileName;
	}

	public void setImageMapFileName(String imageMapFileName) {
		this.imageMapFileName = imageMapFileName;
	}

}
