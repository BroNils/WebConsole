package com.tes;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {
	public static String resultHTTP;
	private final static String USER_AGENT = "Mozilla/5.0";
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	private static FileConfiguration config = new YamlConfiguration();
    private static Path configFile;
    private static Float ver = (float) 1.0;
    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	
    public Path getDataPath() {
        return getDataFolder().toPath();
    }
    
	public void loadConfig() throws IOException {
		configFile = getDataPath().resolve("config.yml");
		if(!Files.exists(configFile)) {
			config.addDefault("domain", "http://example.com");
			config.addDefault("port", 80);
			config.addDefault("path", "");
			config.addDefault("isSecure", false);
			config.addDefault("version", ver);
			config.options().copyDefaults(true);
			config.save(getDataPath()+"/config.yml");;
		} else {
			config.setDefaults(YamlConfiguration.loadConfiguration(configFile.toFile()));
	        config.options().copyDefaults(true);
		}
	}
	
	@Override
	public void onEnable() {
		try {
			loadConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ANSI_WHITE + "[" + ANSI_RED + "WebConsole" + ANSI_WHITE + "]" + ANSI_CYAN + " WebConsole enabled!" + ANSI_RESET);
		consoleListen();
	}
	
	@Override
	public void onDisable() {
		try {
			config.save(getDataPath()+"/config.yml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String sendGet(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	// HTTP POST request
	private static String sendPost(String url, String urlParameters, Boolean isSecured) throws Exception {

		URL obj = new URL(url);
		if(isSecured) {
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			return response.toString();
		} else {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			return response.toString();
		}

	}
	
	void consoleListen() {
		if(config.getString("domain").toLowerCase().contains("example.com")) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			    public void run() { /* code */ 
			    	try {
						//System.out.println(Main.sendGet(config.getString("domain") + ":" + config.getString("port") + "/" + config.getString("path")));
			    		resultHTTP = Main.sendGet(config.getString("domain") + ":" + config.getString("port") + "/" + config.getString("path") + "/exec.log");
			    		if(resultHTTP.toLowerCase().contains("nocmdx")) {
			    			Main.sendPost(config.getString("domain") + ":" + config.getString("port") + "/" + config.getString("path") + "/reqman.php", "cmd="+resultHTTP+"&done=true", config.getBoolean("isSecure"));
			    		} else {
			    			Bukkit.dispatchCommand(console, resultHTTP);
			    			resultHTTP = null;
			    			Main.sendPost(config.getString("domain") + ":" + config.getString("port") + "/" + config.getString("path") + "/reqman.php", "cmd="+resultHTTP+"&done=true", config.getBoolean("isSecure"));
			    		}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}, 2, 20);
		} else {
			System.out.println(ANSI_WHITE + "[" + ANSI_RED + "WebConsole" + ANSI_WHITE + "]" + ANSI_CYAN + " You have to setup your config file!" + ANSI_RESET);
		}
	}
}
