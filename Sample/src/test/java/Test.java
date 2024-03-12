//import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.*;

public class Test {
    static int WhichTime = 0;
    static SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
    static String BookingCity;
    static int BookingDate;

    static String MovieName;

    static DateFormat dateFormat = new SimpleDateFormat("dd");
    static Date date = new Date();
    static int today = Integer.parseInt(dateFormat.format(date));

    public static void main(String[] args) throws InterruptedException {

        Scanner inputObj = new Scanner(System.in);

        System.out.println(" --------------        Please enter your city  -------------");
        BookingCity = inputObj.nextLine();
        System.out.println(" --------------        Please enter your booking date    -------------");
        BookingDate = inputObj.nextInt();

        boolean flagCheck = checkDateIsLessthanWeek(BookingDate);
        do {
            if(flagCheck){

            }
            else {
                System.out.println(" --------------        Please enter your booking date   -------------");
                BookingDate = inputObj.nextInt();
                flagCheck = checkDateIsLessthanWeek(BookingDate);
            }
        }while (!flagCheck);

        System.out.println(" --------------        Please enter your booking movie name ( with specific Languages if you want )   -------------");
        inputObj.nextLine();
        MovieName = inputObj.nextLine();

        ChromeDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "/Users/mohammedishaque/Desktop/projects/Selenium_projects/sample/Sample/src/main/resources/chromedriver");
        driver.get("https://in.bookmyshow.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//input[@placeholder=\"Search for your city\"]")).sendKeys(BookingCity);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder=\"Search for your city\"]")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.id("4")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//input[@placeholder=\"Search for Movies, Events, Plays, Sports and Activities\"])")).sendKeys(MovieName);
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//input[@placeholder=\"Search for Movies, Events, Plays, Sports and Activities\"])")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[@data-phase=\"postRelease\"])[1]")).click();
        Thread.sleep(2000);
        try {
            if (driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).isDisplayed())
                driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).click();
        } catch (Exception e) {
        }
        driver.findElement(By.xpath("//div[text()[normalize-space()=\""+BookingDate+"\"]]")).click();
        Thread.sleep(2000);
        try {
            if (driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).isDisplayed())
                driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).click();
        } catch (Exception e) {
        }
        int n = driver.findElements(By.className("__title")).size();
        int TheatreChoise;
        int  showChoise;
        List<WebElement> TheatreName = driver.findElements(By.className("__title"));
        List<WebElement> ShowTime = driver.findElements(By.className("showtime-pill-wrapper"));
        List<WebElement> ShowTimeIndivitual = driver.findElements(By.className("showtime-pill"));
        ArrayList<String> MatchedTimings = new ArrayList<String>();

        System.out.println(" Please choose theatre from below available list \n");
        for(int name = 0;name < TheatreName.size(); name++)
        {
            System.out.println("No."+ (name+1)+" " + TheatreName.get(name).getText().replaceAll("INFO", "").trim());
        }
        TheatreChoise = inputObj.nextInt();
        TheatreChoise=TheatreChoise-1;
        flagCheck = (TheatreChoise <= TheatreName.size() ? true : false);
        do {
            if(flagCheck){
                System.out.println(" --------------     Thanks for choosing your theatre name   -------------");
                MovieName = inputObj.nextLine();
            }
            else {
                System.out.println(" --------------        Please enter your booking movie row between 1 " + (TheatreName.size())+  "  -------------");
                TheatreChoise = inputObj.nextInt();
                TheatreChoise=TheatreChoise-1;
                flagCheck = (TheatreChoise <= TheatreName.size() ? true : false);
                System.out.println(flagCheck);
            }
        }while (!flagCheck);

        System.out.println(" --------------     You have choose this theatre   -------------" + TheatreName.get(TheatreChoise).getText().replaceAll("INFO", "").trim());


        String timing = (ShowTime.get(TheatreChoise).getText().trim()).replaceAll("Dolby 7.1", "").replaceAll("QSC 7.1", "").replaceAll("QSC 5.1", "").replaceAll("ATMOS", "").replaceAll("4K DOLBY 7.1", "").replaceAll("ENG", "").replaceAll("4K ATMOS enabled", "").replaceAll("4K", "").replaceAll("DolbyAtmos","").replaceAll("DOLBY","").replaceAll("7.1","").replaceAll("/","").trim();
            String listOftimings[] = timing.trim().split("\n");
            System.out.println("We have found " + listOftimings.length + " shows, feel free to select from below matches results !!!");

        for (int count = 0; count < listOftimings.length; count++) {
                    System.out.println((count+1) +" "+listOftimings[count]);
                }
        showChoise = inputObj.nextInt();
        flagCheck = (showChoise <= listOftimings.length ? true : false);

        do {
            if(flagCheck){
                System.out.println(" --------------     Thanks for choosing your show time   -------------");
                MovieName = inputObj.nextLine();
            }
            else {
                System.out.println(" --------------        Please enter your booking time row between 1" + (listOftimings.length)+  "  -------------");
                showChoise = inputObj.nextInt();
                showChoise=showChoise-1;
                flagCheck = (showChoise <= listOftimings.length ? true : false);
            }
        }while (!flagCheck);
        System.out.println(" --------------     Chosen time    -------------" + listOftimings[showChoise]);

        try {
            if (driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).isDisplayed())
                driver.findElement(By.xpath("//*[@id=\"wzrk-cancel\"]")).click();
        } catch (Exception e) {
        }

        ShowTimeIndivitual.get(showChoise).click();

        driver.findElement(By.id("btnPopupAccept")).click();

        String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
        String subWindowHandler = null;
        Thread.sleep(2000);
        Set<String> handles = driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler); // switch to popup window
        driver.findElement(By.id("pop_3")).click();
        driver.findElement(By.id("proceed-Qty")).click();
       // Now you are in the popup window, perform necessary actions here
        driver.switchTo().window(parentWindowHandler);  // switch back to parent window


        int Available_Size =  driver.findElements(By.className("_available")).size();
        int Booked_Size =  driver.findElements(By.className("_blocked")).size();
        System.out.println("------  Total no of available seats in this theatre  "+Available_Size );
        System.out.println("------  Total no of booked seats in this theatre  "+Booked_Size );
    }

    public static boolean checkDateIsLessthanWeek(int bdate)
    {
        int checkDate = today+4;
        if(checkDate > bdate)
            return true;
        else
        { return false; }
    }

}


//        boolean t = driver.findElement(By.partialLinkText("Amruth Digital 2K A/C Cinema: Lingarajapuram")).isDisplayed();
//        if(t)
//            System.out.println("The theatre you asked  available on that day ");
//        else
//            System.out.println("The theatre you asked not available on that day ");
//
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//a[@data-date-time='11:00 AM']"));
//        driver.findElement(By.id("btnPopupAccept")).click();
//        driver.findElement(By.id("pop_3")).click();
//        driver.findElement(By.id("proceed-Qty")).click();
//        int Available_Size =  driver.findElements(By.className("_available")).size();
//        System.out.println(Available_Size);
//        int Booked_Size =  driver.findElements(By.className("_blocked")).size();
//        System.out.println(Booked_Size);
//        element.click();



//        driver.findElement(By.xpath("//input[@class=\"react-autosuggest__input\"]")).sendKeys("Bangalore");
//        driver.findElement(By.id("nav-signin-tooltip")).click();
//        driver.findElement(By.id("ap_email")).sendKeys("ganeshkannan1410@gmail.com");
//        driver.findElement(By.id("continue")).click();
//        driver.findElement(By.id("ap_password")).sendKeys("MGganesh@14");
//        driver.findElement(By.id("signInSubmit")).click();
//        String userName = driver.findElement(By.id("nav-link-accountList-nav-line-1")).getText();
//        System.out.println(userName);
//        Assert.assertEquals("Unexpected username","Hello, Ganesh",userName);
//        driver.findElement(By.id("glow-ingress-block")).click();
//        System.out.println(driver.findElements(By.xpath("//div[@id='GLUXAddressBlock']/ul")).size());

