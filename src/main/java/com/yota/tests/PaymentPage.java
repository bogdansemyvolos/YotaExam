package com.yota.tests;

import com.yota.elements.Button;
import com.yota.elements.Label;
import com.yota.elements.TextBox;
import org.openqa.selenium.By;

public class PaymentPage extends BasePage {

    /**
     * Базовый конструктор.
     */
    public PaymentPage() {
        super(By.xpath("//a[contains(.,'Пополнить')]"), "Пополнение и смена тарифа");
    }

    private TextBox amount = new TextBox(By.id("amount"), "Сумма пополнения");
    private Button doPayment = new Button(By.xpath("//a[contains(.,'Пополнить')]"), "Пополнить счет");
    private Button doReset = new Button(By.xpath("//a[contains(.,'Сброс')]"), "Сброс");
    private Button moveLeft = new Button(By.xpath("//a[contains(@data-bind,'click: moveLeft')]"), "Перемещение бегунка влево");
    private Button moveRight = new Button(By.xpath("//a[contains(@data-bind,'click: moveRight')]"), "Перемещение бегунка вправо");
    private Button doPurchase = new Button(By.xpath("//a[contains(.,'Подключить')]"), "Подключить");
    private Label balance = new Label(By.xpath("//span[contains(@data-bind,'balance')]"), "Баланс");
    private Label timeNow = new Label(By.xpath("//div[@class = 'tariff unavailable']//div[@class = 'time']/strong"), "Дней осталось (текущий тариф)");
    private Label speedNowNumber = new Label(By.xpath("//div[@class = 'tariff unavailable']//div[@class = 'speed']/strong"), "Скорость (текущий тариф) число");
    private Label speedNowText = new Label(By.xpath("//div[@class = 'tariff unavailable']//div[@class = 'speed']/span"), "Скорость (текущий тариф) текст");
    private Label costNow = new Label(By.xpath("//div[@class = 'tariff unavailable']//div[@class = 'cost no-arrow']/strong"), "Стоимость (текущий тариф)");
    private Label timeNew = new Label(By.xpath("//div[@class = 'tariff']//div[@class = 'time']/strong"), "Дней осталось (новый тариф)");
    private Label speedNewNumber = new Label(By.xpath("//div[@class = 'tariff']//div[@class = 'speed']/strong"), "Скорость (новый тариф) число");
    private Label speedNewText = new Label(By.xpath("//div[@class = 'tariff']//div[@class = 'speed']/span"), "Скорость (новый тариф) текст");
    private Label costNew = new Label(By.xpath("//div[@class = 'tariff']//div[@class = 'cost']/strong"), "Стоимость (новый тариф)");


    /**
     * Перечень тарифов
     */
    enum Tariff {
        KB64(1, "64Кбит/сек (макс.)", 0),
        KB320(2, "320Кбит/сек (макс.)", 300),
        KB416(3, "416Кбит/сек (макс.)", 350),
        KB512(4, "512Кбит/сек (макс.)", 400),
        KB640(5, "640Кбит/сек (макс.)", 450),
        KB768(6, "768Кбит/сек (макс.)", 500),
        KB896(7, "896Кбит/сек (макс.)", 550),
        MB1_0(8, "1.0Мбит/сек (макс.)", 600),
        MB1_3(9, "1.3Мбит/сек (макс.)", 650),
        MB1_7(10, "1.7Мбит/сек (макс.)", 700),
        MB2_1(11, "2.1Мбит/сек (макс.)", 750),
        MB3_3(12, "3.1Мбит/сек (макс.)", 800),
        MB4_1(13, "4.1Мбит/сек (макс.)", 850),
        MB5_0(14, "5.0Мбит/сек (макс.)", 900),
        MB5_7(15, "5.7Мбит/сек (макс.)", 950),
        MB6_4(16, "6.4Мбит/сек (макс.)", 1000),
        MB7_1(17, "7.1Мбит/сек (макс.)", 1050),
        MB7_8(18, "7.8Мбит/сек (макс.)", 1100),
        MB8_5(19, "8.5Мбит/сек (макс.)", 1150),
        MB9_2(20, "9.2Мбит/сек (макс.)", 1200),
        MB10_0(21, "10.0Мбит/сек (макс.)", 1250),
        MB12_0(22, "12.0Мбит/сек (макс.)", 1300),
        MB15_0(23, "15.0Мбит/сек (макс.)", 1350),
        MAX(24, "МаксМбит/сек (макс.)", 1400);


        private int number;
        private int cost;
        private String speed;

        /**
         * Устанавливает поля тарифа
         *
         * @param number порядковый номер тарифа
         * @param speed  скорость
         * @param cost   стоимость
         */
        Tariff(int number, String speed, int cost) {
            this.number = number;
            this.cost = cost;
            this.speed = speed;
        }

        /**
         * Возвращает скорость тарифа
         * @return скорость
         */
        public String getSpeed() {
            return speed;
        }

        /**
         * Возвращает стоимость тарифа
         * @return стоимость
         */
        public int getCost() {
            return cost;
        }

        /**
         * Возвращает порядковый номер тарифа
         * @return порядковый номер
         */
        public int getNumber() {
            return number;
        }
    }

    /**
     * Вводит сумму и нажимает на кнопку "Пополнить"
     * @param amount сумма пополнения
     */
    public void doPayment(String amount) {
        this.amount.setText(amount);
        doPayment.clickAndWait();
    }

    /**
     * Нажимает на кнопку перемещения бегунка влево
     */
    public void moveLeftClick() {
        moveLeft.clickAndWait();
    }

    /**
     * Нажимает на кнопку перемещения бегунка вправо
     */
    public void moveRightClick() {
        moveRight.jsClickAndWait();
    }

    /**
     * Нажимает на кнопку "Подключить"
     */
    public void doPurchaseClick() {
        doPurchase.clickAndWait();
    }

    /**
     * Нажимает на кнопку "Сброс", если баланс не ноль
     */
    public void doResetIfBalanceNotNull() {
        if (!balance.getText().equals("0")) {
            doReset.clickAndWait();
        }
    }

    /**
     * Нажимает на кнопку "Сброс"
     */
    public void doReset() {
        doReset.clickAndWait();
    }

    /**
     * Проверяет баланс
     * @param expected оиждаемы баланс
     */
    public final void assertBalance(String expected) {
        jassert.assertEquals("Баланс содержит некорректное значение", "Баланс содержит корректное значение", expected, balance.getText(), true);
    }

    /**
     * Переключает бегунок на нужный тариф начиная со стандартного
     * @param speed необходимая скорость
     */
    public void switchTariff(String speed) {
        int tariffNumber = getTariff(speed).getNumber();
        for (int i = 0; i < tariffNumber - 1; i++) {
            moveRightClick();
        }
    }

    /**
     * Проверяет подключенный тариф
     *
     * @param speed ожидаемая скорость
     * @param time  ожидаемое время
     */
    public final void assertTariff(String speed, String time) {
        String cost = Integer.toString(getTariff(speed).getCost());
        jassert.assertEquals("Текущая скорость содержит некорректное значение", "Текущая скорость содержит корректное значение", speed, speedNowNumber.getText() + speedNowText.getText(), true);
        jassert.assertEquals("Текущая стоимость содержит некорректное значение", "Текущая стоимость содержит корректное значение", cost, costNow.getText(), true);
        jassert.assertEquals("Количество оставшихся дней содержит некорректное значение", "Количество оставшихся дней содержит корректное значение", time, timeNow.getText(), true);
    }

    /**
     * Возвращает нужный тариф
     * Если нет подходящего возвращает дефолтный
     * @param speed скорость тарифа
     * @return нужный тариф
     */
    public Tariff getTariff(String speed) {
        Tariff[] mass = Tariff.values();
        for (int j = 0; j < mass.length; j++) {
            if ((mass[j].getSpeed()).equals(speed)) {
                return mass[j];
            }
        }
        return mass[0];
    }

    /**
     * Пополняет баланс на недостающую для пополнения сумму
     */
    public void doDeficientPayment() {
        int balanceNow = Integer.parseInt(balance.getText());
        int cost = Integer.parseInt(costNew.getText());
        if (balanceNow < cost) {
            doPayment(Integer.toString(cost - balanceNow));
        }
    }

    /*
     * Проверяет, что кнопка "Подключить" активна
     */
    public void assertDoPurchaseButtonIsEnable() {
        jassert.assertTrue("Кнопка \"Подключить\" неактивна", "Кнопка \"Подключить\" активна", doPurchase.isEnabled(), true);
    }

    /*
     * Проверяет, что кнопка "Подключить" неактивна
     */
    public void assertDoPurchaseButtonIsDisable() {
        jassert.assertTrue("Кнопка \"Подключить\" активна", "Кнопка \"Подключить\" неактивна", doPurchase.isDisabled(), true);
    }
}
