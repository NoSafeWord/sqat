import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchAdvancedPage extends PageBase{

    //xpath
    private By h1By = By.xpath("//h1[contains(@class,'p-title-value')]");
    private By keywordsBy = By.xpath("//ul[contains(@class,'inputList')]//input[@name='keywords']");
    private By searchBtnBy = By.xpath("//div[contains(@class,'formSubmitRow-controls')]//button[@type='submit']");

    //const
    public SearchAdvancedPage(WebDriver driver) {
        super(driver);
    }  

    //validat if we switched
    public String isOnAdvancedSearchPage() {
        return this.waitAndReturnElement(h1By).getText();
    }

    //fill search form and execute search
    public SearchResultPage search(String keyword){
        this.waitAndReturnElement(keywordsBy).sendKeys(keyword);
        this.waitAndReturnElement(searchBtnBy).click();
        return new SearchResultPage(this.driver);
    }
}
