package product.engine.gpxparser.gpx;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import product.engine.gpxparser.gpx.nodes.GPXBeaconDevice;
import product.engine.gpxparser.gpx.nodes.GPXConstants;
import product.engine.gpxparser.gpx.nodes.GPXMetaDataExtensionTrl;
import product.engine.gpxparser.gpx.nodes.GPXMetadata;
import product.engine.gpxparser.gpx.nodes.GPXMetadataExtension;
import product.engine.gpxparser.gpx.nodes.GPXRoute;
import product.engine.gpxparser.gpx.nodes.GPXTrack;
import product.engine.gpxparser.gpx.nodes.GPXTrackSegment;
import product.engine.gpxparser.gpx.nodes.GPXWayPointOpeningHours;
import product.engine.gpxparser.gpx.nodes.GPXWaypoint;
import product.engine.gpxparser.gpx.nodes.GPXWaypointExtension;
import product.engine.gpxparser.gpx.nodes.GPXWaypointImageDescription;
import product.engine.gpxparser.gpx.nodes.GPXWaypointTranslation;

public class IndoorGPXParser {

    private static final String LOG_TAG = "IndoorGPXParser";

    public GPX parseGPX(InputStream in) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(in);

        // xml element GPX
        Node firstChild = document.getFirstChild();

        if (firstChild != null && GPXConstants.GPX_NODE.equals(firstChild.getNodeName())) {
            GPX gpx = new GPX();
            NamedNodeMap attributes = firstChild.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node nodeAttr = attributes.item(i);
                if (GPXConstants.VERSION_ATTR.equals(nodeAttr.getNodeName())) {
                    gpx.setVersion(nodeAttr.getNodeValue());
                } else if (GPXConstants.CREATOR_ATTR.equals(nodeAttr.getNodeName())) {
                    gpx.setCreator(nodeAttr.getNodeValue());
                }
            }

            // <gpx> child nodes
            NodeList nodeList = firstChild.getChildNodes();
            Log.d(LOG_TAG, "found " + nodeList.getLength() + " child nodes. Start parsing");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (GPXConstants.WPT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "waypoint node found");
                    GPXWaypoint currentWaypoint = parseGPXWaypoint(currentNode);
                    if (currentWaypoint != null) {
                        Log.d(LOG_TAG, "add waypoint " + currentWaypoint.getName() + " to gpx");
                        gpx.addGPXWaypoint(currentWaypoint);
                    }
                } else if (GPXConstants.TRK_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "track node found");
                    GPXTrack currenTrack = parseGPXTrack(currentNode);
                    if (currenTrack != null) {
                        gpx.addGPXTrack(currenTrack);
                    }
                } else if (GPXConstants.RTE_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "route node found");
                    GPXRoute currentRoute = parseGPXRoute(currentNode);
                    if (currentRoute != null) {
                        Log.d(LOG_TAG, "add route " + currentRoute.getRouteName() + " to gpx");
                        gpx.addGPXRoute(currentRoute);
                    }
                } else if (GPXConstants.METADATA_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "metadata node found");
                    GPXMetadata currentMetadata = parseGPXMetada(currentNode);
                    if (currentMetadata != null) {
                        Log.d(LOG_TAG, "add metadata " + currentMetadata.getMetadataName() + " to gpx");
                        gpx.addGPXMetada(currentMetadata);
                    }
                } else if (GPXConstants.POI_BEACON_DEVICES.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "beacondevices node found");
                    NodeList beaconDeviceNodes = currentNode.getChildNodes();
                    if (beaconDeviceNodes != null) {
                        for (int beaconDevicePos = 0; beaconDevicePos < beaconDeviceNodes.getLength(); beaconDevicePos++) {
                            GPXBeaconDevice beaconDeviceOnTheTour = new GPXBeaconDevice();
                            NodeList beaconDeviceItemNodes = beaconDeviceNodes.item(beaconDevicePos).getChildNodes();
                            if (beaconDeviceItemNodes != null) {
                                for (int beaconDeviceNodeItemPos = 0; beaconDeviceNodeItemPos < beaconDeviceItemNodes.getLength(); beaconDeviceNodeItemPos++) {
                                    Node currentBeaconItemNode = beaconDeviceItemNodes.item(beaconDeviceNodeItemPos);
                                    if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_UNIQUE_ID, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setBeaconUniqueId(currentBeaconItemNode.getFirstChild().getNodeValue());
                                    } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_NAME, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setName(currentBeaconItemNode.getFirstChild().getNodeValue());
                                    } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_MAJOR, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setMajor(getNodeValueAsInteger(currentBeaconItemNode.getFirstChild().getNodeValue()));
                                    } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_MINOR, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setMinor(getNodeValueAsInteger(currentBeaconItemNode.getFirstChild().getNodeValue()));
                                    } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_ADDRESS, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setAddress(currentBeaconItemNode.getFirstChild().getNodeValue());
                                    } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_FIRMWAREVERSION, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setFirmwareversion(currentBeaconItemNode.getFirstChild().getNodeValue());
                                    } else if (hasMatchWithThisNodeName(GPXConstants.BEACON_DEVICE_LAT, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setLatitude(getNodeValueAsDouble(currentBeaconItemNode.getFirstChild().getNodeValue()));
                                    } else if (hasMatchWithThisNodeName(GPXConstants.BEACON_DEVICE_LON, currentBeaconItemNode)) {
                                        beaconDeviceOnTheTour.setLongitude(getNodeValueAsDouble(currentBeaconItemNode.getFirstChild().getNodeValue()));
                                    }
                                }
                                gpx.addBeaconDeviceOnTheTour(beaconDeviceOnTheTour);
                            }
                        }
                    }
                }
            }
            return gpx;
        } else {
            Log.d(LOG_TAG, "root node not <gpx>");
        }

        return null;
    }


    private GPXWaypoint parseGPXWaypoint(Node node) {
        if (node == null) {
            Log.d(LOG_TAG, "null waypoint node received");
            return null;
        }

        GPXWaypoint waypoint = new GPXWaypoint();
        NamedNodeMap nodeMapAttr = node.getAttributes();

        Node latitudeNode = nodeMapAttr.getNamedItem(GPXConstants.LAT_ATTR);
        if (latitudeNode != null) {
            Double latitude = null;
            try {
                latitude = Double.parseDouble(latitudeNode.getNodeValue());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            waypoint.setLatitude(latitude);
        } else {
            Log.d(LOG_TAG, "no latitude value found in waypoint data");
        }


        Node longitudeNode = nodeMapAttr.getNamedItem(GPXConstants.LON_ATTR);
        if (longitudeNode != null) {
            Double longitude = null;
            try {
                longitude = Double.parseDouble(longitudeNode.getNodeValue());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            waypoint.setLongitude(longitude);
        } else {
            Log.d(LOG_TAG, "no longitude value found in waypoint data");
        }

        NodeList wpChildNodes = node.getChildNodes();
        if (wpChildNodes != null) {
            for (int i = 0; i < wpChildNodes.getLength(); i++) {
                Node currentNode = wpChildNodes.item(i);
                if (GPXConstants.ELE_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "elevation found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setElevation(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.TIME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "time found in waypoint data");

                    if (currentNode.getFirstChild() != null) {
                        waypoint.setWpTime(getNodeValueAsDate(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.MAGVAR_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "magvar found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setMagneticDeclination(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.GEOIDHEIGHT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "geoidheight found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setGeoidHeight(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.NAME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "name found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setName(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.CMT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "comment found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setComment(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.DESC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "description found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setDescription(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.SRC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "src found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setSrc(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.SYM_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "sym found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setSym(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.TYPE_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "type found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setType(currentNode.getFirstChild().getNodeValue());
                    }
                } else if (GPXConstants.SAT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "sat found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setSat(getNodeValueAsInteger(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.HDOP_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "hdop found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setHdop(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.VDOP_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "vdop found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setVdop(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.PDOP_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "pdop found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setPdop(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.AGEOFGPSDATA_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "ageofgpsdata found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setAgeOfGPSData(getNodeValueAsDouble(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.DGPSID_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "dgpsid found in waypoint data");
                    if (currentNode.getFirstChild() != null) {
                        waypoint.setDgpsid(getNodeValueAsInteger(currentNode.getFirstChild().getNodeValue()));
                    }
                } else if (GPXConstants.EXTENSIONS_NODE.equals(currentNode.getNodeName())) {
                    GPXWaypointExtension ext = new GPXWaypointExtension();
                    NodeList extNodes = currentNode.getChildNodes();
                    if (extNodes != null) {
                        for (int j = 0; j < extNodes.getLength(); j++) {
                            Node currentExtNode = extNodes.item(j);
                            /**
                             * Poi images
                             */
                            if (GPXConstants.POIIMAGELIST.equals(currentExtNode.getNodeName())) {
                                List<String> poiImages = new ArrayList<String>();
                                NodeList imgageNodes = currentExtNode.getChildNodes();
                                if (imgageNodes != null) {
                                    for (int k = 0; k < imgageNodes.getLength(); k++) {
                                        Node imageNode = imgageNodes.item(k);
                                        if (imageNode.getFirstChild() != null) {
                                            poiImages.add(imageNode.getFirstChild().getNodeValue());
                                        }
                                    }
                                }
                                ext.setPoiImages(poiImages);

                            } else if (GPXConstants.POIID.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setPoiId(Long.valueOf(currentExtNode.getFirstChild().getNodeValue()));
                                }
                            } else if (GPXConstants.POIAUDIOFILEID.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setAudioFileName(currentExtNode.getFirstChild().getNodeValue());
                                }
                            } else if (GPXConstants.POIVIDEOFILEID.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setVideoFileName(currentExtNode.getFirstChild().getNodeValue());
                                }
                            } else if (GPXConstants.POIEXTERNALVIDEOLINK.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setExternalVideoLink(currentExtNode.getFirstChild().getNodeValue());
                                }
                            } else if (GPXConstants.POIQRCODE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setQrCode(currentExtNode.getFirstChild().getNodeValue());
                                }
                            } else if (GPXConstants.POITYPE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setPoiType(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POITRANSLATIONLIST.equals(currentExtNode.getNodeName())) {

                                List<GPXWaypointTranslation> gpxWaypointTranslations = new ArrayList<>();

                                NodeList translationNodes = currentExtNode.getChildNodes();

                                if (translationNodes != null) {
                                    for (int k = 0; k < translationNodes.getLength(); k++) {

                                        GPXWaypointTranslation gpxWaypointTranslation = new GPXWaypointTranslation();

                                        NodeList translationItemNodes = translationNodes.item(k).getChildNodes();

                                        if (translationItemNodes != null) {

                                            for (int h = 0; h < translationItemNodes.getLength(); h++) {

                                                Node currentTranslationItemNode = translationItemNodes.item(h);

                                                if (GPXConstants.POILANGUAGECODE.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setLanguageCode(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POILANGUAGENAME.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setLanguageName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POINAMETRANSLATION.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POIDESCRIPTIONTRANSLATION.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setDescription(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }
                                                } else if (GPXConstants.POITRANSLATEDAUDIFILENAME.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setAudioFileName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POITRANSLATEDVIDEOFILENAME.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setVideoFileName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POITRANSLATEDEXTERNALVIDEOLINK.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxWaypointTranslation.setExternalVideoFileName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.POI_IMAGE_DESCRIPTION_LIST.equals(currentTranslationItemNode.getNodeName())) {

                                                    Map<String, String> gpxWayPointImageDescriptionMap = new HashMap<>();

                                                    NodeList imageDescriptionList = currentTranslationItemNode.getChildNodes();

                                                    for (int imageDescIndex = 0; imageDescIndex < imageDescriptionList.getLength(); imageDescIndex++) {

                                                        NodeList currentImageDescriptionNodeList = imageDescriptionList.item(imageDescIndex).getChildNodes();

                                                        GPXWaypointImageDescription gpxWaypointImageDescription = new GPXWaypointImageDescription();

                                                        if (currentImageDescriptionNodeList != null) {

                                                            for (int imageDescNodeIndex = 0; imageDescNodeIndex < currentImageDescriptionNodeList.getLength(); imageDescNodeIndex++) {

                                                                Node currentImageDescriptionItem = currentImageDescriptionNodeList.item(imageDescNodeIndex);

                                                                if (GPXConstants.POI_IMAGE_FILE_NAME.equals(currentImageDescriptionItem.getNodeName())) {

                                                                    if (currentImageDescriptionItem.getFirstChild().getNodeValue() != null) {
                                                                        gpxWaypointImageDescription.setFileName(currentImageDescriptionItem.getFirstChild().getNodeValue());
                                                                    }

                                                                } else if (GPXConstants.POI_IMAGE_DESCRIPTION.equals(currentImageDescriptionItem.getNodeName())) {

                                                                    if (currentImageDescriptionItem.getFirstChild().getNodeValue() != null) {
                                                                        gpxWaypointImageDescription.setDescription(currentImageDescriptionItem.getFirstChild().getNodeValue());
                                                                    }
                                                                }
                                                            }

                                                        }

                                                        gpxWayPointImageDescriptionMap.put(gpxWaypointImageDescription.getFileName(), gpxWaypointImageDescription.getDescription());
                                                    }

                                                    gpxWaypointTranslation.setGpxWaypointImageDescriptionMap(gpxWayPointImageDescriptionMap);

                                                }

                                            }
                                        }
                                        gpxWaypointTranslations.add(gpxWaypointTranslation);
                                    }
                                }
                                ext.setGpxWaypointTranslations(gpxWaypointTranslations);

                            } else if (GPXConstants.POICATEGORY.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setPoiCategory(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POIADDRESS.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setAddress(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POIPHONENUMBER.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setPhoneNumber(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POIWEBPAGE.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setWebPage(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POIFACEBOOKPAGE.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setFacebookPage(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POIOPENINGHOURS.equals(currentExtNode.getNodeName())) {

                                NodeList openingNodes = currentExtNode.getChildNodes();

                                if (openingNodes != null) {

                                    GPXWayPointOpeningHours gpxWayPointOpeningHours = new GPXWayPointOpeningHours();

                                    for (int k = 0; k < openingNodes.getLength(); k++) {

                                        Node openingHoursItem = openingNodes.item(k);

                                        if (openingHoursItem != null) {

                                            if (GPXConstants.WEEKDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Weekday day = new GPXWayPointOpeningHours.Weekday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }
                                                gpxWayPointOpeningHours.setWeekday(day);
                                            } else if (GPXConstants.MONDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Monday day = new GPXWayPointOpeningHours.Monday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setMonday(day);
                                            } else if (GPXConstants.TUESDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Tuesday day = new GPXWayPointOpeningHours.Tuesday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }
                                                }

                                                gpxWayPointOpeningHours.setTuesday(day);

                                            } else if (GPXConstants.WEDNESDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Wednesday day = new GPXWayPointOpeningHours.Wednesday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setWednesday(day);

                                            } else if (GPXConstants.THURSDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Thursday day = new GPXWayPointOpeningHours.Thursday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setThursday(day);

                                            } else if (GPXConstants.FRIDAYDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Friday day = new GPXWayPointOpeningHours.Friday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setFriday(day);

                                            } else if (GPXConstants.SATURDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Saturday day = new GPXWayPointOpeningHours.Saturday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setSaturday(day);

                                            } else if (GPXConstants.SUNDAYOPENING.equals(openingHoursItem.getNodeName())) {

                                                NodeList weekDayNodeList = openingHoursItem.getChildNodes();

                                                GPXWayPointOpeningHours.Sunday day = new GPXWayPointOpeningHours.Sunday();

                                                for (int h = 0; h < weekDayNodeList.getLength(); h++) {

                                                    Node currentWeekDayNodeItem = weekDayNodeList.item(h);

                                                    if (GPXConstants.START.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setStartTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    } else if (GPXConstants.END.equals(currentWeekDayNodeItem.getNodeName())) {

                                                        day.setEndTime(getNodeValueAsLong(currentWeekDayNodeItem.getFirstChild().getNodeValue()));

                                                    }

                                                }

                                                gpxWayPointOpeningHours.setSunday(day);

                                            }

                                        }

                                    }

                                    ext.setGpxWayPointOpeningHours(gpxWayPointOpeningHours);

                                }

                            } else if (GPXConstants.EVENTSTARTTIME.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setEventStartTime(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                                }

                            } else if (GPXConstants.EVENTENDTTIME.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setEventEndTime(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                                }

                            } else if (GPXConstants.POIEMAIL.equals(currentExtNode.getNodeName())) {

                                if (currentExtNode.getFirstChild().getNodeValue() != null) {
                                    ext.setEmail(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.POI_BEACON_DEVICES.equals(currentExtNode.getNodeName())) {

                                List<GPXBeaconDevice> beaconDevicesOnTheTour = new ArrayList<GPXBeaconDevice>();

                                NodeList beaconDeviceNodes = currentExtNode.getChildNodes();

                                if (beaconDeviceNodes != null) {

                                    for (int beaconDevicePos = 0; beaconDevicePos < beaconDeviceNodes.getLength(); beaconDevicePos++) {

                                        GPXBeaconDevice beaconDeviceOnTheTour = new GPXBeaconDevice();

                                        NodeList beaconDeviceItemNodes = beaconDeviceNodes.item(beaconDevicePos).getChildNodes();

                                        if (beaconDeviceItemNodes != null) {

                                            for (int beaconDeviceNodeItemPos = 0; beaconDeviceNodeItemPos < beaconDeviceItemNodes.getLength(); beaconDeviceNodeItemPos++) {

                                                Node currentBeaconItemNode = beaconDeviceItemNodes.item(beaconDeviceNodeItemPos);

                                                if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_UNIQUE_ID, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setBeaconUniqueId(currentBeaconItemNode.getFirstChild().getNodeValue());

                                                } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_NAME, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setName(currentBeaconItemNode.getFirstChild().getNodeValue());

                                                } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_MAJOR, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setMajor(getNodeValueAsInteger(currentBeaconItemNode.getFirstChild().getNodeValue()));

                                                } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_MINOR, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setMinor(getNodeValueAsInteger(currentBeaconItemNode.getFirstChild().getNodeValue()));

                                                } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_ADDRESS, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setAddress(currentBeaconItemNode.getFirstChild().getNodeValue());

                                                } else if (hasMatchWithThisNodeName(GPXConstants.POI_BEACON_DEVICE_FIRMWAREVERSION, currentBeaconItemNode)) {

                                                    beaconDeviceOnTheTour.setFirmwareversion(currentBeaconItemNode.getFirstChild().getNodeValue());
                                                }
                                            }

                                            beaconDevicesOnTheTour.add(beaconDeviceOnTheTour);
                                        }

                                    }
                                    ext.setBeaconDevices(beaconDevicesOnTheTour);
                                }
                            }
                        }
                    }
                    waypoint.setExtensions(ext);
                }
            }
        } else {
            Log.d(LOG_TAG, "No Waypoint child nodes found");
        }

        return waypoint;
    }

    private GPXTrack parseGPXTrack(Node node) {
        if (node == null) {
            Log.d(LOG_TAG, "null track node received");
            return null;
        }

        GPXTrack track = new GPXTrack();
        NodeList trChildNodes = node.getChildNodes();
        if (trChildNodes != null) {
            for (int i = 0; i < trChildNodes.getLength(); i++) {
                Node currentNode = trChildNodes.item(i);
                if (GPXConstants.NAME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "name found in track data");
                    track.setTrackName(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.CMT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "comment found in track data");
                    track.setTrackComment(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.DESC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "description found in track data");
                    track.setTrackDescription(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.SRC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "source found in track data");
                    track.setTrackSrc(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.NUMBER_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "number found in track data");
                    track.setTrackNumber(getNodeValueAsInteger(currentNode.getFirstChild().getNodeValue()));
                } else if (GPXConstants.TYPE_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "type found in track data");
                    track.setTrackType(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.TRKSEG_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "trkseg found in track data");
                    GPXTrackSegment trackSegment = parseGPXTrackSegment(currentNode);
                    track.addGPXTrackSegment(trackSegment);
                }
            }
        }

        return track;
    }

    private GPXTrackSegment parseGPXTrackSegment(Node node) {
        if (node == null) {
            Log.d(LOG_TAG, "null tracksegment node received");
            return null;
        }
        GPXTrackSegment trackSegment = new GPXTrackSegment();
        NodeList trksegChildNodes = node.getChildNodes();
        if (trksegChildNodes != null) {
            for (int i = 0; i < trksegChildNodes.getLength(); i++) {
                Node currentNode = trksegChildNodes.item(i);
                if (GPXConstants.TRKPT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "trackpoint found int tracksegment data");
                    GPXWaypoint gpxWaypoint = parseGPXWaypoint(currentNode);
                    if (gpxWaypoint != null) {
                        trackSegment.addGPXTrackPoint(gpxWaypoint);
                    }
                }
            }
        } else {
            Log.d(LOG_TAG, "No Tracksegment child nodes found");
        }

        return trackSegment;
    }

    // parse route
    private GPXRoute parseGPXRoute(Node node) {
        if (node == null) {
            return null;
        }

        GPXRoute route = new GPXRoute();
        NodeList trChildNodes = node.getChildNodes();
        if (trChildNodes != null) {
            for (int i = 0; i < trChildNodes.getLength(); i++) {
                Node currentNode = trChildNodes.item(i);
                if (GPXConstants.NAME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "name found in track data");
                    route.setRouteName(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.CMT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "comment found in track data");
                    route.setRouteComment(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.DESC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "description found in track data");
                    route.setRouteDescription(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.SRC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "source found in track data");
                    route.setRouteSrc(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.NUMBER_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "number found in track data");
                    route.setRouteNumber(getNodeValueAsInteger(currentNode.getFirstChild().getNodeValue()));
                } else if (GPXConstants.TYPE_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "type found in track data");
                    route.setRouteType(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.RTEPT_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "route found in track data");
                    GPXWaypoint gpxWaypoint = parseGPXWaypoint(currentNode);
                    if (gpxWaypoint != null) {
                        route.addGPXRoutePoint(gpxWaypoint);
                    }
                }
            }
        } else {
            Log.d(LOG_TAG, "No Route child nodes found");
        }

        return route;
    }

    private GPXMetadata parseGPXMetada(Node node) {
        if (node == null) {
            Log.d(LOG_TAG, "null metadata node received");
            return null;
        }

        GPXMetadata metadata = new GPXMetadata();
        NodeList metaChildNodes = node.getChildNodes();
        if (metaChildNodes != null) {
            for (int i = 0; i < metaChildNodes.getLength(); i++) {
                Node currentNode = metaChildNodes.item(i);
                if (GPXConstants.NAME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "name found in metadata");
                    metadata.setMetadataName(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.DESC_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "description found in metadata");
                    metadata.setMetadataDescription(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.TIME_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "time found in metadata");
                    metadata.setMetadataTime(getNodeValueAsDate(currentNode.getFirstChild().getNodeValue()));
                } else if (GPXConstants.KEYWORDS_NODE.equals(currentNode.getNodeName())) {
                    Log.d(LOG_TAG, "keywords found in metadata");
                    metadata.setMetadataKeywords(currentNode.getFirstChild().getNodeValue());
                } else if (GPXConstants.AUTHOR_NODE.equals(currentNode.getNodeName())) {
                    // Log.d(LOG_TAG, "keywords found in metadata");
                    GPXMetadata.GPXPersonType personType = new GPXMetadata.GPXPersonType();
                    NodeList authorNodes = currentNode.getChildNodes();
                    if (authorNodes != null) {
                        for (int j = 0; j < authorNodes.getLength(); j++) {
                            Node currentAuthorNode = authorNodes.item(j);
                            if (GPXConstants.NAME_NODE.equals(currentAuthorNode.getNodeName())) {
                                personType.setName(currentAuthorNode.getFirstChild().getNodeValue());
                            }
                        }
                    }
                    metadata.setPersonType(personType);
                } else if (GPXConstants.EXTENSION.equals(currentNode.getNodeName())) {
                    GPXMetadataExtension ext = new GPXMetadataExtension();
                    NodeList extNodes = currentNode.getChildNodes();
                    if (extNodes != null) {
                        for (int j = 0; j < extNodes.getLength(); j++) {
                            Node currentExtNode = extNodes.item(j);

                            if (GPXConstants.STARTPALCE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setStartplace(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.TOURTYPECODE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setTourTypeCode(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.REGIONCODE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setRegionCode(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.STARTDESCRIPTION.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setStartDescription(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.LANGUAGE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setLanguage(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.CONDITION.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setCondition(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.DIFFICULTY.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setDifficulty(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.EXPERIENCE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setExperience(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.LANDSCAPE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setLandscape(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.TECHNICAL.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setTechnical(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.FULLDURATION.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setFullDuration(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.REALDURATION.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setRealDuration(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.DISTANCELONG.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setDistanceLong(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.POICOUNT.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setPoiCount(getNodeValueAsLong(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.STROLLERFRIENDLY.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setStrollerFriendly(getNodeValueAsInteger(currentExtNode.getFirstChild().getNodeValue()));
                            } else if (GPXConstants.MAINCOVERIMAGEID.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null)
                                    ext.setMainCoverImageId(currentExtNode.getFirstChild().getNodeValue());
                            } else if (GPXConstants.COVERIMAGELIST.equals(currentExtNode.getNodeName())) {
                                List<String> coverImages = new ArrayList<String>();
                                NodeList imgageNodes = currentExtNode.getChildNodes();
                                if (imgageNodes != null) {
                                    for (int k = 0; k < imgageNodes.getLength(); k++) {
                                        Node imageNode = imgageNodes.item(k);
                                        coverImages.add(imageNode.getFirstChild().getNodeValue());
                                    }
                                }
                                ext.setCoverImages(coverImages);
                            } else if (GPXConstants.IMAGEMAPFILEID.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null) {
                                    ext.setImageMapFileName(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.START_LATITUDE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null) {
                                    ext.setStartLatitude(Long.parseLong(currentExtNode.getFirstChild().getNodeValue()));
                                }

                            } else if (GPXConstants.START_LONGITUDE.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null) {
                                    ext.setStartLongitude(Long.parseLong(currentExtNode.getFirstChild().getNodeValue()));
                                }
                            } else if (GPXConstants.COUNTRY_NAME.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null) {
                                    ext.setCountryName(currentExtNode.getFirstChild().getNodeValue());
                                }
                            } else if (GPXConstants.AUTHOR.equals(currentExtNode.getNodeName())) {
                                if (currentExtNode.getFirstChild() != null) {
                                    ext.setAuthor(currentExtNode.getFirstChild().getNodeValue());
                                }

                            } else if (GPXConstants.META_DATA_TRANSLATION_LIST.equals(currentExtNode.getNodeName())) {

                                List<GPXMetaDataExtensionTrl> gpxMetaDataExtensionTrlList = new ArrayList<>();

                                NodeList translationNodes = currentExtNode.getChildNodes();

                                if (translationNodes != null) {
                                    for (int translationNodeIndex = 0; translationNodeIndex < translationNodes.getLength(); translationNodeIndex++) {

                                        GPXMetaDataExtensionTrl gpxMetaDataExtensionTrl = new GPXMetaDataExtensionTrl();

                                        NodeList translationItemNodes = translationNodes.item(translationNodeIndex).getChildNodes();

                                        if (translationItemNodes != null) {

                                            for (int h = 0; h < translationItemNodes.getLength(); h++) {

                                                Node currentTranslationItemNode = translationItemNodes.item(h);

                                                if (GPXConstants.TRANSLATED_LANGUAGE_CODE.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setLanguageCode(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.TRANSLATED_LANGUAGE_NAME.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setLanguageName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.TRANSLATED_NAME.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setName(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }

                                                } else if (GPXConstants.TRANSLATED_DESCRIPTION.equals(currentTranslationItemNode.getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setDescription(currentTranslationItemNode.getFirstChild().getNodeValue());
                                                    }
                                                } else if (GPXConstants.TRANSLATED_STARTPLACE.equals(currentTranslationItemNode
                                                        .getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setStartPlace(currentTranslationItemNode.getFirstChild().getNodeValue
                                                                ());
                                                    }
                                                } else if (GPXConstants.TRANSLATED_AUDIOEXIST.equals(currentTranslationItemNode
                                                        .getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setTranslatedAudioExist(Boolean.parseBoolean(currentTranslationItemNode
                                                                .getFirstChild().getNodeValue
                                                                        ()));
                                                    }
                                                } else if (GPXConstants.TRANSLATED_VIDEOEXIST.equals(currentTranslationItemNode
                                                        .getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setTranslatedVideoExist(Boolean.parseBoolean(currentTranslationItemNode
                                                                .getFirstChild().getNodeValue
                                                                        ()));
                                                    }
                                                } else if (GPXConstants.TRANSLATED_EXTERNALVIDEOEXIST.equals(currentTranslationItemNode
                                                        .getNodeName())) {

                                                    if (currentTranslationItemNode.getFirstChild().getNodeValue() != null) {

                                                        gpxMetaDataExtensionTrl.setTranslatedExternalVideoExist(Boolean.parseBoolean(currentTranslationItemNode
                                                                .getFirstChild().getNodeValue
                                                                        ()));
                                                    }
                                                }
                                            }
                                        }
                                        gpxMetaDataExtensionTrlList.add(gpxMetaDataExtensionTrl);
                                    }
                                }
                                ext.setGpxMetaDataExtensionTrlDTOs(gpxMetaDataExtensionTrlList);
                            }
                        }
                    }
                    metadata.setExtensions(ext);

                }
            }
        }

        return metadata;
    }

    private Double getNodeValueAsDouble(String nodeValue) {
        Double doubleValue = null;
        try {
            doubleValue = Double.parseDouble(nodeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doubleValue;
    }


    private Date getNodeValueAsDate(String nodeValue) {
        Date dateValue = null;
        try {
            String checkLastChar = nodeValue.substring(nodeValue.length() - 1);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = null;

            if (checkLastChar.equals("Z")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            } else {
                sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            }
            sdf.setCalendar(cal);
            if (checkLastChar.equals("Z")) {
                cal.setTime(sdf.parse(nodeValue));
            } else {
                cal.setTime(sdf.parse(nodeValue + "Z"));
            }
            dateValue = cal.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateValue;
    }

    private Integer getNodeValueAsInteger(String nodeValue) {
        Integer integerValue = null;
        try {
            integerValue = Integer.parseInt(nodeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integerValue;
    }

    private Long getNodeValueAsLong(String nodeValue) {
        Long longValue = null;
        try {
            longValue = Long.parseLong(nodeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return longValue;
    }

    private boolean hasMatchWithThisNodeName(String nodeName, Node currentBeaconItemNode) {

        if (nodeName == null || currentBeaconItemNode == null || currentBeaconItemNode.getNodeName() == null) {
            return false;
        }

        if (!nodeName.equals(currentBeaconItemNode.getNodeName())) {
            return false;
        }

        if (currentBeaconItemNode.getFirstChild() == null || currentBeaconItemNode.getFirstChild().getNodeValue() == null) {
            return false;
        }

        return true;

    }

}
