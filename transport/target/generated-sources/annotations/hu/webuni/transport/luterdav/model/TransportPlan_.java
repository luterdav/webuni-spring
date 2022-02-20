package hu.webuni.transport.luterdav.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransportPlan.class)
public abstract class TransportPlan_ {

	public static volatile SingularAttribute<TransportPlan, Integer> revenue;
	public static volatile ListAttribute<TransportPlan, Section> section;
	public static volatile SingularAttribute<TransportPlan, Long> id;

	public static final String REVENUE = "revenue";
	public static final String SECTION = "section";
	public static final String ID = "id";

}

