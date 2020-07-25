package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.checkIfItemExistInVehicleClass;

@Entity
public class VehicleClassRateEntity {

    public static final int MAXIMUM_CLASS_TYPE = 100;

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column (length = MAXIMUM_CLASS_TYPE)
    private Model.VehicleClass classType;

    @Column
    private Integer dailyRate;

    public VehicleClassRateEntity(Model.VehicleClass classType, Integer dailyRate){
        this.classType = classType;
        this.dailyRate = dailyRate;
    }

    public static String getDailyRateByClassType(Model.VehicleClass selectedClassType){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String dailyRate = "";
        try {
            selectedClassType = checkIfItemExistInVehicleClass(selectedClassType.toString());
            if(selectedClassType != Model.VehicleClass.UNKNOWN){
                dailyRate = session.createQuery("from VehicleClassRateEntity where classType = " + selectedClassType.ordinal(), VehicleClassRateEntity.class).getSingleResult().toString();
                session.getTransaction().commit();
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load Class type " + selectedClassType.toString() + ". " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return dailyRate;
    }
}
