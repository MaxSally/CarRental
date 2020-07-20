package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.MappingException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static edu.unl.cse.csce361.car_rental.backend.Model.Fuel.*;
import static edu.unl.cse.csce361.car_rental.backend.Model.Transmission.*;
import static edu.unl.cse.csce361.car_rental.backend.Model.VehicleClass.*;

public class DatabasePopulator {

    static Set<Model> createModels() {
        System.out.println("Creating car models...");
        return Set.of(
                new ModelEntity("Triumph", "Stag", COMPACT, 2, MANUAL, GASOLINE, 19),
                new ModelEntity("Austin", "Allegro", COMPACT, 4, MANUAL, GASOLINE, 28),
                new ModelEntity("Acura", "ILX", COMPACT, 4, MANUAL, GASOLINE, 34),
                new ModelEntity("Mercury", "Montego", LARGE, 4, AUTOMATIC, GASOLINE, 29),
                new ModelEntity("Chevrolet", "Malibu", MIDSIZED, 4, AUTOMATIC, GASOLINE, 36),
                new ModelEntity("Chrysler", "Pacifica", MINIVAN, 5, AUTOMATIC, GASOLINE, 28),
                new ModelEntity("Nissan", "Versa", ECONOMY, 4, AUTOMATIC, GASOLINE, 40),
                new ModelEntity("Toyota", "Highlander", SUV, 5, AUTOMATIC, GASOLINE, 29),
                new ModelEntity("Ford", "Ranger", TRUCK, 2, AUTOMATIC, GASOLINE, 26),
                new ModelEntity("Volkswagen", "Jetta", COMPACT, 4, MANUAL, DIESEL, 40),
                new ModelEntity("Tesla", "Model 3", MIDSIZED, 4, AUTOMATIC, PLUGIN_ELECTRIC, 132),
                new ModelEntity("Chevrolet", "Bolt", ECONOMY, 5, AUTOMATIC, PLUGIN_ELECTRIC, 119)
        );
    }

    @SuppressWarnings("SpellCheckingInspection")
    static Set<Car> createCars() {
        System.out.println("Creating cars...");
        return Set.of(
                new CarEntity("Ranger", "Blue", "123 ABC", "1234567890ABCDEFG"),
                new CarEntity("Malibu", "Gray", "456 DEF", "HJKLMNPRSTUVWXYZ1"),
                new CarEntity("Malibu", "Red", "789 GHI", "234567890ABCDEFGH"),
                new CarEntity("ILX", "Red", "AB12", "PL0KM9JN8UHB7YGV6"),
                new CarEntity("Pacifica", "Red", "34CD", "TFC5RDX4ESZ3WA21Z"),
                new CarEntity("Versa", "Red", "GBR1", "XCVBNMASDFGHJKLWE"),
                new CarEntity("Highlander", "Red", "GBR2", "RTYUP12345678901A"),
                new CarEntity("Jetta", "Red", "HUZKRR", "Z2WSX3EDC4RFV5TGB"),
                new CarEntity("Bolt", "Red", "JAVA", "6YHN7UJM8K9L0P1A2"),
                new CarEntity("ILX", "Red", "SEEPLUS", "S3D4F5G6H7J8K9L01"),
                new CarEntity("Pacifica", "White", "PYTHON", "2Z3X4C5V6B7N8M9KL"),
                new CarEntity("Versa", "White", "RUBY", "ASDFGHJJKLZXCVBNM"),
                new CarEntity("Highlander", "White", "JSCRIPT", "12345678909876543"),
                new CarEntity("Jetta", "White", "ADA", "AZSXDCFVGBHNJMKLT"),
                new CarEntity("Bolt", "White", "PASCAL", "ACW3ACCACA23TVAS4"),
                new CarEntity("ILX", "White", "4TRAN", "9283017465LAKSJDH"),
                new CarEntity("Pacifica", "White", "BASIC", "FGMZNXBCVPWUEYRT5"),
                new CarEntity("Versa", "Fuchsia", "ALGOL", "HELL0W0RLDF00BARB"),
                new CarEntity("Highlander", "Magenta", "ASM", "AZ9UUXPLUGHXYZZYW"),
                new CarEntity("Jetta", "Cyan", "LISP", "1BBLEW0BBLET1MEYW"),
                new CarEntity("Bolt", "Yellow", "SCHEME", "1MEYG00DBYE543210")
        );
    }

    static Set<Customer> createCorporateCustomers() {
        System.out.println("Creating corporate customers...");
        return Set.of(
                new CorporateCustomerEntity("Cornhusker Airlines", "123 Airport Avenue", "",
                        "Lincoln", "NE", "68524", "923410", 0.75),
                new CorporateCustomerEntity("Exciting Emulators", "345 Small Street", "Suite 543",
                        "Omaha", "NE", "68007", "341039", 0.9),
                new CorporateCustomerEntity("Procrastination Pastimes", "678 Lazy Lane", "",
                        "Kearney", "NE", "68845", "748302", 0.85),
                new CorporateCustomerEntity("Bohn's Drones", "901 Diving Drive", "Hangar 18",
                        "Seward", "NE", "68434", "7897234", 1.0),
                new CorporateCustomerEntity("City of Pacopolis", "234 Winding Way", "",
                        "Pacopolis", "NE", "68000", "92872187", 0.9),
                new CorporateCustomerEntity("University of NeverLand", "33 College Court", "",
                        "NeverLand", "NE", "69999", "789079123", 0.75),
                new CorporateCustomerEntity("Archie's Pleistocene Petting Zoo", "22 La Brea Boulevard", "",
                        "Royal", "NE", "68773", "0986123", 0.525)
        );
    }

    @SuppressWarnings("SpellCheckingInspection")
    static Set<Customer> createIndividualCustomers() {
        System.out.println("Creating individual customers...");
        return Set.of(
                new IndividualCustomerEntity("Stu Dent", "Fawlty Tower", "Room SB32",
                        "NeverLand", "NE", "69999"),
                new IndividualCustomerEntity("Dee Veloper", "8 Infinite Loop", "",
                        "Lincoln", "NE", "68123",
                        "1234567809876543", 6, 2029, "9021"),
                new IndividualCustomerEntity("Pat Mann", "234 Winding Way", "Executive Mansion",
                        "Pacopolis", "NE", "68000",
                        "0864213579019283", 3, 2025, "9988"),
                new IndividualCustomerEntity("Dawn Keykong", "938 Towering Terrace", "Upper Girder",
                        "Omaha", "NE", "68013",
                        "9988776655443322", 11, 2027, "1234"),
                new IndividualCustomerEntity("Mary O'Kart", "1 Racing Road", "",
                        "Seward", "NE", "68434"),
                new IndividualCustomerEntity("Louie Gee", "96 Plumbers Parkway", "",
                        "Beatrice", "NE", "68310"),
                new IndividualCustomerEntity("Zack Suhn", "111 Sega Circle", "",
                        "Kearney", "NE", "68847",
                        "7272636354549292", 1, 2024, "455"),
                new IndividualCustomerEntity("Q. Burte", "4 Hopalong Highway", "",
                        "Ashland", "NE", "68003")
        );
    }

    @SuppressWarnings("SpellCheckingInspection")
    static Set<RentalEntity> createRentals(Session session) {
        System.out.println("Creating rentals...");
        Car car;
        Customer customer;
        RentalEntity rental;
        Set<RentalEntity> rentals = new HashSet<>();
        // first individual rental (returned)
        car = session.bySimpleNaturalId(CarEntity.class).load("HJKLMNPRSTUVWXYZ1"); // Malibu, "456 DEF" plate
        customer = session.bySimpleNaturalId(CustomerEntity.class).load("Stu Dent");
        rental = customer.rentCar(car);
        rental.setDailyRate(4000);
        rental.setRentalStart(LocalDate.of(2020, 1, 15));
        rental.setRentalEnd(LocalDate.of(2020, 1, 20));
        long paymentDue = rental.makePayment(4000 * (20 - 15));
        assert paymentDue == 0;
        assert customer instanceof IndividualCustomer;
        rentals.add(rental);
        // second individual rental (active)
        car = session.bySimpleNaturalId(CarEntity.class).load("2Z3X4C5V6B7N8M9KL"); // Pacifica, "PYTHON" plate
        customer = session.bySimpleNaturalId(CustomerEntity.class).load("Stu Dent");
        rental = customer.rentCar(car);
        rental.setDailyRate(5000);
        rental.setRentalStart(LocalDate.of(2020, 8, 10));
        rental.makePayment(5000);
        assert customer instanceof IndividualCustomer;
        rentals.add(rental);
        // first corporate rental (active)
        car = session.bySimpleNaturalId(CarEntity.class).load("HJKLMNPRSTUVWXYZ1"); // Malibu, "456 DEF" plate
        customer = session.bySimpleNaturalId(CustomerEntity.class).load("Archie's Pleistocene Petting Zoo");
        rental = customer.rentCar(car);
        rental.setDailyRate(2100);
        rental.setRentalStart(LocalDate.of(2020, 7, 31));
        rental.makePayment(2100);
        assert customer instanceof CorporateCustomer;
        rentals.add(rental);
        // second corporate rental (active)
        car = session.bySimpleNaturalId(CarEntity.class).load("FGMZNXBCVPWUEYRT5"); // Pacifica, "BASIC" plate
        customer = session.bySimpleNaturalId(CustomerEntity.class).load("Archie's Pleistocene Petting Zoo");
        rental = customer.rentCar(car);
        rental.setDailyRate(2625);
        rental.setRentalStart(LocalDate.of(2020, 7, 31));
        rental.makePayment(2625);
        assert customer instanceof CorporateCustomer;
        rentals.add(rental);
        return rentals;
    }

    public static void main(String[] args) {
        System.out.println("Creating Hibernate session...");
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            createModels().forEach(session::saveOrUpdate);
            createCars().forEach(session::saveOrUpdate);
            createCorporateCustomers().forEach(session::saveOrUpdate);
            createIndividualCustomers().forEach(session::saveOrUpdate);
            createRentals(session).forEach(session::saveOrUpdate);
            System.out.println("Concluding Hibernate transaction...");
            session.getTransaction().commit();
            System.out.println("Success! The database has been populated.");
        } catch (MappingException mappingException) {
            System.err.println("Problem encountered when creating a table. The most likely problem is a missing\n" +
                    "    <mapping class=\"...\"/> tag in hibernate.cfg.xml, but it's possible the\n" +
                    "    problem is that the programmer is attempting to load an interface instead\n" +
                    "    of an entity.");
            StackTraceElement[] trace = mappingException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + mappingException.getMessage());
            session.getTransaction().rollback();
        } catch (PersistenceException persistenceException) {
            System.err.println("Problem encountered when populating a table. The most likely problem is that\n" +
                    "    you're running this populator without dropping the existing tables first.");
            StackTraceElement[] trace = persistenceException.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + persistenceException.getMessage());
            System.err.println("  " + persistenceException.getCause().getCause().getMessage());
            session.getTransaction().rollback();
        } catch (Exception exception) {
            System.err.println("Problem encountered that (probably) has nothing to do with creating and\n" +
                    "    populating tables. This is (most likely) is a plain, old-fashioned\n" +
                    "    programming boo-boo.");
            StackTraceElement[] trace = exception.getStackTrace();
            System.err.println("  " + trace[trace.length - 1]);
            System.err.println("  " + exception.getMessage());
            session.getTransaction().rollback();
        } finally {
            HibernateUtil.closeSession(session);
        }
    }
}
