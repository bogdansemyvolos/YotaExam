package com.yota.tests;

import com.yota.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PaymentPageTest extends TestBase {
	
	@Parameters({ "path" })
	@BeforeClass
	public void testInit(String path) {
		webDriver.get(websiteUrl + path);
	}

	@DataProvider
	public Object[][] amount() {
		return new Object[][]{
				{"653", "653"},
				{"0,89", "0,89"},
				{"0.32", "0.32"},
				{"abcd", "0"},
				{"-250", "0"},
				{"!@#$%^&*", "0"},
				{" ", "0"},
				{"1 000 000", "0"},
		};
	}

	@Test(dataProvider = "amount")
	public void testPayment(String amount, String expect) {
		info("-----==== Тест оплаты ====-----");
		PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
		paymentPage.doPayment(amount);
		paymentPage.assertBalance(expect);
		info("-----==== Тест завершен успешно ====-----");
	}

	@DataProvider
	public Object[][] speed() {
		return new Object[][]{
				{"64Кбит/сек (макс.)"},
				{"320Кбит/сек (макс.)"},
				{"416Кбит/сек (макс.)"},
				{"512Кбит/сек (макс.)"},
				{"640Кбит/сек (макс.)"},
				{"768Кбит/сек (макс.)"},
				{"896Кбит/сек (макс.)"},
				{"1.0Мбит/сек (макс.)"},
				{"1.3Мбит/сек (макс.)"},
				{"1.7Мбит/сек (макс.)"},
				{"2.1Мбит/сек (макс.)"},
				{"3.1Мбит/сек (макс.)"},
				{"4.1Мбит/сек (макс.)"},
				{"5.0Мбит/сек (макс.)"},
				{"5.7Мбит/сек (макс.)"},
				{"6.4Мбит/сек (макс.)"},
				{"7.1Мбит/сек (макс.)"},
				{"7.8Мбит/сек (макс.)"},
				{"8.5Мбит/сек (макс.)"},
				{"9.2Мбит/сек (макс.)"},
				{"10.0Мбит/сек (макс.)"},
				{"12.0Мбит/сек (макс.)"},
				{"15.0Мбит/сек (макс.)"},
				{"МаксМбит/сек (макс.)"},
		};
	}

	@Test(dataProvider = "speed")
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

	@Test
	@Parameters({ "speedButtonDisableTest" })
	public void testFakeBalance(String speed) {
		info("-----==== Тест фейкового пополнения ====-----");
		PaymentPage paymentPage = new PaymentPage();
		paymentPage.doReset();
		paymentPage.switchTariff(speed);
		paymentPage.setBalance(paymentPage.getTariff(speed).getCost());
		paymentPage.assertDoPurchaseButtonIsDisable();
		info("-----==== Тест завершен успешно ====-----");
	}
}
