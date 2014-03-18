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

import java.util.ArrayList;
import java.util.List;

/** Helper class for quick testing that generator produces expected arguments. */
public class GeneratorPrinter {
	public static void main(String args[]) {
		final Iterable<Object[]> data = PurchaseReaderSplTest.generator(12);

		if (!data.iterator().hasNext()) {
			System.out.printf("The iterable does not contain any data.\n");
			return;
		}
		
		int index = -1;
		List<Class<?>> parameterTypes = null;
		
		for (Object[] params : data) {
			index++;

			if (index == 0) {
				parameterTypes = getClassesAndPrintThem(params);
			}

			System.out.printf("%5d: { ", index);
			printCommaSeparate(params);
			System.out.printf(" }\n");

			if (index > 0) {
				checkParameters(params, parameterTypes);				
			}
		}
	}
	
	private static List<Class<?>> getClassesAndPrintThem(final Object[] params) {
		final List<Class<?>> parameterTypes = new ArrayList<>(params.length);
		
		final List<String> parameterClassNames = new ArrayList<>(params.length);
		for (Object o : params) {
			parameterTypes.add(o.getClass());
			parameterClassNames.add(o.getClass().getCanonicalName());
		}
		
		System.out.printf("Types: { ");
		printCommaSeparate(parameterClassNames.toArray());
		System.out.printf(" }\n");
		
		return parameterTypes;
	}
	
	private static void checkParameters(final Object[] params, final List<Class<?>> expectedClasses) {
		if (expectedClasses.size() != params.length) {
			System.out.printf("Warning: the above differs in length from the first arguments.\n");
			return;
		}
		
		for (int i = 0; i < params.length; i++) {
			Class<?> paramClass = params[i].getClass();
			if (!paramClass.equals(expectedClasses.get(i))) {
				System.out.printf("Warning: argument %d has different class (%s).\n", i + 1, paramClass.getCanonicalName());
			}
		}
	}
	
	private static void printCommaSeparate(final Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			System.out.printf("%s", objs[i].toString());
			if (i + 1 < objs.length) {
				System.out.printf(", ");
			}
		}
	}
}
