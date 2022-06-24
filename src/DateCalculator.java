/**
 * represents date calculator
 */
public class DateCalculator {
    /**
     * calculates new date, given a date and a number of days to add or subtract
     *
     * @param date date of type Date
     * @param num  number of days to add or subtract from the date
     * @return the new created date
     */
    public static Date addToDate(Date date, int num) {
        boolean isPositive = num >= 0;
        //if there are more than 365 days to add (a year) then we calculate the number of
        // rounded years between the current date and the retrieved date
        int numOfRoundedYears = numOfRoundedYears(date, num / 365, isPositive);
        // use function that uses recursive calls
        return addToDateAux(new Date(date.getDay(), date.getMonth(), date.getYear() + num / 365),
                num % 365 + numOfRoundedYears, isPositive);

    }

    /**
     * calculates the number of rounded years between the given date and the given date + numOfYears param
     *
     * @param date       current date
     * @param numOfYears number of years to add to the date
     * @param isPositive is the numOfYears is positive
     * @return the number of rounded years between the given date and the given date + numOfYears param
     */
    private static int numOfRoundedYears(Date date, int numOfYears, boolean isPositive) {
        double year = date.getYear();
        // counts how many rounded years are between 0 and given date + numOfYears param
        double sum = (year + numOfYears) / 4 - (year + numOfYears) / 100 + (year + numOfYears) / 400;
        // subtracts the number of rounded years are between 0 and given date,
        // now in sum there are only the num of rounded years in our range
        sum -= (year) / 4 - (year) / 100 + (year) / 400;
        // round the sum upwards if the retrieved year is positive
        if (year + numOfYears > 0 && sum - (int) sum > 0) sum += 1;
        // we don't want to conclude this year in the calculating if it is a rounded year
        // and we already passed the 29 of february
        if (date.getMonth() > 2 && isRoundedYear(date.getYear()) == 1 && isPositive)
            return (int) sum - 1;
            //analogy to the former if we need to subtract days
        else if (!isPositive && date.getMonth() < 3 && isRoundedYear(date.getYear() + numOfYears) == 1) {
            return (int) sum - 1;
        }
        return (-1) * (int) sum;
    }

    /**
     * recursive function, adds one day to the given day in each call
     *
     * @param date       current date
     * @param num        number of years to add to the date
     * @param isPositive is the numOfYears is positive
     * @return the wanted date (the given date + num)
     */
    private static Date addToDateAux(Date date, int num, boolean isPositive) {
        if (num == 0) return date;
        // if num is positive then use calculateNextDay func and use recursive call with num - 1
        if (isPositive) return addToDateAux(calculateNextDay(date), num - 1, isPositive);
        // if num is negative then use calculatePreviousDay func and use recursive call with num + 1
        return addToDateAux(calculatePreviousDay(date), num + 1, isPositive);
    }

    /**
     * calculate next day of given date
     *
     * @param date current date
     * @return the date with one more day according to restrictions
     */
    private static Date calculateNextDay(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        // month has 28 days
        if (month == 2 && day == 28) {
            if (isRoundedYear(year) == 1) {
                return new Date(day + 1, month, year);
            }
            return new Date(1, month + 1, year);
        }
        // month has 30 days
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 30)
            return new Date(1, month + 1, year);
        // month has 31 days
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day == 31) {
            if (month == 12) {
                return new Date(1, 1, year + 1);
            }
            return new Date(1, month + 1, year);
        }
        return new Date(day + 1, month, year);
    }

    /**
     * calculate previous day of given date
     *
     * @param date current date
     * @return the date with one less day according to restrictions
     */
    private static Date calculatePreviousDay(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        // month has 28 days
        if (month == 3 && day == 1) {
            if (isRoundedYear(year) == 1) {
                return new Date(29, month - 1, year);
            }
            return new Date(28, month - 1, year);
        }
        // month has 30 days
        if ((month == 5 || month == 7 || month == 10 || month == 12) && day == 1)
            return new Date(30, month - 1, year);
        // month has 31 days
        if ((month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11 || month == 1) && day == 1) {
            if (month == 1) {
                return new Date(31, 12, year - 1);
            }
            return new Date(31, month - 1, year);
        }
        return new Date(day - 1, month, year);
    }

    /**
     * checks if a given year has the 29 of february
     *
     * @param year the year to check
     * @return true if the year is rounded and false otherwise
     */
    private static int isRoundedYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 1 : 0;
    }
}
