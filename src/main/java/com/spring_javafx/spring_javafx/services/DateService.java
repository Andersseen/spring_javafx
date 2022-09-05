package com.spring_javafx.spring_javafx.services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class DateService {

    private final String patternFirstDay = "dd/MM/yyyy";
    private final String patternLocalMonth = "dd-MMM-yyyy";
    private final String patternFirstMonth = "MM/dd/yyyy";
    private final String patternFirstYear = "yyyy/MM/dd";

    List<String> formatStrings = Arrays.asList(patternFirstDay, patternLocalMonth, patternFirstMonth, patternFirstYear);


    public Date tryParse(String dateString) {
        Date date = null;
        for (String formatString : formatStrings)
        {
            try
            {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
//                new SimpleDateFormat(formatString).parse(dateString);
                java.util.Date parsed = format.parse(dateString);
                date = new Date(parsed.getTime());
                return date;
            }
            catch (ParseException e) {
                System.out.println();
            }
        }
        return date;
    }

}
