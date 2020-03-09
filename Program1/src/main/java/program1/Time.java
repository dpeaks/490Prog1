/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class gets the time and formats it correctly.
 * @author Dante
 */
class Time {

    /**
     * Get the current time
     * @return the time at a current moment
     */
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
    
    /**
     * Format the time to display the milliseconds
     * @param dateTime
     * @return 
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern( "hh:mm:ss:nnnnnnnnnnnnnnn"));
    }

}
