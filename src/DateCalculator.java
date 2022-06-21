public class DateCalculator {
    public static Date addToDate(Date date, int num) {
        boolean isPositive = num >= 0;
        int numOfRoundedYears = numOfRoundedYears(date, num / 365, isPositive);
        return addToDateAux(new Date(date.getDay(), date.getMonth(), date.getYear() + num / 365),
                num % 365 + numOfRoundedYears, isPositive);

    }

    private static int numOfRoundedYears(Date date, int numOfYears, boolean isPositive) {
        double year = (double)date.getYear();
        double sum = (year + numOfYears) / 4 - (year + numOfYears) / 100 + (year + numOfYears) / 400;
        sum -= (year) / 4 - (year) / 100 + (year) / 400;
        if (year + numOfYears > 0 && sum-(int)sum>0) sum += 1;
        if (date.getMonth() > 2 && isRoundedYear(date.getYear()) == 1 && isPositive)
            return (int)sum - 1;
        else if (!isPositive && date.getMonth() < 3 && isRoundedYear(date.getYear() + numOfYears) == 1) {
            return (int)sum - 1;
        }
        return (-1) * (int)sum;
    }

    private static Date addToDateAux(Date date, int num, boolean isPositive) {
        if (num == 0) return date;
        if (isPositive) return addToDateAux(calculateNextDay(date), num - 1, isPositive);
        return addToDateAux(calculatePreviousDay(date), num + 1, isPositive);
    }

    private static Date calculateNextDay(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        if (month == 2 && day == 28) {
            if (isRoundedYear(year) == 1) {
                return new Date(day + 1, month, year);
            }
            return new Date(1, month + 1, year);
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 30)
            return new Date(1, month + 1, year);
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day == 31) {
            if (month == 12) {
                return new Date(1, 1, year + 1);
            }
            return new Date(1, month + 1, year);
        }
        return new Date(day + 1, month, year);
    }

    private static Date calculatePreviousDay(Date date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        if (month == 3 && day == 1) {
            if (isRoundedYear(year) == 1) {
                return new Date(29, month - 1, year);
            }
            return new Date(28, month - 1, year);
        }
        if ((month == 5 || month == 7 || month == 10 || month == 12) && day == 1)
            return new Date(30, month - 1, year);
        if ((month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11 || month == 1) && day == 1) {
            if (month == 1) {
                return new Date(31, 12, year - 1);
            }
            return new Date(31, month - 1, year);
        }
        return new Date(day - 1, month, year);
    }

    private static int isRoundedYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 1 : 0;
    }
}
