package ca.samueltaylor.taylor_commands.helper;

public class McColor
{
	final public static String black = "\u00a70";
	final public static String blue = "\u00a71";
	final public static String darkGreen = "\u00a72";
	final public static String darkAqua = "\u00a73";
	final public static String darkRed = "\u00a74";
	final public static String purple = "\u00a75";
	final public static String gold = "\u00a76";
	final public static String grey = "\u00a77";
	final public static String darkGrey = "\u00a78";
	final public static String indigo = "\u00a79";
	final public static String green = "\u00a7a";
	final public static String aqua = "\u00a7b";
	final public static String red = "\u00a7c";
	final public static String pink = "\u00a7d";
	final public static String yellow = "\u00a7e";
	final public static String white = "\u00a7f";
	
	public static String replaceColorCodes(String line)
	{
		String temp = line;
		
		temp = replaceString(temp, "^black", black);
		temp = replaceString(temp, "^blue", blue);
		temp = replaceString(temp, "^darkGreen", darkGreen);
		temp = replaceString(temp, "^darkAqua", darkAqua);
		temp = replaceString(temp, "^darkRed", darkRed);
		temp = replaceString(temp, "^purple", purple);
		temp = replaceString(temp, "^gold", gold);
		temp = replaceString(temp, "^grey", grey);
		temp = replaceString(temp, "^darkGrey", darkGrey);
		temp = replaceString(temp, "^indigo", indigo);
		temp = replaceString(temp, "^green", green);
		temp = replaceString(temp, "^aqua", aqua);
		temp = replaceString(temp, "^red", red);
		temp = replaceString(temp, "^pink", pink);
		temp = replaceString(temp, "^yellow", yellow);
		temp = replaceString(temp, "^white", white);
		
		temp = replaceString(temp, "&0", black);
		temp = replaceString(temp, "&1", blue);
		temp = replaceString(temp, "&2", darkGreen);
		temp = replaceString(temp, "&3", darkAqua);
		temp = replaceString(temp, "&4", darkRed);
		temp = replaceString(temp, "&5", purple);
		temp = replaceString(temp, "&6", gold);
		temp = replaceString(temp, "&7", grey);
		temp = replaceString(temp, "&8", darkGrey);
		temp = replaceString(temp, "&9", indigo);
		temp = replaceString(temp, "&a", green);
		temp = replaceString(temp, "&b", aqua);
		temp = replaceString(temp, "&c", red);
		temp = replaceString(temp, "&d", pink);
		temp = replaceString(temp, "&e", yellow);
		temp = replaceString(temp, "&f", white);
		
		return temp;
	}
	
	public static String replaceString(String line, String target, String replacement)
	{
		String temp = line;
		
		while(temp.indexOf(target) >= 0)
		{
			String front = "";
			if(temp.indexOf(target) > 0)
				front = temp.substring(0,temp.indexOf(target));
			String back = "";
			if(temp.indexOf(target) + target.length() < temp.length())
				back = temp.substring(temp.indexOf(target) + target.length());
			
			temp = front + replacement + back;
		}
		
		return temp;
	}
	
}
