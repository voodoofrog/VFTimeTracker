package uk.co.forgottendream.vftimetracker;

import org.bukkit.ChatColor;

/**
 * Represents a season
 */
public class Season {
	private String worldName;
	private String name;
	private YearlyPeriod seasonPeriod;
    private double wheatGrowthMulti;
    private double cactusGrowthMulti;
    private double caneGrowthMulti;
    private double pumpkinStemGrowthMulti;
    private double melonStemGrowthMulti;
    private ChatColor colour;
	
    /**
     * Creates a season object
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
	public Season(String worldName, String name, YearlyPeriod seasonPeriod, double wheatGrowthMulti, double cactusGrowthMulti,
			double caneGrowthMulti, double pumpkinStemGrowthMulti, double melonStemGrowthMulti, ChatColor colour) {
		this.worldName = worldName;
		this.name = name;
		this.seasonPeriod = seasonPeriod;
	    this.wheatGrowthMulti = wheatGrowthMulti;
	    this.cactusGrowthMulti = cactusGrowthMulti;
	    this.caneGrowthMulti = caneGrowthMulti;
	    this.pumpkinStemGrowthMulti = pumpkinStemGrowthMulti;
	    this.melonStemGrowthMulti = melonStemGrowthMulti;
	    this.colour = colour;
	}
	
    /**
     * Gets the name of the world this season covers
     *
     * @return The world name for this season
     */
	public String getWorldName() {
		return this.worldName;
	}
	
    /**
     * Gets the name of this season
     *
     * @return The seasons name
     */
	public String getName() {
		return this.name;
	}
	
    /**
     * Gets the period this season covers
     *
     * @return The period
     */
	public YearlyPeriod getSeasonPeriod() {
		return this.seasonPeriod;
	}
	
    /**
     * Gets the wheat growth multiplier for this season
     *
     * @return The wheat growth multiplier
     */
	public double getWheatGrowthMultiplier() {
		return this.wheatGrowthMulti;
	}
	
    /**
     * Gets the cactus growth multiplier for this season
     *
     * @return The cactus growth multiplier
     */
	public double getCactusGrowthMultiplier() {
		return this.cactusGrowthMulti;
	}
	
    /**
     * Gets the sugar cane growth multiplier for this season
     *
     * @return The sugar cane growth multiplier
     */
	public double getCaneGrowthMultiplier() {
		return this.caneGrowthMulti;
	}
	
    /**
     * Gets the pumpkin stem growth multiplier for this season
     *
     * @return The pumpkin stem growth multiplier
     */
	public double getPumpkinStemGrowthMultiplier() {
		return this.pumpkinStemGrowthMulti;
	}
	
    /**
     * Gets the melon stem growth multiplier for this season
     *
     * @return The melon stem growth multiplier
     */
	public double getMelonStemGrowthMultiplier() {
		return this.melonStemGrowthMulti;
	}
	
    /**
     * Gets the message colour for this season
     *
     * @return The colour enum
     */
	public ChatColor getColour() {
		return this.colour;
	}
	
    /**
     * Gets the days left in this season based on the day given
     *
     * @param dayOfYear The day of the year
     * @return The number of days left
     */
	public long daysLeft(long dayOfYear) {
		long daysLeft = this.seasonPeriod.getEndDay() - dayOfYear;
		return daysLeft;
	}
	
}
