package br.com.sprint08.ApiSprint08;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListarProdutosAtivos {
	static ExtentTest test;
	static ExtentReports report;

	Response response = null;
	String urlBase = "http://localhost:8007/rest/v1/produto/json/listar";

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(
				System.getProperty("user.dir") + "\\ApiListarProdutosAtivos_" + dataHoraParaArquivo() + ".html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarProdutosAtivos() {

		test = report.startTest("CT01_ListarProdutosAtivos");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT02 - Listar produtos Ativos ####\n");

		Response response = given().contentType("application/json").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);
		test.log(LogStatus.INFO, "Produtos", response.jsonPath().getJsonObject("Descricao").toString());

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true,
					response.jsonPath().getJsonObject("Parceria").toString().contains("Descricao") == true);
			test.log(LogStatus.PASS, "Json", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	@Test
	public void CT02_ListarProdutosComParametro() {

		test = report.startTest("CT02_ListarProdutosComParametro");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT02 - Listar produtos com algum parâmetros de busca ####\n");

		Response response = given().contentType("application/json").param("codParceria", "195").get(urlBase);

		test.log(LogStatus.INFO, "Chamando a Api", urlBase + "?codParceria=195");
		test.log(LogStatus.INFO, "Produtos", response.jsonPath().getJsonObject("Descricao").toString());

		System.out.println(response.jsonPath().getJsonObject("Descricao"));

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true,
					response.jsonPath().getJsonObject("Parceria").toString().contains("Descricao") == true);
			test.log(LogStatus.PASS, "Json", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	@Test
	public void CT03_ListarProdutosComCodigo() {

		test = report.startTest("CT03_ListarProdutosComCodigo");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT02 - Listar produtos com código ####\n");

		Response response = given().contentType("application/json").param("codProduto", "9028").get(urlBase);

		test.log(LogStatus.INFO, "Chamando a Api", urlBase + "?codProduto=9028");
		test.log(LogStatus.INFO, "Produtos", response.jsonPath().getJsonObject("Descricao").toString());

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true,
					response.jsonPath().getJsonObject("Parceria").toString().contains("Descricao") == true);
			test.log(LogStatus.PASS, "Json", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	public static String dataHoraParaArquivo() {

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat("dd-MM-yyyy_hhmm").format(ts);

	}

	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();
	}

}
