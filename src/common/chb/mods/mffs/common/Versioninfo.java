package chb.mods.mffs.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;

public class Versioninfo {
	      private static boolean create;
		  private static String Major;
		  private static String Minor;
		  private static String Revision;
		  private static String betabuild;

		  public static final String version() {
			    if (!create) {
			    create();
			    }
			    return Major+"."+Minor+"."+Revision+"."+betabuild;
			  }

		  private static void create() {
		    InputStream inputstream = Versioninfo.class.getClassLoader().getResourceAsStream("versioninfo");
		    Properties properties = new Properties();

		    if (inputstream != null) {
		      try {
		        properties.load(inputstream);
		        Major = properties.getProperty("mffs.version.major.number");
		        Minor = properties.getProperty("mffs.version.minor.number");
		        Revision = properties.getProperty("mffs.version.revision.number");
		        betabuild = properties.getProperty("mffs.version.betabuild.number");
		      } catch (IOException ex) {
		        FMLCommonHandler.instance().getFMLLogger().log(Level.SEVERE, " Modual ForceField System V2 broken Installation detected!", ex);
		        throw new RuntimeException(ex);
		      }
		    }
		    create = true;
		  }
		}
