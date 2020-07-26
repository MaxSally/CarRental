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
    @Column (length = MAXIMUM_CLASS_TYPE)
    private String classType;

    @Column
    private Integer dailyRate;

    public VehicleClassRateEntity(String classType, Integer dailyRate){
        this.classType = classType;
        this.dailyRate = dailyRate;
    }

    public Integer getDailyRate(){
        return dailyRate;
    }

    public static Integer getDailyRateByClassType(Model.VehicleClass selectedClassType){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Integer dailyRate = 0;
        try {
            selectedClassType = checkIfItemExistInVehicleClass(selectedClassType.toString());
            if(selectedClassType != Model.VehicleClass.UNKNOWN){
                VehicleClassRateEntity vehicleClassRateEntity = session.bySimpleNaturalId(VehicleClassRateEntity.class).load(selectedClassType.toString());
                dailyRate = vehicleClassRateEntity.getDailyRate();
                session.getTransaction().commit();
            }
        } catch (HibernateException exception) {
            System.err.println("Could not load Class type " + selectedClassType.toString() + ". " + exception.getMessage());
            session.getTransaction().rollback();
        }
        return dailyRate;
    }
}
