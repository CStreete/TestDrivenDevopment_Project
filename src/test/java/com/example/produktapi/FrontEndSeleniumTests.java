package com.example.produktapi;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FrontEndSeleniumTests {
    // SetUp

    static WebDriver webDriver;
    @BeforeAll
    public static void setUp (){


    }
    @BeforeEach
    void getURL(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        webDriver = new ChromeDriver(options);
        webDriver.get("https://java22.netlify.app/");
        webDriver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);
    }
    @AfterAll
    public static void close(){
        webDriver.quit();
    }


    // Image Src Attribute & Image Load Tests
    // Fjallraven Foldsack No1 Backpack Fits 15 Laptops
    @Test
    void ControlSrcAttributeFjallravenFoldsackNo1BackpackFits15Laptops(){
        WebElement srcString = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[1]/div/img"));
        assertEquals("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg", srcString.getAttribute("src"));
    }
    @Test
    void ControlImageLoadFjallravenFoldsackNo1BackpackFits15Laptops(){
        WebElement productImage = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg')]")));
        assertTrue(productImage.isDisplayed(), "Image is not loaded");
    }

    // Casual Premium Slim Fit TShirts
    @Test
    void ControlSrcAttributeCasualPremiumSlimFitTShirts(){
        WebElement srcString = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[2]/div/img"));
        assertEquals("https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg", srcString.getAttribute("src"));
    }

    @Test
    void ControlImageLoadMensCasualPremiumSlimFitTShirts(){
        WebElement productImage = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg')]")));
        assertTrue(productImage.isDisplayed(), "Image is not loaded");
    }

    // Mens Cotton Jacket
    @Test
    void ControlSrcAttributeMensCottonJacket(){
        WebElement srcString = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[3]/div/img"));
        assertEquals("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg", srcString.getAttribute("src"));
    }

    @Test
    void ControlImageLoadMensCottonJacket(){
        WebElement productImage = new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg')]")));
        assertTrue(productImage.isDisplayed(), "Image is not loaded");
    }

    // Number Of Products Control
    @Test
    void ControlNumberOfProducts(){
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(20,products.size(), "Not all products are loaded");
    }

    // Page Title Control
    @Test
    void ControlOfPageTitle(){
        String pageTitle = webDriver.getTitle();
        assertEquals("Webbutik", pageTitle, "Title is Incorrect");
    }


    // Price Controls
    @Test
    void ControlOfFjallravenFoldsackNo1BackpackFits15LaptopsPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[1]/div/div/p")).getText();
        assertTrue(pElement.contains("109.95"), "Displayed price is incorrect");

    }
    @Test
    void ControlOfMensCasualPremiumSlimFitTShirtsPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[2]/div/div/p")).getText();
        assertTrue(pElement.contains("22.3"),"Displayed price is incorrect");
    }
    @Test
    void ControlOfMensCottonJacketPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[3]/div/div/p")).getText();
        assertTrue(pElement.contains("55.99"),"Displayed price is incorrect");
    }

    @Test
    void ControlOfMensCasualSlimFitPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[4]/div/div/p")).getText();
        assertTrue(pElement.contains("15.99"),"Displayed price is incorrect");
    }
    @Test
    void ControlOfAcerSB220QBi215inchesFullHD1920x1080IPSUltraThinPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[13]/div/div/p")).getText();
        assertTrue(pElement.contains("599"),"Displayed price is incorrect");
    }
    @Test
    void ControlOfSamsung49InchCHG90144HzCurvedGamingMonitorLC49HG90DMNXZASuperUltraScreenQLEDPrice(){
        String pElement = webDriver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[14]/div/div/p")).getText();
        assertTrue(pElement.contains("999.99"),"Displayed price is incorrect");
    }


    // Category Click Tests
    @Test
    void ControlAmountOfProductsShownWhenClickedOnAll(){
        WebElement electronicsButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/a"));
        electronicsButton.click();
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(20,products.size());
    }

    @Test
    void ControlAmountOfProductsShownWhenClickedOnElectronics(){
        WebElement electronicsButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/a"));
        electronicsButton.click();
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(6,products.size());
    }
    @Test
    void ControlAmountOfProductsShownWhenClickedOnJewelery(){
        WebElement electronicsButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a"));
        electronicsButton.click();
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(4,products.size());
    }

    @Test
    void ControlAmountOfProductsShownWhenClickedOnMenClothing(){
        WebElement electronicsButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[4]/a"));
        electronicsButton.click();
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(4,products.size());
    }

    @Test
    void ControlAmountOfProductsShownWhenClickedOnWomenClothing(){
        WebElement electronicsButton = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[5]/a"));
        electronicsButton.click();
        List<WebElement> products = webDriver.findElements(By.className("productItem"));
        assertEquals(6,products.size());
    }

    // Category Control
    @Test
    void ControlHomePageButtonName(){
        WebElement homepageBtn = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/a"));
        assertEquals("Startsida", homepageBtn.getText());
    }
    @Test
    void ControlElectronicsButtonName(){
        WebElement homepageBtn = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/a"));
        assertEquals("electronics", homepageBtn.getText());
    }
    @Test
    void ControlJeweleryButtonName(){
        WebElement homepageBtn = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a"));
        assertEquals("jewelery", homepageBtn.getText());
    }
    @Test
    void ControlMenClothingButtonName(){
        WebElement homepageBtn = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[4]/a"));
        assertEquals("men's clothing", homepageBtn.getText());
    }
    @Test
    void ControlWomenClothingButtonName(){
        WebElement homepageBtn = webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/div[5]/a"));
        assertEquals("women's clothing", homepageBtn.getText());
    }


    @Test
    void ControlProductAllProductNames (){
        List<WebElement> productTitles = webDriver.findElements(By.className("card-title"));




    }









}
