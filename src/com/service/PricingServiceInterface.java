package com.service;

import java.io.IOException;

import com.exceptions.ElementAlreadyExistsException;
import com.exceptions.NoPricingException;
import com.models.Pricing;
import com.models.Reservation;

public interface PricingServiceInterface {

	public Double calculatePricing(Reservation reservation) throws IOException, NoPricingException;

	public Pricing[] getPricing(Boolean isRoom, String[] roomTypes) throws IOException;

	public void deletePricing(Pricing pricing) throws IOException;

	public void addPricing(Pricing pricing) throws IOException, ElementAlreadyExistsException;

}
