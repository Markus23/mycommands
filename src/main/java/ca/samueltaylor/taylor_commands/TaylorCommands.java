package ca.samueltaylor.taylor_commands;

import ca.samueltaylor.taylor_commands.commands.*;
import ca.samueltaylor.taylor_commands.helper.Config;
import ca.samueltaylor.taylor_commands.helper.HomePoint;
import ca.samueltaylor.taylor_commands.helper.WarpPoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaylorCommands implements ModInitializer {

	public static final String MODID ="taylor_commands" ;
	private static File worldDir;
	public static Logger LOGGER = LogManager.getLogger("[TaylorCommands] ");
	public static File configDir = FabricLoader.getInstance().getConfigDirectory();

	public static File taylorCommandsDir = new File(configDir,"taylor_commands");
	public static Config config;

	public static HashMap<String, Boolean>perms = new HashMap<>();
	

	@Override
	public void onInitialize() {
		LOGGER.log(Level.INFO, "Starting init...");

		if(!taylorCommandsDir.exists()) makedir(taylorCommandsDir);
		
		config = new Config("taylor_commands/config", this.loadConfig());

		initWorlds();

		CommandRegistry.INSTANCE.register(false, CommandBack::register);
		CommandRegistry.INSTANCE.register(false, CommandDay::register);
		CommandRegistry.INSTANCE.register(false, CommandDelHome::register);
		CommandRegistry.INSTANCE.register(false, CommandDelWarp::register);
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


		LOGGER.log(Level.INFO, "Registered commands.");
	}

	private static void initWorlds() {
		ServerStartCallback.EVENT.register(server ->{
			WarpPoint.loadAll();
			HomePoint.loadAll();
		});


	}
	public static File getWorldDir() {
		return new File(".");
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
