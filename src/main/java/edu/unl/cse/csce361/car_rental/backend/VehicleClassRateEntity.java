package edu.unl.cse.csce361.car_rental.backend;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import java.util.List;

import static edu.unl.cse.csce361.car_rental.backend.ValidationUtil.checkIfItemExistInVehicleClass;

@Entity
public class VehicleClassRateEntity {

    public static final int MAXIMUM_CLASS_TYPE = 100;

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(length = MAXIMUM_CLASS_TYPE)
    private String classType;

    @Column
    private Integer dailyRate;

    public VehicleClassRateEntity(String classType, Integer dailyRate) {
        this.classType = classType;
        this.dailyRate = dailyRate;
    }

    public VehicleClassRateEntity() {
        super();
    }

    public Integer getDailyRate() {
        return dailyRate;
    }

    public static VehicleClassRateEntity getVehicleRateEntityByClassType(Model.VehicleClass selectedClassType) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        selectedClassType = checkIfItemExistInVehicleClass(selectedClassType.toString());
        VehicleClassRateEntity vehicleClassRateEntity = null;
        if (selectedClassType != Model.VehicleClass.UNKNOWN) {
            try {
                vehicleClassRateEntity = session.bySimpleNaturalId(VehicleClassRateEntity.class).load(selectedClassType.toString());
                session.getTransaction().commit();
            } catch (HibernateException exception) {
                System.err.println("Could not load Class type " + selectedClassType.toString() + ". " + exception.getMessage());
                session.getTransaction().rollback();
            }
        } else {
            session.getTransaction().rollback();
        }
        return vehicleClassRateEntity;
    }

    public void setDailyRate(Integer dailyRate) {
        Session session = HibernateUtil.getSession();
        System.out.println("Starting Hibernate transaction...");
        session.beginTransaction();
        try {
            if (dailyRate != null) {
                this.dailyRate = dailyRate;
                session.saveOrUpdate(this);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("error: " + e);
            session.getTransaction().rollback();
        }
    }
}
