package ca.samueltaylor.taylor_commands.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveFile 
{
	public File file;

	public String name;
	public String path;

	public ArrayList<String> data = new ArrayList<String>();

	String mask = "what you see is not what it is.";

	//if this sign is detected, it's a masked file.
	String sign = ".(OAo)o ";

	public SaveFile(String name, String path)
	{
		this.name = name;
		this.path = path;
		file = new File(path + name);
	}
	protected SaveFile(){}

	public void createFile()
	{		
		if(file.exists())
			return;

		File pathFile = new File(path);

		pathFile.mkdirs();

		try 
		{
			file.createNewFile();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param hide If it's needed to apply a mask on this save file.
	 */
	public void save(boolean hide)
	{
		createFile();

		try 
		{
			PrintWriter out = new PrintWriter(file);

			if(hide)
			{
				out.println(sign);
			}

			int index = 0;
			for(String line : data)
			{
				for(int i = 0; i < line.length(); i++)
				{
					char character = line.charAt(i);

					if(hide)
					{
						//apply mask						
						character = (char)((character + mask.charAt(index) - 64) % 95);
						character = (char)(character + 32);
						////////////////////////

						index++;
						index = index % mask.length();
					}

					out.print(character);
				}
				out.println();
				out.flush();
			}
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public void load()
	{
		createFile();
		clear();
		boolean hide = false;
		int index = 0;
		try 
		{
			Scanner scan = new Scanner(file);

			while(scan.hasNext())
			{
				String rawString = scan.nextLine();
				String line = "";

				if(rawString.equals(sign))
				{
					hide = true;
					continue;
				}

				if(hide)
				{
					for(int i = 0; i < rawString.length(); i++)
					{
						//debug:
						//System.out.println(rawString.charAt(i) - mask.charAt(index) + " " + (int)rawString.charAt(i) + " " + (int)mask.charAt(index));

						char character = rawString.charAt(i);

						if(hide)
						{
							//remove mask
							character = (char)(character - 32);
							character = (char)((character - (mask.charAt(index) - 32) + 95) % 95 + 32);
							/////////////
						}

						line += character;
						index++;
						index = index % mask.length();
					}
				}
				else
				{
					line = rawString;
				}

				data.add(line);
			}

			scan.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public void clear()
	{
		data.clear();
	}

	public boolean exists()
	{
		return file.exists();
	}

	public String getSingleData(String name){
		for(String aData : data){
			if(aData.contains(name))
				return aData;
		}
		return null;
	}

	public boolean isBoolean(String name){
		if(name.contains("true") || name.contains("false"))
			return true;
		else
			return false;
	}

	public boolean getBoolean(String name){
		String aData = getSingleData(name);
		String[] split = null;
		if(isBoolean(aData)){
			split = aData.split("=");
		}
		return Boolean.parseBoolean(split[1]);
	}

	public String getString(String name){
		String aData = getSingleData(name);
		String[] split = aData.split("=");
		return split[1];
	}
}
