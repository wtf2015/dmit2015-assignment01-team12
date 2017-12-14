package security.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;


import northwind.data.ShipperRepository;
import northwind.model.Shipper;

@Stateless
public class ShipperService {
	@Inject
	private Logger log;
	
	@Inject
	private ShipperRepository shipperRepository;
	
	public List<Shipper> findAll() {
		return shipperRepository.findAll();
	}
	
	public Shipper findOne(int shipperID) {
		Shipper currentShipper = null;
		try {
			currentShipper = shipperRepository.find(shipperID);
		} catch(NoResultException e) {
			log.fine(e.getMessage());
		}
		return currentShipper;
	}
	
	public void createShipper(Shipper newShipper) {
		shipperRepository.persist(newShipper);
	}
	
	public Shipper updateShipper(Shipper currentShipper) {
		return shipperRepository.edit(currentShipper);
	}
	
	public void deleteShipper(int shipperID) {
		Shipper currentShipper = findOne(shipperID);
		if (currentShipper != null) {
			shipperRepository.remove(currentShipper);
		}
	}
}
