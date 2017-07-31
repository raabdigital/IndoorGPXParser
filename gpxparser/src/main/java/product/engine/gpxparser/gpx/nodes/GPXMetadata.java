package product.engine.gpxparser.gpx.nodes;

import java.util.Date;


public class GPXMetadata {

	private String metadataName;
	private String metadataDescription;
	private Date metadataTime;
	private String metadataKeywords;

	//AUTHOR
	private GPXPersonType personType;
	
	private GPXMetdadataExtension extensions;
	
	public GPXMetadata() {
	}


	public GPXMetadata(String authorName, String tourName,
			String tourDescription, Date startTime, GPXMetdadataExtension extensions) {
		this.metadataName = tourName;
		this.metadataDescription = tourDescription;
		this.metadataTime = startTime;
		this.personType = new GPXPersonType(authorName);
		this.extensions = extensions;
	}


	public String getMetadataName() {
		return metadataName;
	}


	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}


	public String getMetadataDescription() {
		return metadataDescription;
	}


	public void setMetadataDescription(String metadataDescription) {
		this.metadataDescription = metadataDescription;
	}


	public Date getMetadataTime() {
		return metadataTime;
	}


	public void setMetadataTime(Date metadataTime) {
		this.metadataTime = metadataTime;
	}


	public String getMetadataKeywords() {
		return metadataKeywords;
	}


	public void setMetadataKeywords(String metadataKeywords) {
		this.metadataKeywords = metadataKeywords;
	}


	public GPXPersonType getPersonType() {
		return personType;
	}


	public void setPersonType(GPXPersonType personType) {
		this.personType = personType;
	}




	public static class GPXPersonType {
		private String name;

		public GPXPersonType() {
		}

		public GPXPersonType(String authorName) {
			this.name = authorName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}


	public GPXMetdadataExtension getExtensions() {
		return extensions;
	}


	public void setExtensions(GPXMetdadataExtension extensions) {
		this.extensions = extensions;
	}
	
	
	
}
