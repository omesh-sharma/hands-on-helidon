package io.helidon.examples.quickstart.mp;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/companies")
public class CompanyResource {

	private final String company_name;
	private final String company_address;

	@Inject
	public CompanyResource(@ConfigProperty(name = "company.name") String company_name,
			@ConfigProperty(name = "company.address") String company_address) {
		super();
		this.company_name = company_name;
		this.company_address = company_address;
	}
	
	@Inject
	@ConfigProperty(name = "company.id")
	private int company_id;
	
	@Inject
	@ConfigProperties(prefix="data1")
	/// Or
	//@Any
	DemoConfig config;
	

	
	@GET
	@Path("/config")
	public DemoConfig getCompanyConfigDetails()
	{
		return config;
	}
	
	@Inject
	@ConfigProperties(prefix="data2")
	DemoConfig config2;
	
	@GET
	@Path("/config1")
	public DemoConfig getCompanyConfigDetails1()
	{
		return config2;
	}
	
	
	
	@GET 
	public  Company getCompanyDetails()
	{
		return new Company(company_name, company_address, company_id);
	}
	
}
