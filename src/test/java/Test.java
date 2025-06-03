import com.microsoft.playwright.Playwright;

public class Test {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        playwright.chromium().launch();
    }
}
