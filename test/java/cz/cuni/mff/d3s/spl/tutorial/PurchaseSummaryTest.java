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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/** Tests for the PurchaseSummary class. */
public class PurchaseSummaryTest {
	private PurchaseSummary summary;
	
	@Before
	public void initializeSummary() {
		summary = new PurchaseSummary();
	}
	
	@Test
	public void testEmptyInput() throws IOException {
		assertTrue(summary.get().isEmpty());
	}

	@Test
	public void testMultiplicationWorks() {
		summary.add("user1", "item1", new Date(), 2, 4.5);
		
		Collection<CustomerSummary> records = summary.get();
		
		checkSummary(records, new String[] {"user1"}, new double[] {9.});
	}
	
	@Test
	public void testSummingWorks() {
		summary.add("user1", "item1", new Date(), 1, 0.5);
		summary.add("user1", "item2", new Date(), 2, 1.2);
		summary.add("user2", "item1", new Date(), 2, 4.1);				
		
		Collection<CustomerSummary> records = summary.get();
		
		checkSummary(records, new String[] {"user1", "user2"}, new double[] {2.9, 8.2});
	}
		
	private static void checkSummary(Collection<CustomerSummary> records,
			String[] customersInput, double sums[]) {
		assertEquals(customersInput.length, sums.length);
		
		String[] customers = Arrays.copyOf(customersInput, customersInput.length);		
		for (CustomerSummary rec : records) {
			int i = 0;
			for (; i < customers.length; i++) {
				if (rec.getCustomer().equals(customers[i])) {
					break;
				}
			}
			if (i >= customers.length) {
				fail(String.format("Customer %s not supposed to be present.", rec.getCustomer()));
			}
			assertEquals(sums[i], rec.getTotalPrice(), 0.001);
			customers[i] = null;
		}
		
		for (String c : customers) {
			if (c != null) {
				fail(String.format("Customer %s not found.", c));
			}
		}
	}
}
