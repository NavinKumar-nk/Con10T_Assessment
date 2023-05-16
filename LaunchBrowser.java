package Assessment;
// SCENARIO-1 : Successfull Login.
import java.nio.file.Paths;
import java.util.List:

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LaunchBrowser 
{
	public static void main(String[] args) throws Exception  
	{	

		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		//BrowserContext brContent= browser.newContext();
		BrowserContext brContent=browser.newContext(new Browser.NewContextOptions().setViewportSize(1366 , 768));
		Page page = brContent.newPage();
		
		// Maximize the window
		// Get the screen size
        int screenWidth = (int) page.evaluate("window.screen.width");
        int screenHeight = (int) page.evaluate("window.screen.height");
        
        // Set the viewport size to match the screen size
        page.setViewportSize(screenWidth, screenHeight);
		
        // Open Login Page
		page.navigate("https://www.saucedemo.com/");
		page.waitForLoadState();
        
        System.out.println("TASK : Write end-to-end tests for saucedemo.com using Playright.");
        System.out.println("\nSCENARIO-1 : Successfull Login.");
		
		// Enter valid user name & password
		page.locator("[name='user-name']").fill("standard_user");
		page.locator("[name='password']").fill("secret_sauce");
		// Click on login button
		page.locator("[name='login-button']").click();
		page.waitForLoadState();
		
		//brContent.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("applogin.json")));
		
        // Get the current URL
        String currentUrl = page.url();

        // Verify the redirection to the home page
        if (currentUrl.equals("https://www.saucedemo.com/inventory.html"))
        {
            System.out.println("\nUser is redirected to the home page.");
        }
        else
        {
             System.out.println("\nUser is not redirected to the home page. Current URL: " + currentUrl);
        }
         
        //Verify that the title Products is visible on the page
         // Get the text content of the title element
        String pageTitle = page.innerText("[class='title']");

        // Verify the title
        if (pageTitle.equals("Products"))
        {
            System.out.println("The title 'Products' is visible on the page.");
        }
        else
        {
            System.out.println("The title 'Products' is not visible on the page.");
        }

        // Click on Add to the cart on two random product
        page.click("[name='add-to-cart-sauce-labs-backpack']");
        Thread.sleep(1000);
        page.click("[name='add-to-cart-sauce-labs-bike-light']");
		Thread.sleep(1000);
        
		// Find the button element
        ElementHandle buttonElement = page.querySelector("[name='remove-sauce-labs-backpack']");

        // Get the current button text
        String buttonText = buttonElement.innerText();

        // Verify the button text changes to remove
        if (buttonText.equals("Remove"))
        {
            System.out.println("The button text has changed to 'Remove'.");
        }
        else
        {
            System.out.println("The button text has not changed to 'Remove'. Current text: " + buttonText);
        }
    
        //Verify that the number on cart changes to 2
        ElementHandle cartElement = page.querySelector("[class='shopping_cart_badge']");
        String cartValue = cartElement.innerText();
        System.out.println("Number on the cart : " +cartValue);

        // Click on Cart button
        page.click("[class='shopping_cart_link']");
        page.waitForLoadState();
        
        // Wait for the page to load completely
        page.waitForLoadState();

        // Verify that the user is redirected to cart page
        if (page.url().equals("https://www.saucedemo.com/cart.html"))
        {
            System.out.println("User is redirected to the cart page.");
        }
        else
        {
            System.out.println("User is not redirected to the cart page.");
        }
        
        //Verify that 2 items are visible on the Page
        List<ElementHandle> itemElements = page.querySelectorAll("[class='cart_item']");

        // Verify the number of visible items
        if (itemElements.size() == 2)
        {
            System.out.println("2 items are visible on the cart page.");
        } else
        {
            System.out.println("Expected 2 items, but found " + itemElements.size() + " items on the cart page.");
        }
        
        // click on checkout Button
        page.click("[class='btn btn_action btn_medium checkout_button']");
        page.waitForLoadState();

        // Verify that user is redirected to information form page
        if (page.url().equals("https://www.saucedemo.com/checkout-step-one.html"))
        {
            System.out.println("User is redirected to the information form page.");
        }
        else
        {
            System.out.println("User is not redirected to the information form page.");
        }
        
        Thread.sleep(2000);
        
        // Input data in the requisite fields
        page.locator("[name='firstName']").fill("Jhon");
		page.locator("[name='lastName']").fill("Wick");
		page.locator("[name='postalCode']").fill("0001111");
		Thread.sleep(2000);
		//Click on Continue button
		page.locator("[name='continue']").click();
		page.waitForLoadState();
 	        
		// Verify that the user is redirected to checkout overview page
		if (page.url().equals("https://www.saucedemo.com/checkout-step-two.html"))
		{
		     System.out.println("User is redirected to the checkout overview page.");
		}
		else
		{
		    System.out.println("User is not redirected to the checkout overview page.");
		}
		
        // Scroll to the bottom of the page
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        
        // Wait for the page to load
        page.waitForLoadState();

        // Verify that total amount is sum of item total and tax
     
        // Get the item total value and remove the dollar symbol
        String itemTotalText = page.innerText("[class='summary_subtotal_label']");
        String itemTotalValue = itemTotalText.replaceAll("[^0-9.]", "");

        // Get the tax value and remove the dollar symbol
        String taxText = page.innerText("[class='summary_tax_label']");
        String taxValue = taxText.replaceAll("[^0-9.]", "");

        // Get the total amount value and remove the dollar symbol
        String totalAmountText = page.innerText("[class='summary_info_label summary_total_label']");
        String totalAmountValue = totalAmountText.replaceAll("[^0-9.]", "");

        // Convert the item total and tax to numeric values
        double itemTotal = Double.parseDouble(itemTotalValue);
        double tax = Double.parseDouble(taxValue);

        // Calculate the expected total amount
        double expectedTotalAmount = itemTotal + tax;

        // Convert the total amount to a numeric value
        double actualTotalAmount = Double.parseDouble(totalAmountValue);

        // Verify if the actual total amount matches the expected total amount
        if (actualTotalAmount == expectedTotalAmount)
        {
            System.out.println("Verification passed, total amount is the sum of item total and tax.");
        }
        else
        {
            System.out.println("Verification failed, total amount is not the sum of item total and tax");
        }
    
		// Click on finish button
        page.click("[name='finish']");
		page.waitForLoadState();
		
		// Verify that the user is redirected to checkout complete page
		if (page.url().equals("https://www.saucedemo.com/checkout-complete.html"))
		{
		     System.out.println("User is redirected to the checkout complete page.");
		}
		else
		{
		    System.out.println("User is not redirected to the checkout complete page.");
		}
		
		Thread.sleep(2000);
        
		// Verify that the text Thank you for your order! appears on the page
        String tyfyoText = page.innerText("[class='complete-header']");
        System.out.println("The text "+tyfyoText+" is appeared on the page.");
        
		page.waitForLoadState();

        browser.close(); 
	}
}
