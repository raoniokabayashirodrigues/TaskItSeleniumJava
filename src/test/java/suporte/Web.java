package suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Web {
    public static WebDriver createChrome(){

        //Configura o WebDriver
        System.setProperty("webdriver.chrome.driver","C:/cmder/chromedriver.exe");
        WebDriver navegador = new ChromeDriver();
        //Maximiza a tela
        navegador.manage().window().maximize();
        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Navega para a homepage.
        navegador.get("http://www.juliodelima.com.br/taskit/");

        return navegador;
    }
}
