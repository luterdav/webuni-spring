package hu.webuni.transport.luterdav.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> zipcode;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> countryCode;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, Double> latitude;
	public static volatile SingularAttribute<Address, String> houseNumber;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile ListAttribute<Address, Milestone> milestones;
	public static volatile SingularAttribute<Address, Double> longitude;

	public static final String ZIPCODE = "zipcode";
	public static final String CITY = "city";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String STREET = "street";
	public static final String LATITUDE = "latitude";
	public static final String HOUSE_NUMBER = "houseNumber";
	public static final String ID = "id";
	public static final String MILESTONES = "milestones";
	public static final String LONGITUDE = "longitude";

}

