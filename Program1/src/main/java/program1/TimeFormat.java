/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Dante
 */
class TimeFormat {

    public static String formatDateTime(LocalDateTime dateTime) {
        //hh:mm:ss a zzz
        return dateTime.format(DateTimeFormatter.ofPattern( "hh:mm:ss:nnnnnnnnnnnnnnn"));
    }
    
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
