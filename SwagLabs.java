package Assessment;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class SwagLabs 
{
	
	public static void main(String[] args) 
	{
		
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		BrowserContext brContent=browser.newContext();
		Page page = brContent.newPage();
		
		page.navigate("https://www.saucedemo.com/");
		
        System.out.println("TASK : Write end-to-end tests for described using Playright");
		System.out.println("SCENARIO-2 : For Failed Login with Invalid Password ");
		
		// Login with invalid password
		page.locator("[name='user-name']").fill("standard_user");
		page.locator("[name='password']").fill("standard_user");
		page.locator("[name='login-button']").click();
		
		brContent.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("applogin.json")));
		
		
		ElementHandle errorMessage = page.querySelector("[data-test='error']"); 
		
		if (errorMessage != null)
		{
			System.out.println("Login failed. Invalid password.");
		} 
		else
		{
			System.out.println("Login was successful.");
		}
		
		browser.close();
	
	}
}