/*
 * Copyright 2013 Charles University in Prague
 * Copyright 2013 Vojtech Horky
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.cuni.mff.d3s.spl.tutorial;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/** Does the summary for semicolon separated input read from stdin. */
public class Main {	
	public static void main(String[] args) throws IOException {
		final PurchaseSummary summary = new PurchaseSummary();
		
		PurchaseReader.read(summary, new InputStreamReader(System.in));
		
		final Collection<CustomerSummary> results = summary.get();
		
		for (CustomerSummary entry : results) {
			System.out.printf("%s;%.2f\n", entry.getCustomer(), entry.getTotalPrice());
		}
	}
}
