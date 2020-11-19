package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuariosTestData.csv")

public class InformacoesUsuariosTest {
    private WebDriver navegador;

    //Colhe o nome do método atual exempro: removerUmaContaDeUmUsuario foi substituido por test.getMethodName()
    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){

        navegador = Web.createChrome();

        //Clicar no link com texto Sign In
        navegador.findElement(By.linkText("Sign in")).click();
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no campo de com name "login" o texto "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar no campo de com name "password" o texto "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //clicar no link Sing In
        navegador.findElement(By.linkText("SIGN IN")).click();
        //Valido se estou logado, que dentro do elemento com class "me" contém o texto "Hi, Julio"
        WebElement me = navegador.findElement(By.className("me"));
        String textoNoElementoMe = me.getText();
        assertEquals("Hi, Julio", textoNoElementoMe);

        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //clicar no link que contem o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }
    @Test
    public void testAdicionarUmaInformaçãoUsuario(@Param(name="tipo")String tipo,
                                                  @Param(name="contato")String contato,
                                                  @Param(name="mensagem")String mensagem){

        //clicar no botão que contém o texto + ADD MORE DATA
        navegador.findElement(By.xpath("//*[@id=\"moredata\"]/div[2]/button")).click();

        //identificar a popup aonde está o formulário de ID addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "type" escolher a opção phone
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        //No campo de name contact digitar +551199999999
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        //Clicar no link com texto save
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id toast-container validar que o texto é "Your contact has been added!"
        WebElement toastMessage = navegador.findElement(By.id("toast-container"));
        String mensagemAdd = toastMessage.getText();

        assertEquals(mensagemAdd, mensagem);
    }

    @Test
    public void removerUmaContaDeUmUsuario(){
        //Clicar no elemento pelo seu xpath
        //navegador.findElement(By.xpath("//span[text()=\"+5511999886688\"]/following-sibling::a")).click();
        navegador.findElement(By.xpath("//*[@id=\"moredata\"]/div[1]/ul/li[1]/a")).click();

        //confirmar a janela javascript
        navegador.switchTo().alert().accept();

        // Validar que a mensagem Rest in peace, dear phone! foi apresentada
        WebElement toastMessage = navegador.findElement(By.id("toast-container"));
        String mensagemAdd = toastMessage.getText();
        assertEquals(mensagemAdd, "Rest in peace, dear phone!");

        String screenshotArquivo = "C:/Users/Administrador/Pictures/Selenium" + Generator.dataHoraParaArquivo() + test.getMethodName() +
                ".png";
        Screenshot.tirar(navegador, screenshotArquivo
                 );
        //aguardar até 10 segundos até que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(toastMessage));

        //Clicar no texto Logout para sair do sistema.
        navegador.findElement(By.linkText("Logout")).click();

    }

    @After
    public void TearDown(){
        //fechar navegador
        navegador.close();
    }
}
