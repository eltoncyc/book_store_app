/**
 * Project: Books
 * File: Common.java
 */

package app.book.data.util;


public class Common {

    /**
     * If the input string exceeds the length then truncate the string to the length - 3 characters and add "..."
     * 
     * @param title
     * @param length
     * @return a string
     */
    public static String truncateIfRequired(String title, int length) {
        if (title.length() > length) {
            title = title.substring(0, length - 3) + "...";
        }

        return title;
    }

}
