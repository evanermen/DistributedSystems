package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;

import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;

public class Client extends AbstractScriptedSimpleTest {

	private ICarRentalCompany company;


	/********
	 * MAIN *
	 ********/

	public static void main(String[] args) throws Exception {
		if (System.getSecurityManager() != null) {
			System.setSecurityManager(null);
		}
		try {
			String carRentalCompanyName = "Hertz";

			Registry registry = LocateRegistry.getRegistry("localhost"); //stond argument: args[0]
			ICarRentalCompany companyObject = (ICarRentalCompany) registry.lookup(carRentalCompanyName);

			// An example reservation scenario on car rental company 'Hertz' would be...
			Client client = new Client("simpleTrips", companyObject);
			client.run();
		} catch (Exception e) {
			System.err.println("Client exception:");
			e.printStackTrace();
		}
	}    


	/***************
	 * CONSTRUCTOR *
	 ***************/

	public Client(String scriptFile, ICarRentalCompany company) {
		super(scriptFile);
		this.company = company;
	}

	/**
	 * Check which car types are available in the given period
	 * and print this list of car types.
	 *
	 * @param 	start
	 * 			start time of the period
	 * @param 	end
	 * 			end time of the period
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected void checkForAvailableCarTypes(Date start, Date end) throws Exception {
		String types = company.getAvailableCarTypesString(start, end);
		System.out.println(types);
	}

	/**
	 * Retrieve a quote for a given car type (tentative reservation).
	 * 
	 * @param	clientName 
	 * 			name of the client 
	 * @param 	start 
	 * 			start time for the quote
	 * @param 	end 
	 * 			end time for the quote
	 * @param 	carType 
	 * 			type of car to be reserved
	 * @return	the newly created quote
	 *  
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Quote createQuote(String clientName, Date start, Date end, String carType) throws Exception {
		ReservationConstraints constraints = new ReservationConstraints(start, end, carType);
		Quote quote = company.createQuote(constraints, clientName);
		return quote;
	}

	/**
	 * Confirm the given quote to receive a final reservation of a car.
	 * 
	 * @param 	quote 
	 * 			the quote to be confirmed
	 * @return	the final reservation of a car
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Reservation confirmQuote(Quote quote) throws Exception {
		Reservation reservation = company.confirmQuote(quote);
		return reservation;
	}

	/**
	 * Get all reservations made by the given client.
	 *
	 * @param 	clientName
	 * 			name of the client
	 * @return	the list of reservations of the given client
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected List<Reservation> getReservationsBy(String clientName) throws Exception {
		return company.getReservationsBy(clientName);

	}

	/**
	 * Get the number of reservations for a particular car type.
	 * 
	 * @param 	carType 
	 * 			name of the car type
	 * @return 	number of reservations for the given car type
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected int getNumberOfReservationsForCarType(String carType) throws Exception {
		return company.getNumberOfReservationsForCarType(carType);
	}
}