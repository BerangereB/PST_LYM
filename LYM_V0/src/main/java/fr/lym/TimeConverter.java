package fr.lym;

/**
 * Created by bourd on 11/02/2018.
 */

public class TimeConverter {
    /**
     *  This function converts milliseconds
     *  to format Hours:Minutes:seondes
     */

    public static String millisecondsToTimer(long milliseconds){
        String endOfString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000*60*60));
        int minutes = (int) (milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ( (milliseconds % (1000*60*60)) % (1000*60)) / (1000);

        if(hours > 0){
            endOfString = hours + ":";
        }
        if(seconds < 10){
            secondsString = "0"+seconds;
        }else{
            secondsString = ""+seconds;
        }
        endOfString = endOfString + minutes + ":" + secondsString;

        return endOfString;
    }

    public int getProgressPercentage(long currentPosition, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentPosition / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        percentage =  ( ((double) (currentSeconds))  / totalSeconds )*100;

        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }
}
