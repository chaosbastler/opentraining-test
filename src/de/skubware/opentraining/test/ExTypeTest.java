/**
 * 
 * This is a unit test for OpenTraining, an Android application for planning your your fitness training.
 * Copyright (C) 2012 Christian Skubich
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package de.skubware.opentraining.test;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import android.test.suitebuilder.annotation.SmallTest;
import de.skubware.opentraining.basic.ExerciseType;
import junit.framework.TestCase;

/**
 * JUnit test for  {@link ExerciseType}.
 * 
 * @author Christian Skubich
 */
public class ExTypeTest extends TestCase {

	ExerciseType EX_1;
	ExerciseType EX_2;

	final String EX_1_NAME = "Exercise ONE";
	final String EX_2_NAME = "Exercise TWO";
	
	/** Creates 2 ExerciseTypes before each test */
	public void setUp() throws Exception {
		Set<ExerciseType> list = new TreeSet<ExerciseType>(ExerciseType.listExerciseTypes());
		for (ExerciseType ex : list) {
			ExerciseType.removeExerciseType(ex);
		}

		EX_1 = new ExerciseType.Builder(EX_1_NAME).build();
		EX_2 = new ExerciseType.Builder(EX_2_NAME).build();
	}

	/** Removes all created ExercisesTypes at the end of each test */
	public void tearDown() throws Exception {
		assertTrue(ExerciseType.removeExerciseType(EX_1));
		assertTrue(ExerciseType.removeExerciseType(EX_2));
		
		assertTrue(ExerciseType.listExerciseTypes().isEmpty());
	}

	/** Tests if the constructor is empty */
	public void testConstructorIsPrivate() {
		// constructor must be private
		try {
			Constructor<ExerciseType> cons = ExerciseType.class.getConstructor();
			assertTrue(Modifier.isPrivate(cons.getModifiers()));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/** Compares the hash code of two exercises, that should be equal*/
	public void testHashCode() {
		assertEquals(EX_1_NAME.hashCode(), EX_1.hashCode());
		assertEquals(EX_2_NAME.hashCode(), EX_2.hashCode());
	}
	
	/** Test, if getUnlocalizedName returns the right name */
	@SmallTest
	public void testGetName() {
		assertEquals(EX_1_NAME, EX_1.getUnlocalizedName());
		assertEquals(EX_2_NAME, EX_2.getUnlocalizedName());
	}

	public void testListExerciseTypes() {
		String exes = "";
		for (ExerciseType ex : ExerciseType.listExerciseTypes()) {
			exes += ex.toString();
			exes += "\n";
		}
		assertEquals(exes, 2, ExerciseType.listExerciseTypes().size());

		assertTrue(ExerciseType.listExerciseTypes().contains(EX_1));
		assertTrue(ExerciseType.listExerciseTypes().contains(EX_2));

	}
	
	/** 
	 * Tests if instance control works
	 * That means, if a second object with the same name should be created,
	 * the old object will be returned.
	 */
	public void testInstanceControll() {
		assertSame(EX_1, new ExerciseType.Builder(EX_1_NAME).build());
		assertSame(EX_2, new ExerciseType.Builder(EX_2_NAME).build());
	}

	public void testToString() {
		assertEquals(EX_1_NAME, EX_1.toString());
		assertEquals(EX_2_NAME, EX_2.toString());
	}
	
	public void testRemoveExerciseType() {
		ExerciseType.removeExerciseType(EX_1);
		for(Method m:ExerciseType.class.getMethods()){
			if(m.getParameterTypes().length==0){
				try {
					m.invoke(EX_1);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}	
		}
		
		// finally set up everything again
		try {
			this.setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
