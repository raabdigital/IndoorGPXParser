package product.engine.gpxparser.gpx.nodes;

public class GPXBeaconDevice {

    private long id;
    private int major;
    private int minor;
    private String name;
    private String beaconUniqueId;
    private String waypointId;
    private Long tourId;
    private String address;
    private String firmwareversion;
    private double latitude;
    private double longitude;

    public GPXBeaconDevice() {

    }

    public GPXBeaconDevice(String waypointId, Long tourId, GPXBeaconDevice beaconDevice) {
        this.waypointId = waypointId;
        this.tourId = tourId;

        this.major = beaconDevice.getMajor();
        this.minor = beaconDevice.getMinor();
        this.latitude = beaconDevice.getLatitude();
        this.longitude = beaconDevice.getLongitude();

        if (beaconDevice.getBeaconUniqueId() != null) {
            this.beaconUniqueId = beaconDevice.getBeaconUniqueId();
        }

        if (beaconDevice.getName() != null) {
            this.name = beaconDevice.getName();
        }

        if (beaconDevice.getAddress() != null) {
            this.address = beaconDevice.getAddress();
        }

        if (beaconDevice.getFirmwareversion() != null) {
            this.firmwareversion = beaconDevice.getFirmwareversion();
        }
    }

    public GPXBeaconDevice(Long tourId, GPXBeaconDevice beaconDevice){
        this(null, tourId, beaconDevice);
    }

    public long getId() {
        return id;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String getName() {
        return name;
    }

    public String getBeaconUniqueId() {
        return beaconUniqueId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeaconUniqueId(String beaconUniqueId) {
        this.beaconUniqueId = beaconUniqueId;
    }

    public String getWaypointId() {
        return waypointId;
    }

    public void setWaypointId(String waypointId) {
        this.waypointId = waypointId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getAddress() {
        return address;
    }

    public String getFirmwareversion() {
        return firmwareversion;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirmwareversion(String firmwareversion) {
        this.firmwareversion = firmwareversion;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}