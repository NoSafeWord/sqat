import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MessagesPage extends PageBase {

    //xpath
    private By messagesBy = By.xpath("//a[contains(@class,'js-badge--conversations')]");
    private By filterBy = By.xpath("//a[contains(@class,'filterBar-menuTrigger')]");
    //opened filter
    private By filterOpenBy = By.xpath("//a[contains(@class,'filterBar-menuTrigger is-menuOpen')]");
    //radio input
    private By radioBy = By.xpath("//input[@type='radio' and @value='']");

    //const
    public MessagesPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://hu.forum.grepolis.com/index.php?conversations/");
    }

    //switch to inbox by clicking
    public MessagesPage gotoAllMessages(){
        this.waitAndReturnElement(messagesBy).click();
        this.waitAndReturnElement(messagesBy).click();
        return new MessagesPage(this.driver);
    }

    //interact with filter
    public void expandFilter(){
        this.waitAndReturnElement(filterBy).click();
    }

    //validate  if filter is visible
    public Boolean isFilterExpanded(){
        WebElement f = this.waitAndReturnElement(filterOpenBy);
        return f != null;
    }

    //check if any of the readio buttons are checked
    public Boolean isRadioInputChecked() {
        WebElement radioInput = driver.findElement(radioBy);
        String checkedAttribute = radioInput.getAttribute("checked");
        return checkedAttribute != null;
    }
}
