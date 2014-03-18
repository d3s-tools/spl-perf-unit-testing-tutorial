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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.spl.SPL;

public class PurchaseReaderSplTest {
	public static class DummyPurchaseAggregator implements PurchaseAggregator {
		double lastPrice;
		@Override
		public void add(String customer, String item, Date date, int amount,
				double price) {
			lastPrice = price;
		}
	}
	
	public static Iterable<Object[]> generator(int items) {
		List<Object[]> result = new ArrayList<>(1);
		
		PurchaseAggregator dummyAggregator = new DummyPurchaseAggregator();
		
		StringBuilder emulatedFile = new StringBuilder();
		
		for (int i = 0; i < items; i++) {
			String line = String.format("customer%d;item17;2014-02-05;1;11.5\n", i + 1);
			emulatedFile.append(line);
		}
		
		result.add(new Object[] { dummyAggregator, new StringReader(emulatedFile.toString()) });
		
		return result;
	}
	
		
	@SPL(
		generators = {
			"gen=GENERATORS@reader:cz.cuni.mff.d3s.spl.tutorial.PurchaseReaderSplTest#generator()"
		},
		methods = {
			"string=SELF@init:cz.cuni.mff.d3s.spl.tutorial.PurchaseReader#read",
			"regexp=SELF@regexp:cz.cuni.mff.d3s.spl.tutorial.PurchaseReader#read"
		},
		formula = {
			"for (count {10, 100, 500, 1000}) string[gen](count) > regexp[gen](count)"
		}
	)
	public static void precompilingRegExpIsFaster() {
		/* Do nothing, just a holder class for the SPL formula. */
	}
}
