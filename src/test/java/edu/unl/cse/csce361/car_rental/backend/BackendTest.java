package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BackendTest {

    Backend backend;

    @Before
    public void setUp() {
        backend = Backend.getInstance();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        DatabasePopulator.createModels().forEach(session::saveOrUpdate);
        DatabasePopulator.createCars().forEach(session::saveOrUpdate);
        DatabasePopulator.createCorporateCustomers().forEach(session::saveOrUpdate);
        DatabasePopulator.createIndividualCustomers().forEach(session::saveOrUpdate);
        DatabasePopulator.createRentals(session).forEach(session::saveOrUpdate);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from RentalEntity").executeUpdate();
        session.createQuery("delete from CarEntity").executeUpdate();
        session.createQuery("delete from ModelEntity").executeUpdate();
        session.createQuery("delete from CustomerEntity").executeUpdate();
        session.createQuery("delete from CorporateCustomerEntity").executeUpdate();
        session.createQuery("delete from IndividualCustomerEntity").executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void testGetCustomer() {
        // arrange
        String customerName = "Mary O'Kart";
        String expectedCustomerAddressStart = "1 Racing Road";
        // act
        Customer customer = backend.getCustomer(customerName);
        // assert
        assertTrue(customer.getAddress().startsWith(expectedCustomerAddressStart));
    }

    @Test
    public void testGetModels() {
        // arrange
        String vehicleClass = "Midsized";
        Set<String> expectedModelNames = Set.of("Malibu", "Model 3");
        // act
        Set<Model> models = backend.getModels(vehicleClass);
        Set<String> actualModelNames = models.stream().map(Model::getModel).collect(Collectors.toSet());
        // assert
        assertEquals(expectedModelNames, actualModelNames);
    }

    @Test
    public void testCreateModelWithNewModel() {
        // arrange
        String manufacturer = "Ford";
        String name = "Model A";
        Model.VehicleClass classType = Model.VehicleClass.OTHER;
        int numberOfDoors = 2;
        Model.Transmission transmission = Model.Transmission.MANUAL;
        Model.Fuel fuel = Model.Fuel.GASOLINE;
        int fuelEconomy = 14;
        // act
        Model model = backend.createModel(manufacturer, name, classType, numberOfDoors, transmission, fuel,
                fuelEconomy);
        // assert
        assertEquals(name, model.getModel());
        assertEquals(transmission, model.getTransmission());
    }

    @Test
    public void testCreateModelWithExistingModel() {
        // arrange
        String manufacturer = "Ford";
        String name = "Ranger";
        Model.VehicleClass classType = Model.VehicleClass.TRUCK;
        int numberOfDoors = 2;
        Model.Transmission specifiedTransmission = Model.Transmission.MANUAL;
        Model.Transmission expectedTransmission = Model.Transmission.AUTOMATIC;
        Model.Fuel fuel = Model.Fuel.GASOLINE;
        int fuelEconomy = 26;
        // act
        Model model = backend.createModel(manufacturer, name, classType, numberOfDoors, specifiedTransmission, fuel,
                fuelEconomy);
        // assert
        assertEquals(name, model.getModel());
        assertEquals(expectedTransmission, model.getTransmission());
    }

    @Test public void testGetCurrentCustomer(){
        // arrange
        String customerName = "Mary O'Kart";
        String expectedCurrentCustomer = customerName;
        // act
        Customer customer = backend.getCustomer(customerName);
        Customer currentCustomer = backend.getCurrentCustomer();
        // assert
        assertTrue(currentCustomer.getName().equals(expectedCurrentCustomer));
    }

    @Test
    public void testCreateIndividualCustomer(){
        //arrange
        String name = "Max";
        String streetAddress1 = "home", streetAddress2 = "", city = "Omaha", state = "NH", zip = "25383", creditCardNumber = "1234567891234565", creditCVV = "456";
        int month = 12, year = 2020;
        //act
        Customer customer = Backend.getInstance().createIndividualCustomer(name, streetAddress1, streetAddress2, city, state, zip);
        //assert
        assertEquals(customer.getName(),name);
    }
}