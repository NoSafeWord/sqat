import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import java.util.Set;


class MainPage extends PageBase {

    //xpath
    private By footerBy = By.id("footer");
    //Login
    private By loginStatusBy = By.xpath("//a[contains(@class,'p-navgroup-link--user')]//span[contains(@class,'p-navgroup-linkText')]");
    private By startLoginButtonBy = By.xpath("//a[contains(@class,'p-navgroup-link--logIn')]");
    private By loginUsernameInputBy = By.xpath("//input[@name='login']");
    private By loginPasswordInputBy = By.xpath("//input[@name='password']"); 
    private By loginSignInButtonBy = By.xpath("//button[contains(@class,'button--icon--login')]");
    //Form search
    private By searchOnPageOpenButtonBy = By.xpath("//a[contains(@class,'p-navgroup-link--search')]");
    private By searchOnPageGotoAdvancedButtonBy = By.xpath("//span[contains(@class,'menu-footer-controls')]//a[contains(@class,'button')]");
    //Logout
    private By userAccountButton = By.xpath("//a[contains(@class, 'p-navgroup-link--user')]");
    private By logoutButtonBy = By.xpath("//div[contains(@class, 'menu--account')]//a[text()='Kijelentkezés']");
    private By afterLogoutText = By.xpath("//a[contains(@class,'p-navgroup-link--logIn')]//span[contains(@class,'p-navgroup-linkText')]");
    private By hoverButtonBy =  By.xpath("//h3[contains(@class,'node-title')]//a[text()='Szabályok']");
    private By closeModalBy =  By.xpath("//body//div[contains(@class,'overlay-title')]//a");

    //const 1
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://hu.forum.grepolis.com/index.php");
    } 

    //const 2
    public MainPage(WebDriver driver, String url){
        super(driver);
        this.driver.get(url);
    }

    // get footer text
    public String getFooterText() {
        return this.waitAndReturnElement(footerBy).getText();
    }

    // start a login process
    public MainPage initLogin(String u, String p) {
        this.waitAndReturnElement(startLoginButtonBy).click();

        this.waitAndReturnElement(loginUsernameInputBy).sendKeys(u);
        this.waitAndReturnElement(loginPasswordInputBy).sendKeys(p);
        
        this.waitAndReturnElement(loginSignInButtonBy).click();

        return new MainPage(this.driver);
    }

    // verification of login
    public String getLoginStatus() {
        return this.waitAndReturnElement(loginStatusBy).getText();
    }

    // switch to a different page -> AdvSearchPage
    public SearchAdvancedPage gotoAdvancedSearchPage(){
        this.waitAndReturnElement(searchOnPageOpenButtonBy).click();
        this.waitAndReturnElement(searchOnPageGotoAdvancedButtonBy).click();
        return new SearchAdvancedPage(this.driver);
    }

    //logout verification
    public String getAfterLogoutText(){
        return this.waitAndReturnElement(afterLogoutText).getText();
    }

    // Logout clicking
    public MainPage logout(){
        this.waitAndReturnElement(userAccountButton).click();
        this.waitAndReturnElement(logoutButtonBy).click();
        return new MainPage(this.driver);
    }

    //hover testing, <a> tag color changes
    public Boolean isHovered(String a, String b){
        WebElement toHover = this.waitAndReturnElement(hoverButtonBy);
        String originalColor = toHover.getCssValue("color");
        Actions actions = new Actions(this.driver);
        actions.moveToElement(toHover, 0, 0).perform();
        String hoveredColor = toHover.getCssValue("color");
        if(a.equals(originalColor)  && b.equals(hoveredColor)){return true;} else {return false;}
    }

    //delete cookies, than observe that we are unable to continue using the site
    public void deleteCookies(){
        this.driver.manage().deleteAllCookies();
    }

    //adds the stored cookie
    public void addCookie(Cookie c){
        this.driver.manage().addCookie(c);
    }

    //Retrun a specific cookie
    public Cookie getCookies(String cookieName){
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null; // Return null if the cookie is not found
    }

    //check if valid
    public Boolean isCookiesValid(){
        this.waitAndReturnElement(startLoginButtonBy).click();
        WebElement hoppa = this.waitAndReturnElement(closeModalBy);
        return hoppa != null;
    }
}
