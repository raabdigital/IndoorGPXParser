package product.engine.gpxparser.gpx.nodes;

import java.util.HashSet;

public class GPXTrack {

    private String trackName;
    private String trackComment;
    private String trackDescription;
    private String trackSrc;
    private Integer trackNumber;
    private String trackType;
    private HashSet<GPXTrackSegment> gpxTrackSegments;

    public GPXTrack() {
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackComment() {
        return trackComment;
    }

    public void setTrackComment(String trackComment) {
        this.trackComment = trackComment;
    }

    public String getTrackDescription() {
        return trackDescription;
    }

    public void setTrackDescription(String trackDescription) {
        this.trackDescription = trackDescription;
    }

    public String getTrackSrc() {
        return trackSrc;
    }

    public void setTrackSrc(String trackSrc) {
        this.trackSrc = trackSrc;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public HashSet<GPXTrackSegment> getGpxTrackSegments() {
        return gpxTrackSegments;
    }

    public void setGpxTrackSegments(HashSet<GPXTrackSegment> gpxTrackSegments) {
        this.gpxTrackSegments = gpxTrackSegments;
    }

    public void addGPXTrackSegment(GPXTrackSegment gpxTrackSegment) {
        if (gpxTrackSegments == null) {
            gpxTrackSegments = new HashSet<GPXTrackSegment>();
        }

        gpxTrackSegments.add(gpxTrackSegment);
    }

}
