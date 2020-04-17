package ca.samueltaylor.taylor_commands.helper;

import java.util.concurrent.ThreadLocalRandom;

public class Random  {

	private static ThreadLocalRandom tlr = ThreadLocalRandom.current();
	
	public static int randInt() {
		return randInt(0, Integer.MAX_VALUE);
	}
	
	public static int randInt(int max) {
		return randInt(0, max);
	}
	
	public static int randInt(int min, int max) {
		return tlr.nextInt(min, max);
	}
	
	public static long randLong() {
		return randLong(0, Long.MAX_VALUE);
	}
	
	public static long randLong(long max) {
		return randLong(0, max);
	}
	
	public static long randLong(long min, long max) {
		return tlr.nextLong(min, max);
	}
	
	public static short randShort() {
		return randShort((short) 0, Short.MAX_VALUE);
	}
	
	public static short randShort(short max) {
		return randShort((short) 0, max);
	}
	
	public static short randShort(short min, short max) {
		return (short) randInt(min, max);
	}
	
	public static double randDouble() {
		return randDouble(0D, Double.MAX_VALUE);
	}
	
	public static double randDouble(double max) {
		return randDouble(0D, max);
	}
	
	public static double randDouble(double min, double max) {
		return tlr.nextDouble(min, max);
	}
	
	public static float randFloat() {
		return randFloat(0F, Float.MAX_VALUE);
	}
	
	public static float randFloat(float max) {
		return randFloat(0F, max);
	}
	
	public static float randFloat(float min, float max) {
		return (float) randDouble(min, max);
	}
	
}