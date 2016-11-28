package com.yota.tests;

import com.yota.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PaymentPageTest extends TestBase {
	
	@Parameters({ "path" })
	@BeforeClass
	public void testInit(String path) {
		webDriver.get(websiteUrl + path);
	}

	@Test
	@Parameters({ "amount" })
	public void testPayment(String amount) {
		info("-----==== Тест оплаты ====-----");
		PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
		paymentPage.doPayment(amount);
		paymentPage.assertBalance(amount);
		info("-----==== Тест завершен успешно ====-----");
	}

	@Test
    @Parameters({ "speedForConnect" })
	public void testConnectTariff(String speed) {
		info("-----==== Тест подключения тарифа ====-----");
        PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
        paymentPage.switchTariff(speed);
        paymentPage.doDeficientPayment();
        paymentPage.doPurchaseClick();
        paymentPage.assertTariff(speed, "30");
		info("-----==== Тест завершен успешно ====-----");
	}

	@Test
	@Parameters({ "speedButtonDisableTest" })
	public void testButtonDisable(String speed) {
		info("-----==== Тест недоступности кнопки \"Подключить\" ====-----");
		PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
		paymentPage.assertDoPurchaseButtonIsDisable();
		paymentPage.switchTariff(speed);
		paymentPage.assertDoPurchaseButtonIsDisable();
		paymentPage.doDeficientPayment();
		paymentPage.assertDoPurchaseButtonIsEnable();
		paymentPage.doPurchaseClick();
		paymentPage.doDeficientPayment();
		paymentPage.assertDoPurchaseButtonIsDisable();
		info("-----==== Тест завершен успешно ====-----");
	}

	@Test
	@Parameters({ "speedForReset" })
	public void testReset(String speed)
	{
		info("-----==== Тест сброса ====-----");
		PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
		paymentPage.switchTariff(speed);
		paymentPage.doDeficientPayment();
        paymentPage.doPurchaseClick();
		paymentPage.doReset();
		paymentPage.assertTariff(PaymentPage.Tariff.KB64.getSpeed(), "0");
		paymentPage.assertBalance("0");
		info("-----==== Тест завершен успешно ====-----");

	}
}
