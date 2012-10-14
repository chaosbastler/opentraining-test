/**
 * 
 * This is OpenTraining, an Android application for planning your your fitness training.
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

import java.util.*;


import de.skubware.opentraining.basic.*;

import junit.framework.TestCase;


public class WorkoutTest extends TestCase {
	
	String TEST_NAME_1 = "Test Workout 1";
	String TEST_NAME_2 = "Test Workout 2";

	List<FitnessExercise> FEX_LIST_1;
	List<FitnessExercise> FEX_LIST_2;
	
	ExerciseType EX_1;
	ExerciseType EX_2;
	ExerciseType EX_3;

	FitnessExercise FEX_1;
	FitnessExercise FEX_2;
	FitnessExercise FEX_3;

	
	Workout WORKOUT_1;
	Workout WORKOUT_2;

	
	public void setUp(){
		
		// ExerciseTypes
		EX_1 = new ExerciseType.Builder("Test Exercise 1").build();
		EX_2 = new ExerciseType.Builder("Test Exercise 2").build();
		EX_3 = new ExerciseType.Builder("Test Exercise 3").build();
		
		// FitnessExercises
		FEX_1 = new FitnessExercise(EX_1);
		FEX_2 = new FitnessExercise(EX_2);
		FEX_3 = new FitnessExercise(EX_3);
		
		// create and fill lists
		FEX_LIST_1 = new ArrayList<FitnessExercise>();
		FEX_LIST_2 = new ArrayList<FitnessExercise>();
		
		FEX_LIST_1.add(FEX_1);
		FEX_LIST_1.add(FEX_2);
		FEX_LIST_1.add(FEX_3);

		
		FEX_LIST_2.add(FEX_2);
		FEX_LIST_2.add(FEX_3);
		
		WORKOUT_1 = new Workout(TEST_NAME_1, FEX_LIST_1);		
		WORKOUT_2 = new Workout(TEST_NAME_2, FEX_LIST_2);
	}
	
	
	@Override
	public void tearDown(){
		assertTrue(ExerciseType.removeExerciseType(EX_1));
		assertTrue(ExerciseType.removeExerciseType(EX_2));
		assertTrue(ExerciseType.removeExerciseType(EX_3));

		
		assertTrue(ExerciseType.listExerciseTypes().isEmpty());
	}
	
	
	/**
	 * Using @Test(expected=NullPointerException.class) does not work.
	 */
	public void testEmptyConstructorArguments(){
		try{
			new Workout(null);
			fail("Expected NullPointerException");
		}catch(NullPointerException ex){ }
		try{
			new Workout(TEST_NAME_1);
			fail("Expected NullPointerException");
		}catch(NullPointerException ex){ }
		try{
			new Workout(TEST_NAME_1, new FitnessExercise[1]);
			fail("Expected NullPointerException");
		}catch(NullPointerException ex){ }
		try{
			new Workout(TEST_NAME_1, new ArrayList<FitnessExercise>());
			fail("Expected NullPointerException");
		}catch(NullPointerException ex){ }
		try{
			new Workout(TEST_NAME_1, Arrays.asList(new ExerciseType[1]));
			fail("Expected NullPointerException");
		}catch(NullPointerException ex){ }
	}
	
	

	/**
	 * Tests if a constructed object with some FitnessExercises is really as it should be.
	 */
	public void testFullWorkout(){
		assertEquals(WORKOUT_1.getName(), TEST_NAME_1);
		
		assertTrue(WORKOUT_1.contains(FEX_1));
		assertTrue(WORKOUT_1.contains(FEX_2));
		assertTrue(WORKOUT_1.contains(FEX_3));

		assertEquals(FEX_1, WORKOUT_1.getFitnessExercises().get(0));
		assertEquals(FEX_2, WORKOUT_1.getFitnessExercises().get(1));
		assertEquals(FEX_3, WORKOUT_1.getFitnessExercises().get(2));
		
		assertEquals(WORKOUT_1.getFitnessExercises().size(), 3);
	}
	
	/**
	 * Tests the equals method of Workout.
	 */
	public void testEquals(){
		// test null
		assertFalse(WORKOUT_1.equals(null));
		
		// test with a different workout
		assertFalse(WORKOUT_1.equals(WORKOUT_2));
		assertFalse(WORKOUT_2.equals(WORKOUT_1));
		
		assertEquals(WORKOUT_1, WORKOUT_1);
		assertEquals(WORKOUT_2, WORKOUT_2);

		
		// test with an equal workout
		Workout w_eq_1 = new Workout(TEST_NAME_1, FEX_1);
		w_eq_1.addFitnessExercise(FEX_2);
		w_eq_1.addFitnessExercise(FEX_3);
		
		assertEquals(WORKOUT_1, w_eq_1);
		
		// remove FitnessExercises and add them again => different order => not equal
		w_eq_1.removeFitnessExercise(FEX_2);
		assertFalse(w_eq_1.equals(WORKOUT_1));
		w_eq_1.addFitnessExercise(FEX_2);
		assertFalse(w_eq_1.equals(WORKOUT_1)); // false because the order is different

		// test with a different name
		Workout w_noteq_1 = new Workout("TEST_NAME", FEX_LIST_1);
		assertFalse(WORKOUT_1.equals(w_noteq_1));
		assertFalse(w_noteq_1.equals(WORKOUT_1));
	}
	
	/**
	 * Test of method {@libj switchExercises()}
	 */
	public void testSwitching(){
		// clone WORKOUT_1
		Workout WORKOUT_1_CLONE = new Workout(TEST_NAME_1, FEX_LIST_1);
		assertEquals(WORKOUT_1, WORKOUT_1_CLONE);
		
		// test switching one exercise with itself
		WORKOUT_1_CLONE.switchExercises(FEX_1, FEX_1);
		// should have no effect
		assertEquals(WORKOUT_1, WORKOUT_1_CLONE);
		
		// test switching two times
		WORKOUT_1_CLONE.switchExercises(FEX_2, FEX_3);
		WORKOUT_1_CLONE.switchExercises(FEX_2, FEX_3);
		// should have no effect
		assertEquals(WORKOUT_1, WORKOUT_1_CLONE);
		
		// test switching one time
		int idx1 = WORKOUT_1.getFitnessExercises().indexOf(FEX_1);
		int idx2 = WORKOUT_1.getFitnessExercises().indexOf(FEX_2);
		WORKOUT_1_CLONE.switchExercises(FEX_2, FEX_1);
		
		assertEquals(idx2, WORKOUT_1_CLONE.getFitnessExercises().indexOf(FEX_1));
		assertEquals(idx1, WORKOUT_1_CLONE.getFitnessExercises().indexOf(FEX_2));

		// workouts now should be different
		assertNotSame(WORKOUT_1, WORKOUT_1_CLONE);

	}


}
