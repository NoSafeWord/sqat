import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

public class UserProfilePage extends PageBase {

    //xpaht
    private By username = By.xpath("//span[contains(@class,'username')]");
    
    //cosnt
    public UserProfilePage(WebDriver driver, String url) {
        super(driver);
        this.driver.get(url);
    }

    public Boolean hasUsername() {
        WebElement uname = this.waitAndReturnElement(username);
        return uname != null && uname.isDisplayed();
    }
    
    public String testJS() {
        // Create an instance of the JavascriptExecutor
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        // Execute JavaScript code
        String pageTitle = (String) jsExecutor.executeScript("return document.title;");
        return pageTitle;
    }
}
