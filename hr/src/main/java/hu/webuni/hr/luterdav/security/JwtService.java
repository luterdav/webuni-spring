package hu.webuni.hr.luterdav.security;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.luterdav.config.HrConfigProperties;
import hu.webuni.hr.luterdav.model.Employee;

@Service
public class JwtService {

	private static final String MANAGER = "manager";
	private static final String EMPLOYEES_MANAGED_USERNAMES = "employeesManagedUsernames";
	private static final String EMPLOYEES_MANAGED_IDS = "employeesManagedIds";
	private static final String USERNAME = "username";
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String AUTH = "auth";
	
	@Autowired
	HrConfigProperties config;
	
	private String issuer;
	private Algorithm alg;
	
	@PostConstruct
	public void init() {
		issuer = config.getJwtData().getIssuer();
		
		try {
			alg = (Algorithm) Algorithm.class.getMethod(config.getJwtData().getAlg(), String.class)
					.invoke(Algorithm.class, config.getJwtData().getSecret());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public String creatJwtToken(UserDetails principal) {
		Employee employee = ((EmployeeUser) principal).getEmployee();
		Employee manager = employee.getManager();
		List<Employee> employeesManaged = employee.getEmployeesManaged();

		Builder jwtBuilder = JWT.create().withSubject(principal.getUsername())
				.withArrayClaim(AUTH,
						principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
				.withClaim(ID, employee.getId())
				.withClaim(NAME, employee.getName());

		if (manager != null)
			jwtBuilder.withClaim(MANAGER, Map.of(ID, manager.getId(), 
					USERNAME, manager.getUsername()));

		if (employeesManaged != null && !employeesManaged.isEmpty())
			jwtBuilder
					.withArrayClaim(EMPLOYEES_MANAGED_IDS,
							employeesManaged.stream().map(Employee::getId).toArray(Long[]::new))
					.withArrayClaim(EMPLOYEES_MANAGED_USERNAMES,
							employeesManaged.stream().map(Employee::getUsername).toArray(String[]::new));

		return jwtBuilder.withExpiresAt(new Date(System.currentTimeMillis() + config.getJwtData().getDuration().toMillis()))
				.withIssuer(issuer).sign(alg);

	}

	public UserDetails parseJwt(String jwtToken) {

		DecodedJWT decodedJwt = JWT.require(alg).withIssuer(issuer).build().verify(jwtToken);

		Employee employee = new Employee();
		employee.setId(decodedJwt.getClaim(ID).asLong());
		employee.setName(decodedJwt.getClaim(NAME).asString());
		employee.setUsername(decodedJwt.getSubject());
		
		Claim managerClaim = decodedJwt.getClaim(MANAGER);
		if(managerClaim != null) {
			Employee manager = new Employee();
		    employee.setManager(manager);
		    Map<String, Object> managerData = managerClaim.asMap();
		    if(managerData != null) {
		    	  manager.setId(((Integer)managerData.get(ID)).longValue());
		          manager.setUsername((String)managerData.get(USERNAME));
		    }
		  
		}
		
		Claim employeesManagedIdsClaim = decodedJwt.getClaim(EMPLOYEES_MANAGED_IDS);
		if(employeesManagedIdsClaim != null) {
			employee.setEmployeesManaged(new ArrayList<>());
			List<Long> employeesManagerdIds = employeesManagedIdsClaim.asList(Long.class);
			if(employeesManagerdIds != null && !employeesManagerdIds.isEmpty()) {
				List<String> employeesManagedUsernames = decodedJwt.getClaim(EMPLOYEES_MANAGED_USERNAMES).asList(String.class);
				for (int i = 0; i < employeesManagerdIds.size(); i++) {
					Employee employeeManaged = new Employee();
				    employeeManaged.setId(employeesManagerdIds.get(i));
				    employeeManaged.setUsername(employeesManagedUsernames.get(i));
				    employee.getEmployeesManaged().add(employeeManaged);
				}
				
			}
			
		}

		return new EmployeeUser(decodedJwt.getSubject(), "dummy", decodedJwt.getClaim(AUTH).asList(String.class)
				.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()), employee);

	}

}
