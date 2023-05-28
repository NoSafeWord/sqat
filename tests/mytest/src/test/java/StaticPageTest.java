import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StaticPageTest extends PageBase{

    //xpath
    private By imageBy = By.xpath("//header[@id='header']//img");

    //const
    public StaticPageTest(WebDriver driver) {
        super(driver);
        this.driver.get("https://hu.forum.grepolis.com/index.php?threads/f%C3%B3rum-szab%C3%A1lyzat.1/");
    }

    public String getTitle(){
        return this.driver.getTitle();
    }

    public Boolean hasTitleImage(){
        WebElement image = this.waitAndReturnElement(imageBy);
        return image != null;
    }
}

