package hu.webuni.transport.luterdav.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Section.class)
public abstract class Section_ {

	public static volatile SingularAttribute<Section, Integer> number;
	public static volatile SingularAttribute<Section, Milestone> fromMilestone;
	public static volatile SingularAttribute<Section, TransportPlan> transportPlan;
	public static volatile SingularAttribute<Section, Milestone> toMilestone;
	public static volatile SingularAttribute<Section, Long> id;

	public static final String NUMBER = "number";
	public static final String FROM_MILESTONE = "fromMilestone";
	public static final String TRANSPORT_PLAN = "transportPlan";
	public static final String TO_MILESTONE = "toMilestone";
	public static final String ID = "id";

}

