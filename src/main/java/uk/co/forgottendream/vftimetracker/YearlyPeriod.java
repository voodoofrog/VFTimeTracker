package uk.co.forgottendream.vftimetracker;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a yearly period of dd/mm to dd/mm
 */
public final class YearlyPeriod {

	private final long startDay;
	private final long endDay;
	private final long totalDays;

	/**
	 * Creates a yearly period with the specified start and end days.
	 * 
	 * @param startDay
	 *            The start day of the period, or {@code null} if
	 *            there is no start day.
	 * @param endDay
	 *            The end day of the period, or {@code null} if
	 *            there is no end day.
	 * @throws IllegalArgumentException
	 *             if arguments fail validation
	 */
    public YearlyPeriod(long startDay, long endDay) throws IllegalArgumentException {
		long totalDays;
		int yearLength = 384;
        
		if (startDay < 0)
			throw new IllegalArgumentException("startDay is negative");
		if (endDay < 0)
			throw new IllegalArgumentException("endDay is negative");
		if (startDay > (yearLength - 1))
			throw new IllegalArgumentException("startDay is outside of the year");
		if (endDay > (yearLength - 1))
			throw new IllegalArgumentException("endDay is outside of the year");
		
		if (startDay > endDay) {
			totalDays = (yearLength - startDay) + endDay + 1;
		} else {
			totalDays = endDay - startDay + 1;
		}
		
		if (totalDays > yearLength)
			throw new IllegalArgumentException("too many days in range");
		
        this.startDay = startDay;
        this.endDay = endDay;
        this.totalDays = totalDays;
    }
    
	/**
	 * Gets the start day of this period
	 * 
	 * @return the number of the day
	 */
    public long getStartDay() {
        return this.startDay;
    }

	/**
	 * Gets the end day of this period
	 * 
	 * @return the number of the day
	 */
    public long getEndDay() {
        return this.endDay;
    }
    
	/**
	 * Gets the total number of days in this period
	 * 
	 * @return the number days
	 */
    public long getTotalDays() {
    	return this.totalDays;
    }

	/**
	 * Checks whether this period is cross-year
	 */
    public boolean isCrossYear() {
    	if (this.startDay > this.endDay) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Checks if this period contains a given day of the year
     *
     * @param dayOfYear The day of the year
     */
    public boolean containsDay(long dayOfYear) {
		int yearLength = 384;
    	if (this.isCrossYear()) {
    		if ((dayOfYear >= this.startDay && dayOfYear < yearLength) || (dayOfYear <= this.endDay && dayOfYear >= 0)) {
    			return true;
    		}
    	} else {
    		if (dayOfYear >= this.startDay && dayOfYear <= this.endDay) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Checks if this period overlaps any in a list of periods
     *
     * @param periods A list of periods to check against
     */
	public boolean overlaps(List<YearlyPeriod> periods) {
		Iterator<YearlyPeriod> epi;

		if (this.isCrossYear()) {
			epi = periods.iterator();
			while (epi.hasNext()) {
				YearlyPeriod listPeriod = epi.next();
				if (listPeriod.isCrossYear()) {
					return true;
				} else if (listPeriod.containsDay(this.startDay) || listPeriod.containsDay(this.endDay)) {
					return true;
				}
			}
		} else {
			epi = periods.iterator();
			while (epi.hasNext()) {
				YearlyPeriod listPeriod = epi.next();
				if (listPeriod.containsDay(this.startDay) || listPeriod.containsDay(this.endDay))
					return true;
			}
		}
		return false;
	}

}
