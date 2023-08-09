package io.helidon.examples.quickstart.mp;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import jakarta.enterprise.context.ApplicationScoped;

@ConfigProperties(prefix = "data1")
@ApplicationScoped
public class DemoConfig {
	private String name;
	private int age;
	private double price;
	
	public DemoConfig() {
		// TODO Auto-generated constructor stub
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
