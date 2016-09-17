package main;

import java.io.IOException;
import java.net.URISyntaxException;

import companies.PopulateCompanyNames;

public class Main {
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		PopulateCompanyNames.worker(false, true);
	}

}
