import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

class SearchResultPage extends PageBase {

    //xpath
    private By emBy = By.xpath("//h1[contains(@class,'p-title-value')]//em");

    //const
    public SearchResultPage(WebDriver driver) {
        super(driver);
    }    
     
    //validat if switch to search result happened
    public String isOnSearchResultPage() {
        return this.waitAndReturnElement(emBy).getText();
    }

}
