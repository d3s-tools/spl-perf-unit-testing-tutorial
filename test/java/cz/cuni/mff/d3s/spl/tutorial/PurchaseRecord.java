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

import java.util.Date;

/** Simplified entry of a single purchase. */
class PurchaseRecord {
	/** Customer name (login). */
	public final String customer;
	/** Item that was bought (assume internal id). */
	public final String item;
	/** Date and time of the purchase. */
	public final Date date;
	/** Amount of items the customer bought. */
	public final int amount;
	/** Price of the single item at the time of the transaction. */
	public final double price;
	
	public PurchaseRecord(final String customer, final String item, final Date date, final int amount,
			final double price) {
		this.customer = customer;
		this.item = item;
		this.date = date;
		this.amount = amount;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return String.format("PurchaseRecord[%s bought %dx %s at %s for %.2f]",
				customer, amount, item, date, price);
	}

	/* Eclipse-generated. */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* Eclipse-generated. */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseRecord other = (PurchaseRecord) obj;
		if (amount != other.amount)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}
}
