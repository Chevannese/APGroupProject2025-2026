package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "route")
public class Route implements Serializable
{
	
	/**
	 * 
	 */
	
	@Id
	@Column(name = "routeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
	private static final long serialVersionUID = 1L;
	private Integer routeID;
	private String origin;
	private String destination;
	
	
	public Route()
	{
		routeID = 0;
		origin = "";
		destination = "";
	}


	public Route(Integer routeID, String origin, String destination) {
		this.routeID = routeID;
		this.origin = origin;
		this.destination = destination;
	}
	
	public Route(Route r) {
		this.routeID = r.routeID;
		this.origin = r.origin;
		this.destination = r.destination;
	}


	public Integer getRouteID() {
		return routeID;
	}


	public void setRouteID(Integer routeID) {
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
