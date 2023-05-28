import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Cookie;

import java.net.URL;
import java.net.MalformedURLException;


public class FirstSeleniumTest {
    public WebDriver driver;
    
    @Before
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        /*
         * Modify the web driver in any way. A
         */
        options.addArguments("--lang=hu-HU"); //Set language to magyar
        options.addArguments("--disable-extensions"); //disable extensions
        //options.addArguments("--blink-settings=imagesEnabled=false"); //removes all images

        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }
    
    /*
     * Test case to check if the site can be reached and opened by retriving the page title (Also a task).
     * In this case the webpage title will be 'Grepolis Forum - HU'
     */
    @Test
    public void B_readPageTitleTest() {
        try {
            MainPage mainPage = new MainPage(this.driver);
            String titleText = mainPage.driver.getTitle();
            Assert.assertTrue(titleText.contains("Grepolis Forum - HU"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("readPageTitleTest failed: " + e.getMessage());
        }
    }

    /*
     * Fill simple form and send (eg. Login)
     * Here we still use the MainPage class for login since the page doesn't redirect (we have a login modal popup)
     * Using initLogin() we check for the popup, send usernam + password and click login
     * Than using getLoginStatus() we check if the user div is not "Bejelentkezés" meaning we are successfully loged into an account.
     */
    @Test
    public void B_LoginTest() {
        try{
            String username = "JrZee";
            String password = "QyM7M6qBnbJ765g3zUqMfEukxC4apx2";

            MainPage mainPage = new MainPage(this.driver);
            mainPage.initLogin(username,password);

            String status = mainPage.getLoginStatus();
            Assert.assertNotEquals(status,"Bejelentkezés");
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail("LoginTest failed: " + e.getMessage());
        }
    }

    /*
     * Logout test
     * Here open the page and login, to test if we can logout.
     * After the logout the text where the Username and other buttons  were should be saying "Bejelentkezés"
     * This indicates that we successfully loged out.
     */
    @Test
    public void B_LogoutTest() {
        try{
            //Login
            String username = "JrZee";
            String password = "QyM7M6qBnbJ765g3zUqMfEukxC4apx2";

            MainPage mainPage = new MainPage(this.driver);
            mainPage.initLogin(username,password);

            String status = mainPage.getLoginStatus();
            Assert.assertNotEquals(status,"Bejelentkezés");

            //Logout After Login and check if the current user text is replaced with the login text and button.
            mainPage.logout();
            String logout_status = mainPage.getAfterLogoutText();
            Assert.assertEquals(logout_status,"Bejelentkezés");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("LogoutTest failed: " + e.getMessage());
        }
    }

    /*
     * Form sending with user. (After a login you send any form.)
     * Simple search on the page, we first click to go to the advanced search page (SearchAdvancedPage.java)
     * Than we fill the form and click search. After that we redirected to the result page.(SearchResultPage.java)
     */
    @Test
    public void B_UserFormSendTest() {
        try {
            //LOGIN
            String username = "JrZee";
            String password = "QyM7M6qBnbJ765g3zUqMfEukxC4apx2";

            MainPage mainPage = new MainPage(this.driver);
            mainPage.initLogin(username,password);

            // FORM SEND
            String keyword = "katapult";

            SearchAdvancedPage advPage = mainPage.gotoAdvancedSearchPage();

            String title = advPage.isOnAdvancedSearchPage();
            Assert.assertEquals(title, "Keresés");
            SearchResultPage resultPage = advPage.search(keyword);

            String result = resultPage.isOnSearchResultPage();
            Assert.assertEquals(result, keyword);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("UserFormSendTest failed: " + e.getMessage());
        }
    }


     /*
     * Static Page test
     * Test a page of the site that doesn't need any form submit, only open and check if opened up correctly or not.
     */
    @Test
    public void B_staticPageTest() {
        try{
            StaticPageTest staticPage = new StaticPageTest(this.driver);
            String title = staticPage.getTitle();
            Assert.assertEquals(title, "Fórumszabályzat | Grepolis Forum - HU");
            Boolean hasImage = staticPage.hasTitleImage();
            Assert.assertTrue(hasImage);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("staticPageTest failed: " + e.getMessage());
        }
    }

    /*
     * Multiple page tests
     * Similarly like the selenium test1 using the  searchQuery array, we store the user  profiles we want to check.(UserProfilePage.java)
     * Each valid user profile should have a username field.
     */
    @Test
    public void B_multiplePageTest(){
        try{
            String[] urlQueries={"ivar.12640","oldfox.12427"};  
            for(String searchQuery : urlQueries) {  
                String url = "https://hu.forum.grepolis.com/index.php?members/"+searchQuery+"/";
                UserProfilePage userPage = new UserProfilePage(this.driver,url);
                Boolean validProfile = userPage.hasUsername();
                Assert.assertTrue(validProfile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("multiplePageTest failed: " + e.getMessage());
        }
    }
    
    /*
     * Read or write a checkbox.
     * In this case we read if a checkbox is checked for messages filters. isRadioInputChecked()
     */
    @Test
    public void B_messagePageCheckBoxTest(){
        try{
            // Login to access 
            String username = "JrZee";
            String password = "QyM7M6qBnbJ765g3zUqMfEukxC4apx2";
                    
            MainPage mainPage = new MainPage(this.driver);
            mainPage.initLogin(username,password);
                    
            //Swithc to messages page
            MessagesPage msgPage = new MessagesPage(this.driver);
            msgPage.gotoAllMessages();
                    
            //Checkbox
            msgPage.expandFilter();
            Assert.assertTrue(msgPage.isFilterExpanded());
            Assert.assertTrue(msgPage.isRadioInputChecked());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("messagePageCheckBoxTest failed: " + e.getMessage());
        }
    }

    /*
     * History Test
     */
    @Test
    public void A_historyTest(){
        try{
            // 2 links to user proflies
            String profileUrl1 = "https://hu.forum.grepolis.com/index.php?members/oldfox.12427/";
            String profileUrl2 = "https://hu.forum.grepolis.com/index.php?members/ivar.12640/";
            // init the driver with the 1st and go to the next
            UserProfilePage profilePage = new UserProfilePage(this.driver, profileUrl1);
            profilePage.driver.get(profileUrl2);
            Assert.assertEquals(driver.getCurrentUrl(), profileUrl2);
            //Go back
            profilePage.driver.navigate().back();
            //Verify
            Assert.assertEquals(driver.getCurrentUrl(), profileUrl1);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("A_historyTest failed: " + e.getMessage());
        }
    }

    /*
     * JavaScript Execution Test
     * Using javascrtipt to retrive title
     */
    @Test
    public void A_javaScriptExecutionTest(){
        try{
            String profileUrl = "https://hu.forum.grepolis.com/index.php?members/ivar.12640/";
            // init the driver with the 1st and go to the next
            UserProfilePage profilePage = new UserProfilePage(this.driver, profileUrl);
            String title = profilePage.testJS();
            Assert.assertEquals(title, "Ivar | Grepolis Forum - HU");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("A_javaScriptExecutionTest failed: " + e.getMessage());
        }
    }

    /*
     * Hover test
     * Check if hovering button texts changes their color.
     */
    @Test
    public void A_HoverTest(){
        try{
            MainPage mainPage = new MainPage(this.driver);
            Boolean h = mainPage.isHovered("rgba(87, 64, 47, 1)","rgba(45, 84, 136, 1)");
            Assert.assertTrue(h);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("A_HoverTest failed: " + e.getMessage());
        }  
    }

    /*
     * Cookie managment
     * Removing the autoaccpeted cookie (possibly session cookie because if deleted breaks the site)
     * This testcase restores the session cookie and checks if the site is usable after.
     */
    @Test
    public void A_CookiesTest(){
        try{
            MainPage mainPage = new MainPage(this.driver);
            //Obtain xf_csrf cookie
            Cookie c = mainPage.getCookies("xf_csrf");
            //Check cookie error
            mainPage.deleteCookies();
            //Reset cookie
            mainPage.addCookie(c);
            //validate
            Boolean h = mainPage.isCookiesValid();
            Assert.assertTrue(h);
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("A_deletedCookiesTest failed: " + e.getMessage());
        }
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
