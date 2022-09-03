package de.mybukit.mycommands;

import de.mybukit.mycommands.commands.*;
import de.mybukit.mycommands.helper.Config;
import de.mybukit.mycommands.helper.HomePoint;
import de.mybukit.mycommands.helper.WarpPoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyCommands implements ModInitializer {

	public static final String MODID ="mycommands" ;
	private static File worlddir;
	public static Logger LOGGER = LogManager.getLogger("[MyCommands] ");
	public static File configdir = FabricLoader.getInstance().getConfigDirectory();

	public static File mycommandsdir=new File(configdir,"mycommands");
	public static Config config;

	public static HashMap<String, Boolean>perms =new HashMap<>();
	

	@Override
	public void onInitialize() {
  
		if(!mycommandsdir.exists()) makedir(mycommandsdir);
		
		config = new Config("mycommands/config", this.loadConfig());

		initWorlds();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			LOGGER.info(Text.translatable("commands.mycommands.test.translations").getString());
			//if (environment.integrated) {
			if (true) {
				CommandBack.register(dispatcher);
				CommandDay.register(dispatcher);
				CommandDelHome.register(dispatcher);
				CommandDelWarp.register(dispatcher);
				//CommandEnderChest.register(dispatcher);
				CommandFly.register(dispatcher);
				//CommandGM.register(dispatcher);
				CommandGod.register(dispatcher);
				CommandHeal.register(dispatcher);
				CommandHome.register(dispatcher);
				CommandRain.register(dispatcher);
				CommandRepair.register(dispatcher);
				CommandRndTp.register(dispatcher);
				CommandSetHome.register(dispatcher);
				CommandSetSpawn.register(dispatcher);
				CommandSetWarp.register(dispatcher);
				CommandSpawn.register(dispatcher);
				CommandSun.register(dispatcher);
				CommandTpa.register(dispatcher);
				CommandTpAccept.register(dispatcher);
				CommandTpDeny.register(dispatcher);
				CommandWarp.register(dispatcher);
			}
		});



	}

	private static void initWorlds() {
		ServerLifecycleEvents.SERVER_STARTED.register(server ->{

			

			if(server.isDedicated()) {
				worlddir = new File(server.getRunDirectory(),server.getSaveProperties().getLevelName());

			}else {

				worlddir = server.getIconFile().get().toFile();
			} 
		});
		ServerLifecycleEvents.SERVER_STARTED.register(server ->{
			WarpPoint.loadAll();
			HomePoint.loadAll();
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
