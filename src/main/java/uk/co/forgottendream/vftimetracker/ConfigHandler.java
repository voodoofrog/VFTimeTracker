package uk.co.forgottendream.vftimetracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles the config for the plugin
 */
public class ConfigHandler {

	private final VFTimeTracker plugin;
	
    public ConfigHandler(VFTimeTracker plugin) {
    	this.plugin = plugin;
    }

    /**
     * Verifies and sets up the plugins settings from the config file and sets up seasons if enabled.
     */
    @SuppressWarnings("unchecked")
	public void init() {
    	FileConfiguration config = this.plugin.getConfig();
    	Logger logger = this.plugin.getLogger();
    	
    	List<String> dayNames = (List<String>)config.getList("DayNames", new ArrayList<String>());
    	if (dayNames.size() < 8) {
    		while (dayNames.size() < 8) {
    			int dayNumToAdd = dayNames.size() + 1;
    			dayNames.add(getDefaultDayName(dayNumToAdd));
    		}
    		config.set("DayNames", dayNames);
    	}
    	
    	List<String> monthNames = (List<String>)config.getList("MonthNames", new ArrayList<String>());
    	if (monthNames.size() < 12) {
    		while (monthNames.size() < 12) {
    			int monthNumToAdd = monthNames.size() + 1;
    			monthNames.add(getDefaultMonthName(monthNumToAdd));
    		}
    		config.set("MonthNames", monthNames);
    	}
    	
    	List<String> moonPhaseNames = (List<String>)config.getList("moonphasenames", new ArrayList<String>());
    	if (moonPhaseNames.size() < 8) {
    		while (moonPhaseNames.size() < 8) {
    			int phaseNumToAdd = moonPhaseNames.size() + 1;
    			moonPhaseNames.add(getDefaultMoonPhaseName(phaseNumToAdd));
    		}
    		config.set("MoonPhaseNames", moonPhaseNames);
    	}
    	boolean seasonsEnabled = config.getBoolean("SeasonsEnabled", false);
    	if (seasonsEnabled) {
    		if (config.getConfigurationSection("Seasons") != null) {
    			Set<String> worldKeys = config.getConfigurationSection("Seasons").getKeys(false);
    			if (worldKeys.size() > 0) {
        			Iterator<String> wk = worldKeys.iterator();
        		    while(wk.hasNext()) {
        		    	String worldName = wk.next();
        		    	if (this.plugin.getServer().getWorld(worldName) != null) {
    		    			List<YearlyPeriod> seasonPeriods = new ArrayList<YearlyPeriod>();
    		    			List<Season> seasonsToAdd = new ArrayList<Season>();
    		    			String currentPath = "Seasons." + worldName;

        		    		Set<String> seasonKeys = config.getConfigurationSection(currentPath).getKeys(false);
        		    		Iterator<String> sk = seasonKeys.iterator();
        		    		while (sk.hasNext()) {
        		    			String seasonName = sk.next();
        		    			YearlyPeriod seasonPeriod = null;
        		    			double wheatGrowthMulti;
        		    			double cactusGrowthMulti;
        		    			double caneGrowthMulti;
        		    			double pumpkinGrowthMulti;
        		    			double melonGrowthMulti;
        		    			ChatColor colour = ChatColor.WHITE;
        		    			boolean addSeason = true;
        		    			
        		    			currentPath = "Seasons." + worldName + "." + seasonName;
        		    			
        		    			if (config.contains(currentPath + ".StartDate") && config.contains(currentPath + ".EndDate")) {
        		    				long startDay = -1;
        		    				String startDateString = config.getString(currentPath + ".StartDate");
        		    				if (validStartEndDate(startDateString)) {
        		    					startDay = getYearDay(startDateString);
        		    				} else {
        		    					addSeason = false;
        		    					logger.info("Season \"" + seasonName + "\" in world named \""
        		    					+ worldName + "\" has an invalid start date. This season will not be added.");
        		    				}
        		    			
        		    				long endDay = -1;
        		    				String endDateString = config.getString(currentPath + ".EndDate");
        		    				if (validStartEndDate(endDateString)) {
        		    					endDay = getYearDay(endDateString);
        		    				} else {
        		    					addSeason = false;
        		    					logger.info("Season \"" + seasonName + "\" in world named \""
        		    					+ worldName + "\" has an invalid end date. This season will not be added.");
        		    				}
        		    			
        		    				if (addSeason) {
        		    					try {
        		    						seasonPeriod = new YearlyPeriod(startDay, endDay);
        		    					} catch (IllegalArgumentException e) {
        		    						addSeason = false;
        		    						logger.info("Season \"" + seasonName + "\" in world named \""
        		    						+ worldName + "\" has an invalid date range: " + e.toString() + ". This season will not be added.");
        		    					}
        		    				}
        		    			
        		    				if (seasonPeriod != null) {
        		    					if (seasonPeriods.size() == 0) {
        		    						seasonPeriods.add(seasonPeriod);
        		    					} else {
        		    						if (!seasonPeriod.overlaps(seasonPeriods)) {
        		    							seasonPeriods.add(seasonPeriod);
        		    						} else {
                		    					addSeason = false;
        	    		    					logger.info("Season \"" + seasonName + "\" in world named \""
        	    	    		    			+ worldName + "\" has overlapping dates. This season will not be added.");
        		    						}
        		    					}
        		    				}
        		    			} else {
    		    					addSeason = false;
    		    					logger.info("Season \"" + seasonName + "\" in world named \""
    		    					+ worldName + "\" is missing a start and/or end date. This season will not be added.");
        		    			}
        		    			
        		    			wheatGrowthMulti = config.getDouble(currentPath + ".WheatGrowthMultiplier", 1.0);
        		    			cactusGrowthMulti = config.getDouble(currentPath + ".CactusGrowthMultiplier", 1.0);
        		    			caneGrowthMulti = config.getDouble(currentPath + ".SugarCaneGrowthMultiplier", 1.0);
        		    			pumpkinGrowthMulti = config.getDouble(currentPath + ".PumpkinGrowthMultiplier", 1.0);
        		    			melonGrowthMulti = config.getDouble(currentPath + ".MelonGrowthMultiplier", 1.0);
        		    			
        		    			try {
        		    				colour = ChatColor.valueOf(config.getString(currentPath + ".Colour").trim().toUpperCase());
        		    			} catch (IllegalArgumentException e) {
    		    					logger.info("Season \"" + seasonName + "\" in world named \""
    		    					+ worldName + "\" has an invalid chat colour.  Defaulting to white.");
        		    			}
        		    			
        		    			if (addSeason) {
        		    				seasonsToAdd.add(new Season(worldName, seasonName, seasonPeriod, wheatGrowthMulti, cactusGrowthMulti,
        		    						caneGrowthMulti, pumpkinGrowthMulti, melonGrowthMulti, colour));
        		    			}
        		    		}
        		    		
        		    		Iterator<Season> stai = seasonsToAdd.iterator();
        		    		long totalDays = 0;
        		    		int yearLength = 384;
        		    		while (stai.hasNext()) {
        		    			totalDays += stai.next().getSeasonPeriod().getTotalDays();
        		    		}
        		    		if (totalDays == yearLength) {
        		    			stai = seasonsToAdd.iterator();
            		    		while (stai.hasNext()) {
            		    			Season season = stai.next();
            		    			this.plugin.getSeasonsHandler().addSeason(season);
            		    		}
		    					logger.info("Seasons for world named \"" + worldName + "\" added successfully.");
        		    		} else {
        		    			if (totalDays > yearLength)
        		    				logger.info("Seasons for world named \"" + worldName 
        		    				+ "\" exceed the year.  They were not added.");
        		    			if (totalDays < yearLength)
        		    				logger.info("Seasons for world named \"" + worldName 
        		    				+ "\" do not cover the year.  They were not added.");		
        		    		}
        		    		
        		    	} else {
        		    		logger.info("There is no world named \"" + worldName + "\". Skipping this entry in the config.");
        		    	}
        		    }
        		    
        		    if (this.plugin.getSeasonsHandler().getSeasons() == null) {
        		    	seasonsEnabled = false;
            			logger.info("No seasons were added. Seasons disabled");
        		    }
        		    
    		    } else {
    		    	seasonsEnabled = false;
        			logger.info("Seasons section in config is empty. Seasons disabled.");
    		    }
    		} else {
    			seasonsEnabled = false;
    			logger.info("No seasons section in config. Seasons disabled.");
    		}
    	}
    	this.plugin.getSeasonsHandler().setEnabled(seasonsEnabled);
    }
    
    /**
     * Gets the default day name for a given number
     *
     * @param dayNum The number of the day
     * @return The day name
     */
    private String getDefaultDayName (int dayNum) {
    	String dayName;
    	switch (dayNum) {
    	case 0: dayName = "Monday";
    		break;
    	case 1: dayName = "Tuesday";
    		break;
    	case 2: dayName = "Wednesday";
    		break;
    	case 3: dayName = "Thursday";
    		break;
    	case 4: dayName = "Friday";
    		break;
    	case 5: dayName = "Saturday";
    		break;
    	case 6: dayName = "Sunday";
    		break;
    	case 7: dayName = "Monseve";
			break;
    	default: dayName = "the " + (dayNum) + this.plugin.getOrdinalFor(dayNum) + " weekday";
    		break;
    	}
    	return dayName;
    }
    
    /**
     * Gets the default month name for a given number
     *
     * @param monthNum The number of the month
     * @return The month name
     */
    private String getDefaultMonthName (int monthNum) {
    	String monthName;
    	switch (monthNum) {
    	case 0: monthName = "January";
    		break;
    	case 1: monthName = "February";
    		break;
    	case 2: monthName = "March";
    		break;
    	case 3: monthName = "April";
    		break;
    	case 4: monthName = "May";
    		break;
    	case 5: monthName = "June";
    		break;
    	case 6: monthName = "July";
    		break;
    	case 7: monthName = "August";
			break;
    	case 8: monthName = "September";
			break;
    	case 9: monthName = "October";
			break;
    	case 10: monthName = "November";
			break;
    	case 11: monthName = "December";
			break;
    	default: monthName = "the " + (monthNum) + this.plugin.getOrdinalFor(monthNum) + " month";
    		break;
    	}
    	return monthName;
    }

    /**
     * Gets the default moon phase name for a given number
     *
     * @param phaseNum The number of the moon phase
     * @return The moon phase name
     */
    private String getDefaultMoonPhaseName (int phaseNum) {
    	String moonPhaseName;
    	switch (phaseNum) {
    	case 0: moonPhaseName = "full";
    		break;
    	case 1: moonPhaseName = "waning gibbous";
    		break;
    	case 2: moonPhaseName = "last quarter";
    		break;
    	case 3: moonPhaseName = "waning crescent";
    		break;
    	case 4: moonPhaseName = "new";
    		break;
    	case 5: moonPhaseName = "waxing crescent";
    		break;
    	case 6: moonPhaseName = "first quarter";
    		break;
    	case 7: moonPhaseName = "waxing gibbous";
			break;
    	default: moonPhaseName = (phaseNum) + this.plugin.getOrdinalFor(phaseNum) + " phase";
    		break;
    	}
    	return moonPhaseName;
    }
    
    /**
     * Checks a config start/end string date for validity
     *
     * @param date The date to check
     */
	private boolean validStartEndDate(String date) {
		 String delimiter = "/";
		 String[] splitDate = date.split(delimiter);

		 if (splitDate.length != 2) {
			 return false;
		 } else if(splitDate[0].length() != 2 || splitDate[1].length() != 2) {
			 return false;
		 } else {
			 long day = Long.parseLong(splitDate[0]) - 1;
			 long monthStartDay = (Long.parseLong(splitDate[1]) - 1) * 32;
			 if (monthStartDay < 0 || monthStartDay > 383) {
				 return false;
			 }
			 if (day < 0 || day > 31) {
				 return false;
			 }
			 return true;
		 }
	}
	
    /**
     * Gets a year day from a config date string
     *
     * @param date The date string to convert
     * @return The day of the year
     */
	private long getYearDay(String date) {
		 String delimiter = "/";
		 String[] splitDate = date.split(delimiter);
		 long day = Long.parseLong(splitDate[0]) - 1;
		 long monthStartDay = (Long.parseLong(splitDate[1]) - 1) * 32;
		 return monthStartDay + day;
	}
	
}
