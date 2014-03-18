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
import java.io.StringReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/** Tests for the PurchaseReader class. */
public class PurchaseReaderTest {
	@Ignore
	private static class DummyAggregator implements PurchaseAggregator {
		public final Collection<PurchaseRecord> records = new LinkedList<>();

		public DummyAggregator() {

		}

		@Override
		public void add(final String customer, final String item, final Date date, final int amount, final double price) {
			records.add(new PurchaseRecord(customer, item, date, amount, price));
		}
	}

	private DummyAggregator aggregator;

	@Before
	public void initializeAggregator() {
		aggregator = new DummyAggregator();
	}

	@Test
	public void testEmptyInput() throws IOException {
		StringReader empty = new StringReader("");
		PurchaseReader.read(aggregator, empty);
		assertTrue(aggregator.records.isEmpty());
	}

	@Test
	public void testValidInput() throws IOException {
		StringReader records = new StringReader(
			"user1;item1;2014-02-03;1;45.5\n"
			+ "user2;item4;2014-02-18;2;2.3\n");
		Collection<PurchaseRecord> expected = Arrays.asList(
			new PurchaseRecord("user1", "item1", makeDate(2014, 1, 3), 1, 45.5),
			new PurchaseRecord("user2", "item4", makeDate(2014, 1, 18), 2, 2.3)
		);

		PurchaseReader.read(aggregator, records);

		assertEquals(expected, aggregator.records);
	}
	
	@Test
	public void invalidInputIsIgnored() throws IOException {
		StringReader records = new StringReader(
			"user1;item1;2014-02-03;1;XXXXX\n"
			+ "user2;item4;2014-02-18;2;2.3\n");
		Collection<PurchaseRecord> expected = Arrays.asList(
			new PurchaseRecord("user2", "item4", makeDate(2014, 1, 18), 2, 2.3)
		);

		PurchaseReader.read(aggregator, records);

		assertEquals(expected, aggregator.records);
	}

	private static Date makeDate(int year, int month, int day) {
		Calendar cal = new GregorianCalendar(year, month, day);
		return cal.getTime();
	}
}
