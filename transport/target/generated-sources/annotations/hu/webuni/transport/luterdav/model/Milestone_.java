package hu.webuni.transport.luterdav.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Milestone.class)
public abstract class Milestone_ {

	public static volatile SingularAttribute<Milestone, Address> address;
	public static volatile SingularAttribute<Milestone, LocalDateTime> plannedTime;
	public static volatile SingularAttribute<Milestone, Long> id;

	public static final String ADDRESS = "address";
	public static final String PLANNED_TIME = "plannedTime";
	public static final String ID = "id";

}

