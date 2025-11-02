package model;

public class Route 
{
	private String routeID;
	private String origin;
	private String destination;
	
	
	public Route()
	{
		routeID = "";
		origin = "";
		destination = "";
	}


	public Route(String routeID, String origin, String destination) {
		this.routeID = routeID;
		this.origin = origin;
		this.destination = destination;
	}
	
	public Route(Route r) {
		this.routeID = r.routeID;
		this.origin = r.origin;
		this.destination = r.destination;
	}


	public String getRouteID() {
		return routeID;
	}


	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	
	
	

	
}
