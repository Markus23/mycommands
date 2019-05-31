package de.mybukit.mycommands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mybukit.mycommands.commands.CommandBack;
import de.mybukit.mycommands.commands.CommandDay;
import de.mybukit.mycommands.commands.CommandDelHome;
import de.mybukit.mycommands.commands.CommandDelWarp;
import de.mybukit.mycommands.commands.CommandEnderChest;
import de.mybukit.mycommands.commands.CommandFly;
import de.mybukit.mycommands.commands.CommandGM;
import de.mybukit.mycommands.commands.CommandGod;
import de.mybukit.mycommands.commands.CommandHeal;
import de.mybukit.mycommands.commands.CommandHome;
import de.mybukit.mycommands.commands.CommandRain;
import de.mybukit.mycommands.commands.CommandRepair;
import de.mybukit.mycommands.commands.CommandRndTp;
import de.mybukit.mycommands.commands.CommandSetHome;
import de.mybukit.mycommands.commands.CommandSetSpawn;
import de.mybukit.mycommands.commands.CommandSetWarp;
import de.mybukit.mycommands.commands.CommandSpawn;
import de.mybukit.mycommands.commands.CommandSun;
import de.mybukit.mycommands.commands.CommandTpAccept;
import de.mybukit.mycommands.commands.CommandTpDeny;
import de.mybukit.mycommands.commands.CommandTpa;
import de.mybukit.mycommands.commands.CommandWarp;
import de.mybukit.mycommands.helper.Config;

import de.mybukit.mycommands.helper.WarpPoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;

public class MyCommands implements ModInitializer {

	public static final String MODID ="mycommands" ;
	private static File worlddir;
	public static Logger LOGGER = LogManager.getLogger("[MyCommands] ");
	public static File configdir = FabricLoader.getInstance().getConfigDirectory();

	public static File mycommandsdir=new File(configdir,"mycommands");
	//public static File configfile = new File(mycommandsdir,"config.cfg");
	//public static MyConfig config_old;
	public static Config config;

	public static HashMap<String, Boolean>perms =new HashMap<>();
	

	@Override
	public void onInitialize() {
  
		if(!mycommandsdir.exists()) makedir(mycommandsdir);
		
		config = new Config("mycommands/config", this.loadConfig());

	
			
		


		initWorlds();

		CommandRegistry.INSTANCE.register(false, CommandBack::register);
		CommandRegistry.INSTANCE.register(false, CommandDay::register);
		CommandRegistry.INSTANCE.register(false, CommandDelHome::register);
		CommandRegistry.INSTANCE.register(false, CommandDelWarp::register);
		//CommandRegistry.INSTANCE.register(false, CommandEnderChest::register);
		CommandRegistry.INSTANCE.register(false, CommandFly::register);
		//CommandRegistry.INSTANCE.register(false, CommandGM::register);
		CommandRegistry.INSTANCE.register(false, CommandGod::register);
		CommandRegistry.INSTANCE.register(false, CommandHeal::register);
		CommandRegistry.INSTANCE.register(false, CommandHome::register);
		CommandRegistry.INSTANCE.register(false, CommandRain::register);
		CommandRegistry.INSTANCE.register(false, CommandRepair::register);
		CommandRegistry.INSTANCE.register(false, CommandRndTp::register);
		CommandRegistry.INSTANCE.register(false, CommandSetHome::register);
		CommandRegistry.INSTANCE.register(false, CommandSetSpawn::register);
		CommandRegistry.INSTANCE.register(false, CommandSetWarp::register);
		CommandRegistry.INSTANCE.register(false, CommandSpawn::register);
		CommandRegistry.INSTANCE.register(false, CommandSun::register);
		CommandRegistry.INSTANCE.register(false, CommandTpa::register);
		CommandRegistry.INSTANCE.register(false, CommandTpAccept::register);
		CommandRegistry.INSTANCE.register(false, CommandTpDeny::register);
		CommandRegistry.INSTANCE.register(false, CommandWarp::register);



	}

	private static void initWorlds() {
		ServerStartCallback.EVENT.register(server ->{

			//registerCommands(server);

			if(server.isDedicated()) {
				worlddir = new File(server.getRunDirectory(),server.getLevelName());

			}else {

				worlddir = server.getIconFile().getParentFile();
			} 
		});
		ServerStartCallback.EVENT.register(server ->{
			WarpPoint.loadAll();
		});


	}
	public static File getWorldDir() {


		return worlddir;
	}

	public void makeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void makedir(File file) {
		if (!file.exists()) {
			file.mkdir();
		}
	}
    public Map<String, Object> loadConfig() {
        Map<String, Object> configOptions = new HashMap<>();
    	configOptions.put("back",false);
    	configOptions.put("day",true);
    	configOptions.put("delhome",false);
    	configOptions.put("delwarp",true);
    	//configOptions.put("enderchest", true);
    	configOptions.put("fly", true);
    	//configOptions.put("gm", true);
    	configOptions.put("god", true);
    	configOptions.put("home",false);
    	configOptions.put("heal",false);
    	configOptions.put("rain",true);
    	configOptions.put("repair",true);
    	configOptions.put("rndtp",false);
    	configOptions.put("sethome",false);
    	configOptions.put("setspawn",true);
    	configOptions.put("setwarp",true);
    	configOptions.put("spawn",false);
    	configOptions.put("sun",true);
    	configOptions.put("tpa",false);
    	configOptions.put("tpaccept",false);
    	configOptions.put("tpdeny",false);
    	configOptions.put("warp",false);

        return configOptions;
}

}
