package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DatabaseTest {

    Session session;

    @Before
    public void setUp() {
        session = HibernateUtil.getSession();
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
    public void testGetIndividualCustomerByName() {
        // arrange
        String customerName = "Mary O'Kart";
        String expectedCustomerAddressStart = "1 Racing Road";
        // act
        Customer customer = CustomerEntity.getCustomerByName(customerName);
        // assert
        assertTrue(customer.getAddress().startsWith(expectedCustomerAddressStart));
    }

    @Test
    public void testGetModelsByClass() {
        // arrange
        Model.VehicleClass vehicleClass = Model.VehicleClass.MIDSIZED;
        Set<String> expectedModelNames = Set.of("Malibu", "Model 3");
        // act
        Set<Model> models = ModelEntity.getModelsByClass(vehicleClass);
        Set<String> actualModelNames = models.stream().map(Model::getModel).collect(Collectors.toSet());
        // assert
        assertEquals(expectedModelNames, actualModelNames);
    }
}