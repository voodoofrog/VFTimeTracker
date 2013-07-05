package uk.co.forgottendream.vftimetracker;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VFTimeTracker extends JavaPlugin {

	private static VFTimeTracker plugin;
	private ConfigHandler configHandler;
	private SeasonsHandler seasonsHandler;
	
	public void onEnable() {
		plugin = this;
		this.configHandler = new ConfigHandler(this);
		this.seasonsHandler = new SeasonsHandler(this);
		this.saveDefaultConfig();
		this.configHandler.init();
		this.saveConfig();
		this.getLogger().info("VFTimeTracker plugin has been enabled.");
	}
	 
	public void onDisable() {
		this.seasonsHandler = null;
		this.configHandler = null;
		plugin = null;
		this.getLogger().info("VFTimeTracker plugin has been disabled.");
	}
	
    /**
     * Gets the plugins instance
     *
     * @return The VFTimeTracker instance
     */
	public static VFTimeTracker getPlugin()
	{
		return plugin;
	}
	
    /**
     * Gets the plugins SeasonsHandler instance
     *
     * @return The SeasonsHandler instance
     */
	public SeasonsHandler getSeasonsHandler()
	{
		return this.seasonsHandler;
	}
	
    /**
     * Gets the full number of days passed in the world
     *
     * @param world The world to get days for
     * @return The number of days
     */
	public long getWorldDays(World world) {
		long days = world.getFullTime()/24000;
		return days;
	}
	
    /**
     * Gets the current moon phase in the world
     *
     * @param world The world to get the moon phase for
     * @return The number of the cycle
     */
	public long getWorldMoonPhase(World world) {
		long phase = getWorldDays(world) % 8;
		return phase;
	}
	
    /**
     * Gets the current day of the month in the world
     *
     * @param world The world to get the day for
     * @return The number of the day
     */
	public long getWorldDayOfMonth(World world) {
		long dayOfMonth = (getWorldDays(world) % 32) + 1;
		return dayOfMonth;
	}
	
    /**
     * Gets the current day of the year in the world
     *
     * @param world The world to get the day for
     * @return The number of the day
     */
	public long getWorldDayOfYear(World world) {
		long dayOfYear = getWorldDays(world) % 384;
		return dayOfYear;
	}
	
    /**
     * Gets how many cycles (weeks) have passed in the world
     *
     * @param world The world to get the cycles for
     * @return The number of cycles
     */
	public long getWorldCycles(World world) {
		long cycles = getWorldDays(world) / 8;
		return cycles;
	}
	
    /**
     * Gets the current cycle of the month in the world
     *
     * @param world The world to get the cycle for
     * @return The number of the cycle
     */
	public long getWorldCycleOfMonth(World world) {
		long cycleOfMonth = getWorldCycles(world) % 4;
		return cycleOfMonth;
	}
	
    /**
     * Gets the current cycle of the year in the world
     *
     * @param world The world to get the cycle for
     * @return The number of the cycle
     */
	public long getWorldCycleOfYear(World world) {
		long cycleOfMonth = getWorldCycles(world) % 48;
		return cycleOfMonth;
	}
	
    /**
     * Gets how many months have passed in the world
     *
     * @param world The world to get the months for
     * @return The number of months
     */
	public long getWorldMonths(World world) {
		long months = getWorldDays(world) / 32;
		return months;
	}
	
    /**
     * Gets the current month of the year in the world
     *
     * @param world The world to get the month for
     * @return The number of the month
     */
	public long getWorldMonthOfYear(World world) {
		long monthOfYear = (getWorldMonths(world) % 12) + 1;
		return monthOfYear;
	}

    /**
     * Gets how many years have passed in the world
     *
     * @param world The world to get the years for
     * @return The number of years
     */
	public long getWorldYears(World world) {
		long years = getWorldDays(world) / 384;
		return years;
	}
	
    /**
     * Gets the name of the current day in the world
     *
     * @param world The world to get the day name for
     * @return The name of the day
     */
	public String getWorldDayName(World world) {
		long phase = getWorldMoonPhase(world);
		String dayString;
		List<String> dayNames = getConfig().getStringList("DayNames");
	    switch ((int)phase) {
	    	case 0: dayString = dayNames.get(0);
	    		break;
	    	case 1: dayString = dayNames.get(1);
	    		break;
	    	case 2: dayString = dayNames.get(2);
	    		break;
	    	case 3: dayString = dayNames.get(3);
	    		break;
	    	case 4: dayString = dayNames.get(4);
	    		break;
	    	case 5: dayString = dayNames.get(5);
	    		break;
	    	case 6: dayString = dayNames.get(6);
	    		break;
	    	case 7: dayString = dayNames.get(7);
    			break;
	    	default: dayString = "Invalid day";
	    		break;
	    }
		return dayString;
	}
	
    /**
     * Gets the name of the current month in the world
     *
     * @param world The world to get the month name for
     * @return The name of the month
     */
	public String getWorldMonthName(World world) {
		long monthOfYear = getWorldMonthOfYear(world);
		String monthString;
		List<String> monthNames = getConfig().getStringList("MonthNames");
	    switch ((int)monthOfYear) {
	    	case 1: monthString = monthNames.get(0);
	    		break;
	    	case 2: monthString = monthNames.get(1);
	    		break;
	    	case 3: monthString = monthNames.get(2);
	    		break;
	    	case 4: monthString = monthNames.get(3);
	    		break;
	    	case 5: monthString = monthNames.get(4);
	    		break;
	    	case 6: monthString = monthNames.get(5);
	    		break;
	    	case 7: monthString = monthNames.get(6);
	    		break;
	    	case 8: monthString = monthNames.get(7);
	    		break;
	    	case 9: monthString = monthNames.get(8);
	    		break;
	    	case 10: monthString = monthNames.get(9);
	    		break;
	    	case 11: monthString = monthNames.get(10);
	    		break;
	    	case 12: monthString = monthNames.get(11);
	    		break;
	    	default: monthString = "Invalid month";
	    		break;
	    }
		return monthString;
	}

    /**
     * Gets the name of the current moon phase in the world
     *
     * @param world The world to get the moon phase name for
     * @return The name of the moon phase
     */
	public String getWorldMoonPhaseName(World world) {
		long phase = getWorldMoonPhase(world);
		String moonPhaseName;
		List<String> moonPhaseNames = getConfig().getStringList("MoonPhaseNames");
	    switch ((int)phase) {
	    	case 0: moonPhaseName = moonPhaseNames.get(0);
	    		break;
	    	case 1: moonPhaseName = moonPhaseNames.get(1);
	    		break;
	    	case 2: moonPhaseName = moonPhaseNames.get(2);
	    		break;
	    	case 3: moonPhaseName = moonPhaseNames.get(3);
	    		break;
	    	case 4: moonPhaseName = moonPhaseNames.get(4);
	    		break;
	    	case 5: moonPhaseName = moonPhaseNames.get(5);
	    		break;
	    	case 6: moonPhaseName = moonPhaseNames.get(6);
	    		break;
	    	case 7: moonPhaseName = moonPhaseNames.get(7);
    		break;
	    	default: moonPhaseName = "Invalid moon phase";
	    		break;
	    }
		return moonPhaseName;
	}
	
    /**
     * Gets the ordinal for a given number
     *
     * @param value The number to get the ordinal for
     * @return The ordinal for the value
     */
	public String getOrdinalFor(int value) {
		int hundredRemainder = value % 100;
		if(hundredRemainder >= 10 && hundredRemainder <= 20) {
			return "th";
		}
		int tenRemainder = value % 10;
		switch (tenRemainder) {
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
		}
	}

    /**
     * Checks whether it is day in the world
     *
     * @param value The world to check
     */
	public boolean isDay(World world) {
		long worldTime = world.getTime();
		if (worldTime >= 0 && worldTime <= 11999) {
			return true;
		} else {
			return false;
		}
	}
	
    /**
     * Checks whether the sun is setting in the world
     *
     * @param value The world to check
     */
	public boolean isSunset(World world) {
		long worldTime = world.getTime();
		if (worldTime >= 12000 && worldTime <= 13799) {
			return true;
		} else {
			return false;
		}
	}
	
    /**
     * Checks whether it is night in the world
     *
     * @param value The world to check
     */
	public boolean isNight(World world) {
		long worldTime = world.getTime();
		if (worldTime >= 13800 && worldTime <= 22199) {
			return true;
		} else {
			return false;
		}
	}
	
    /**
     * Checks whether the sun is rising in the world
     *
     * @param value The world to check
     */
	public boolean isSunrise(World world) {
		long worldTime = world.getTime();
		if (worldTime >= 22200 && worldTime <= 23999) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if(cmd.getName().equalsIgnoreCase("date")) {
			if (player == null) {
				sender.sendMessage("This command can only be run by a player.");
			} else {
				World world = player.getWorld();
				String dayName = getWorldDayName(world);
				long day = getWorldDayOfMonth(world);
				long year = getWorldYears(world)+1;
				String yearString = year + getOrdinalFor((int)year) + " year";
				String date = ChatColor.GRAY + "It is " + ChatColor.WHITE + dayName + ChatColor.GRAY + " the "
					+ ChatColor.WHITE + day + getOrdinalFor((int)day) + ChatColor.GRAY + " of " + ChatColor.WHITE
					+ getWorldMonthName(world) + ", " + yearString + ChatColor.GRAY + ".";
				player.sendMessage(date);
				if (this.seasonsHandler.seasonsEnabled()) {
					long dayOfYear = getWorldDayOfYear(world);
					Season season = this.seasonsHandler.getSeason(world.getName(), dayOfYear);
					if (season != null) {
						player.sendMessage(ChatColor.GRAY + "It is " + season.getColour() + season.getName() + ChatColor.GRAY
							+  " for another " + ChatColor.WHITE + season.daysLeft(dayOfYear) + ChatColor.GRAY + " days.");
					} else {
						player.sendMessage(ChatColor.GRAY + "This world has no seasons.");
					}
				}
			}
			return true;
		}
		return false; 
	}

}
