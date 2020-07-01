package br.com.sprint08.ApiSprint08;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

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
public class ListaNomedaParceria {

	static ExtentTest test;
	static ExtentReports report;

	Response response = null;
	String urlBase = "http://localhost:8007/rest/v1/parceria/json/listar";

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(
				System.getProperty("user.dir") + "\\ApiListaNomedaParceria_" + dataHoraParaArquivo() + ".html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarParcerias() {

		test = report.startTest("CT01_ListarParcerias");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT01 - Listar Parceria ####\n");

		Response response = given().contentType("application/json").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);

		List<Object> QuantidadeSituacaoResgate = response.jsonPath().getList("Descricao");
		test.log(LogStatus.INFO, "Parcerias", QuantidadeSituacaoResgate.toString());

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true, response.jsonPath().getJsonObject("Pessoa").toString().contains("Nome") == true);
			test.log(LogStatus.PASS, "Json", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	@Test
	public void CT02_ListarParceriasPorCodigo() {

		test = report.startTest("CT02_ListarParceriasPorCodigo");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT02 - Listar Parceria por Código ####\n");

		Response response = given().contentType("application/json").param("codParceria", "3").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase + "?codParceria=3");

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true, response.jsonPath().getJsonObject("Pessoa").toString().contains("Nome") == true);
			test.log(LogStatus.PASS, "Json", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	@Test
	public void CT03_ListarParceriasPorCNPJ() {

		test = report.startTest("CT03_ListarParceriasPorCNPJ");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT02 - Listar Parceria por CNPJ ####\n");

		Response response = given().contentType("application/json").param("cpfcnpj", "51990695000137").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase + "?cpfcnpj=51990695000137");

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true, response.jsonPath().getJsonObject("Pessoa").toString().contains("Nome") == true);
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
