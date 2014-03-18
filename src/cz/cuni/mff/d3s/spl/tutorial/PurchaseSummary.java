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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Computes summary for each customer. */
public class PurchaseSummary implements PurchaseAggregator {
	private static class CustomerEntry implements CustomerSummary {
		public final String customer;
		public double totalPrice = 0;
		public final int hash;

		public CustomerEntry(final String c) {
			customer = c;
			if (c != null) {
				hash = c.hashCode();
			} else {
				hash = -1;
			}
		}

		@Override
		public String getCustomer() {
			return customer;
		}

		@Override
		public double getTotalPrice() {
			return totalPrice;
		}

		@Override
		public int hashCode() {
			return hash;
		}

		/* As long as the customer is the same, we consider the objects the same. */
		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof CustomerEntry) {
				CustomerEntry other = (CustomerEntry) obj;
				return customer.equals(other.customer);
			} else {
				return false;
			}
		}
	}

	private final Map<String, CustomerSummary> data = new HashMap<>();

	public PurchaseSummary() {
	}

	@Override
	public void add(final String customer, final String item, final Date date, final int amount, final double price) {
		final double totalPrice = price * amount;
		
		CustomerEntry entry = (CustomerEntry) data.get(customer);
		if (entry == null) {
			entry = new CustomerEntry(customer);
			data.put(customer, entry);
		}
		
		entry.totalPrice += totalPrice;
	}

	public Collection<CustomerSummary> get() {
		return java.util.Collections.unmodifiableCollection(data.values());
	}
}
