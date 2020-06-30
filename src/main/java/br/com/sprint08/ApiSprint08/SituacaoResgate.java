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
public class SituacaoResgate {
	static ExtentTest test;
	static ExtentReports report;

	Response response = null;
	String urlBase = "http://localhost:8007/rest/v1/situacaoresgate/json/listar";

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(
				System.getProperty("user.dir") + "\\ApiSituacaoResgate_" + dataHoraParaArquivo() + ".html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarSituacaoResgate() {

		test = report.startTest("CT01_ListarSituacaoResgate");

		PropertyConfigurator.configure("tools/log4j-html.properties");

		System.out.println("#### CT01 - Listar Situação de Resgate ####\n");

		Response response = given().contentType("application/json").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);

		List<String> QuantidadeSituacaoResgate = response.jsonPath().getList("DescricaoSubDominio");
		test.log(LogStatus.INFO, "Situações", QuantidadeSituacaoResgate.toString());

		if (response.getStatusCode() == 200) {
			Assert.assertEquals(true,
					response.jsonPath().getJsonObject("Codigo").toString().contains("cod_sts_pgm_res") == true);
			test.log(LogStatus.PASS, "Retorno", response.getBody().prettyPrint());
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
