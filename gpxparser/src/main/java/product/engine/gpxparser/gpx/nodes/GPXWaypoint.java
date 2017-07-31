package product.engine.gpxparser.gpx.nodes;

import java.util.Date;

public class GPXWaypoint implements Comparable<GPXWaypoint> {

    private Double latitude;
    private Double longitude;
    private Double elevation;
    private Date time;
    private Double magneticDeclination;
    private Double geoidHeight;
    private String name;
    private String comment;
    private String description;
    private String src;
    private String sym;
    private String type;
    private Integer sat;
    private Double hdop;
    private Double vdop;
    private Double pdop;
    private Double ageOfGPSData;
    private Integer dgpsid;
    private GPXWaypointExtension extensions;


    public GPXWaypoint() {
    }

    public Double getLatitude() {
        return latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public Double getElevation() {
        return elevation;
    }


    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }


    public Date getWpTime() {
        return time;
    }


    public void setWpTime(Date time) {
        this.time = time;
    }


    public Double getMagneticDeclination() {
        return magneticDeclination;
    }


    public void setMagneticDeclination(Double magneticDeclination) {
        this.magneticDeclination = magneticDeclination;
    }


    public Double getGeoidHeight() {
        return geoidHeight;
    }


    public void setGeoidHeight(Double geoidHeight) {
        this.geoidHeight = geoidHeight;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getSrc() {
        return src;
    }


    public void setSrc(String src) {
        this.src = src;
    }


    public String getSym() {
        return sym;
    }


    public void setSym(String sym) {
        this.sym = sym;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public Integer getSat() {
        return sat;
    }


    public void setSat(Integer sat) {
        this.sat = sat;
    }


    public Double getHdop() {
        return hdop;
    }


    public void setHdop(Double hdop) {
        this.hdop = hdop;
    }


    public Double getVdop() {
        return vdop;
    }


    public void setVdop(Double vdop) {
        this.vdop = vdop;
    }


    public Double getPdop() {
        return pdop;
    }


    public void setPdop(Double pdop) {
        this.pdop = pdop;
    }


    public Double getAgeOfGPSData() {
        return ageOfGPSData;
    }


    public void setAgeOfGPSData(Double ageOfGPSData) {
        this.ageOfGPSData = ageOfGPSData;
    }


    public Integer getDgpsid() {
        return dgpsid;
    }


    public void setDgpsid(Integer dgpsid) {
        this.dgpsid = dgpsid;
    }

    public GPXWaypointExtension getExtensions() {
        return extensions;
    }

    public void setExtensions(GPXWaypointExtension extensions) {
        this.extensions = extensions;
    }


    @Override
    public int compareTo(GPXWaypoint another) {
        if (getWpTime() == null) {
            return -1;
        }
        if (another.getWpTime() == null) {
            return 1;
        }
        if (getWpTime().after(another.getWpTime())) {
            return 1;
        } else {
            return -1;
        }
    }
}
