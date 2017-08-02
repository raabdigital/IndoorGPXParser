public class GPXMetaDataExtensionTrlDTO {

    private String languageCode;
    private String languageName;
    private String name;
    private String description;

    private String startPlace;
    private String startDescription;

    private boolean translatedAudioExist;
    private boolean translatedVideoExist;
    private boolean translatedExternalVideoExist;

    public GPXMetaDataExtensionTrlDTO() {
    }

    public boolean isTranslatedExternalVideoExist() { return translatedExternalVideoExist; }

    public void setTranslatedExternalVideoExist(boolean translatedExternalVideoExist) { this.translatedExternalVideoExist = translatedExternalVideoExist; }

    public boolean isTranslatedVideoExist() { return translatedVideoExist; }

    public void setTranslatedVideoExist(boolean translatedVideoExist) { this.translatedVideoExist = translatedVideoExist; }

    public Long getId() {
        return id;
    }

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

    public String getStartPlace() {
        return startPlace;
    }

    public String getStartDescription() {
        return startDescription;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public void setStartDescription(String startDescription) {
        this.startDescription = startDescription;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public boolean isTranslatedAudioExist() {
        return translatedAudioExist;
    }

    public void setTranslatedAudioExist(boolean translatedAudioExist) {
        this.translatedAudioExist = translatedAudioExist;
    }



}