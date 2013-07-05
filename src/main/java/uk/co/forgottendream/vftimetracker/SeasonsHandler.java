package uk.co.forgottendream.vftimetracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * Handles the seasons
 */
public class SeasonsHandler {

	VFTimeTracker plugin;
	private List<Season> seasons;
	private boolean seasonsEnabled = false;
	
    public SeasonsHandler(VFTimeTracker plugin) {
    	this.plugin = plugin;
    }
	
    /**
     * Creates a season and adds it into the season handler
     *
     * @param worldName The name of the world the season covers
     * @param name The name of the season
     * @param seasonPeriod The period the season covers
     * @param wheatGrowthMulti The wheat growth multiplier for VFCropControl
     * @param cactusGrowthMulti The cactus growth multiplier for VFCropControl
     * @param caneGrowthMulti The sugar cane growth multiplier for VFCropControl
     * @param pumpkinStemGrowthMulti The pumpkin stem growth multiplier for VFCropControl
     * @param melonStemGrowthMulti The melon stem growth multiplier for VFCropControl
     * @param colour The colour to use for season messages
     */
	public void addSeason (String worldName, String name, YearlyPeriod seasonPeriod, double wheatGrowthMulti, double cactusGrowthMulti,
			double caneGrowthMulti, double pumpkinStemGrowthMulti, double melonStemGrowthMulti, ChatColor colour) {
		if (this.seasons == null) {
			this.seasons = new ArrayList<Season>();
		}
		this.seasons.add(new Season(worldName, name, seasonPeriod, wheatGrowthMulti, cactusGrowthMulti,
				caneGrowthMulti, pumpkinStemGrowthMulti, melonStemGrowthMulti, colour));
	}
	
    /**
     * Adds a season into the season handler
     *
     * @param season The season to add
     */
	public void addSeason (Season season) {
		if (this.seasons == null) {
			this.seasons = new ArrayList<Season>();
		}
		this.seasons.add(season);
	}
	
    /**
     * Gets the list of seasons
     *
     * return The seasons list
     */
	public List<Season> getSeasons () {
		return this.seasons;
	}
	
    /**
     * Gets the enabled state of seasons
     */
	public boolean seasonsEnabled () {
		return this.seasonsEnabled;
	}
	
    /**
     * Sets the enabled state of seasons
     * @param state The state to set
     */
	public void setEnabled (boolean state) {
		this.seasonsEnabled = state;
	}
	
    /**
     * Gets the current season in a world
     * @param worldName The name of the world
     * @return The season
     */
	public Season getSeason (String worldName) {
		long dayOfYear = plugin.getWorldDayOfYear(plugin.getServer().getWorld(worldName));
		Season season = null;
		Iterator<Season> si = this.seasons.iterator();
		while (si.hasNext()) {
			Season currentSeason = si.next();
			if (currentSeason.getWorldName().equals(worldName) && currentSeason.getSeasonPeriod().containsDay(dayOfYear))
				season = currentSeason;
		}
		return season;
	}
	
    /**
     * Gets the season in a world by the day of the year
     * @param worldName The name of the world
     * @param dayOfYear The day of the year
     * @return The season
     */
	public Season getSeason (String worldName, long dayOfYear) {
		Season season = null;
		Iterator<Season> si = this.seasons.iterator();
		while (si.hasNext()) {
			Season currentSeason = si.next();
			if (currentSeason.getWorldName().equals(worldName) && currentSeason.getSeasonPeriod().containsDay(dayOfYear))
				season = currentSeason;
		}
		return season;
	}

}
