package northwind.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import northwind.model.Shipper;
import security.service.ShipperService;


@Path("webapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NorthwindWebAPI {
	@Inject
	private ShipperService shipperService;
	 	
	@Path("shippers")
	@GET
	public List<Shipper> findAllShipper() {
		return shipperService.findAll();
	}
	
	@Path("shippers/{id}")
	@GET
	public Shipper findOneShipper(@PathParam("id") int shipperID) {
		return shipperService.findOne(shipperID);
	}

	@Path("shippers")
	@POST
	public void createShipper(Shipper newShipper) {
		shipperService.createShipper(newShipper);
	}
	
	@Path("shippers")
	@PUT
	public void updateShipper(Shipper currentShipper) {
		shipperService.updateShipper(currentShipper);
	}

	@Path("shippers/{id}")
	@DELETE
	public void deleteShipper(@PathParam("id") int shipperID) {
		shipperService.deleteShipper(shipperID);
	}
}
