package ca.samueltaylor.taylor_commands.helper;

import ca.samueltaylor.taylor_commands.TaylorCommands;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class WarpPoint
{
	public String name;
	public Location location;

	private static SaveFile warpPointsSaveFile = new SaveFile("warpPoints.txt", TaylorCommands.getWorldDir().toString()+"/taylor_commands/warps/");

	public static ArrayList<WarpPoint> warpPoints = new ArrayList<WarpPoint>();

	public WarpPoint(ServerPlayerEntity player, String name)
	{
		this.name = name;
		location = new Location(player);
	}

	public WarpPoint(String name, Location location)
	{
		this.name = name;
		this.location = location;
	}

	public static WarpPoint getWarpPoint(String name)
	{
		WarpPoint target = new WarpPoint(name,null);
		if(warpPoints.contains(target))
		{
			return warpPoints.get(warpPoints.indexOf(target));
		}
		return null;
	}

	public static String getWarpPoints()
	{
		String targets = "";
		for(WarpPoint wp:warpPoints)
			targets = targets + ", " + wp.name;

		if (targets.length() > 2)
			targets = targets.substring(2);

		return targets;
	}
	public static List<String> getWarpNames()
	{
		List<String>wpnames =new ArrayList<>();
		
		for(WarpPoint wp:warpPoints) {
		
			if(wp==null) {
				return null;
			}
			wpnames.add(wp.name);
		
		
		}
		

		return wpnames;
	}

	public static void setWarpPoint(ServerPlayerEntity player, String name)
	{
		Location location = new Location(player);
		WarpPoint warpPoint = new WarpPoint(name, location);

		if(warpPoints.contains(warpPoint))
			warpPoints.remove(warpPoint);

		warpPoints.add(warpPoint);

		saveAll();
	}

	/**
	 * @return true on success, false when there's no WarpPoint with this name.
	 */
	public static boolean delWarpPoint(String name)
	{
		WarpPoint warpPoint = new WarpPoint(name, null);
		if(warpPoints.contains(warpPoint))
		{
			warpPoints.remove(warpPoint);
			saveAll();
			return true;
		}
		return false;
	}

	public static void loadAll()
	{
		warpPointsSaveFile.load();
		warpPoints.clear();
		for(String info : warpPointsSaveFile.data)
			warpPoints.add(new WarpPoint(info));

	}

	public static void saveAll()
	{
		warpPointsSaveFile.clear();
		for(WarpPoint warpPoint : warpPoints)
			warpPointsSaveFile.data.add(warpPoint.toString());

		warpPointsSaveFile.save(false);
	}

	/**
	 * used to rebuild from string
	 */
	public WarpPoint(String info) 
	{
		try
		{
			this.name = info.substring(0,info.indexOf("("));
			String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			this.location = new Location(locationInfo);
		}
		catch(Exception e)
		{
			System.err.println("Exception on attemping to rebuild WarpPoint from String.");
			name = "Error";
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

		return name + "(" + location.toString() + ")";
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof WarpPoint)
			return name.equals(((WarpPoint)o).name);

		return false;
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	/**
	 * dummy constructor used to create a empty instance
	 */
	@SuppressWarnings("unused")
	private WarpPoint(String name, Object dummy)
	{
		this.name = name;
	}
}
