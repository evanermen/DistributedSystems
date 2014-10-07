package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ICarRentalCompany extends Remote {
	
	public String getAvailableCarTypesString(Date start, Date end) throws RemoteException;

	public Quote createQuote(ReservationConstraints constraints, String client) throws ReservationException, RemoteException;
	
	public Reservation confirmQuote(Quote quote) throws ReservationException, RemoteException;
	
	public List<Reservation> getReservationsBy(String clientName) throws RemoteException;

	public int getNumberOfReservationsForCarType(String carType) throws RemoteException;
}
