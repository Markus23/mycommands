package ca.samueltaylor.taylor_commands.helper;

import ca.samueltaylor.taylor_commands.TaylorCommands;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;


public class HomePoint
{
	public String UUID;
	public Location location;
	public String homename;

	private static SaveFile homesSaveFile = new SaveFile("homes.txt", TaylorCommands.getWorldDir().toString()+"/taylor_commands/homes/");

	public static ArrayList<HomePoint> homes = new ArrayList<HomePoint>();

	public HomePoint(ServerPlayerEntity player, String name)
	{
		this.UUID = player.getUuid().toString();
		this.homename =	name;
		location = new Location(player);
	}

	public HomePoint(ServerPlayerEntity player,String name, Location location)
	{
		this.UUID=player.getUuid().toString();
		this.homename = name;
		this.location = location;
	}

	public static HomePoint getHome(ServerPlayerEntity player, String name)
	{
		for(int i=0;i<homes.size();i++){
			if(player.getUuid().toString().equals(homes.get(i).UUID)&&name.equalsIgnoreCase(homes.get(i).homename)){
				return homes.get(i);
			}
		}
		

		return null;
	}

	public static String gethomePoints(ServerPlayerEntity player)
	{
		String targets = "";
		for(int i=0;i<homes.size();i++){
			if(player.getUuid().toString().equals(homes.get(i).UUID)){
				targets = targets + ", " + homes.get(i).homename;
			}
		}
		if (targets.length() > 2)
			targets = targets.substring(2);

		return targets;
	}
	public static int getHomecounts(ServerPlayerEntity player)
	{
		int targets = 1;
		for(int i=0;i<homes.size();i++){
			if(player.getUuid().toString().equals(homes.get(i).UUID)){
				targets ++;
			}
		}


		return targets;
	}

	public static void setHome(ServerPlayerEntity player,String name)
	{
		Location location = new Location(player);
		
		HomePoint target = new HomePoint(player,name, location);

		for(int i=0;i<homes.size();i++){
			if(player.getUuid().toString().equals(homes.get(i).UUID)&&name.equalsIgnoreCase(homes.get(i).homename)){
				homes.remove(i);
			}		

		}
			

		homes.add(target);


		saveAll();
	}



	/**
	 * @return true on success, false when there's no WarpPoint with this name.
	 */

	public static void loadAll()
	{

		homesSaveFile.load();
		homes.clear();
		for(String info : homesSaveFile.data)
			homes.add(new HomePoint(info));
	}

	public static void saveAll()
	{

		homesSaveFile.clear();
		for(HomePoint home : homes)
			homesSaveFile.data.add(home.toString());

		homesSaveFile.save(false);
	}

	/**
	 * used to rebuild from string
	 * @param location2 
	 * @param homename 
	 */
	public HomePoint(String info) 
	{
		try
		{
			this.UUID = info.substring(0,info.indexOf(","));
			this.homename = info.substring(info.indexOf(",")+1,info.indexOf("("));

			String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			this.location = new Location(locationInfo);
		}
		catch(Exception e)
		{
			System.err.println("Exception on attemping to rebuild WarpPoint from String.");
			UUID="Error";
			homename = "Error";
			location = new Location(0,0,0,0);
		}
	}

	/**
	 * return a describing String that can be used to rebuild a WarpPoint
	 */
	public String toString()
	{
		if(location == null)
			return "";

		return UUID+","+homename +"(" + location.toString() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof HomePoint)
			return homename.equals(((HomePoint)o).homename);

		return false;
	}

	@Override
	public int hashCode()
	{
		return homename.hashCode();
	}

	/**
	 * dummy constructor used to create a empty instance
	 */
	@SuppressWarnings("unused")
	private HomePoint(ServerPlayerEntity player,String name, Object dummy)
	{
		this.UUID=player.getUuid().toString();
		this.homename = name;
	}
	public static boolean delHomePoint(ServerPlayerEntity player,String name)
	{
		
		for(int i=0;i<homes.size();i++){
			if(player.getUuid().toString().equals(homes.get(i).UUID)&&name.equalsIgnoreCase(homes.get(i).homename)){
				homes.remove(i);
			
				saveAll();
				return true;
			}		

		}
		return false;
				

	}
}
