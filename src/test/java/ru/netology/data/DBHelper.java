package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection conn;

    static {
        try (FileInputStream appPropertiesFile = new FileInputStream("application.properties")) {
            Properties appProperties = new Properties();
            appProperties.load(appPropertiesFile);

            conn = DriverManager.getConnection(appProperties.getProperty("spring.datasource.url"),
                    appProperties.getProperty("spring.datasource.username"),
                    appProperties.getProperty("spring.datasource.password"));
        } catch (SQLException | IOException msg) {
            msg.printStackTrace();
        }
    }

    @SneakyThrows
    public static String runner(String select) {
        return queryRunner.query(conn, select, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusCredit() {
        var select = "select status from credit_request_entity order by created desc limit 1";
         return runner(select);
    }

    @SneakyThrows
    public static String getStatusPay() {
        var select = "select status from payment_entity order by created desc limit 1";
        return runner(select);
    }

    @SneakyThrows
    public static int getAmountPay() {
        var select = "select amount from payment_entity order by created desc limit 1";
        return queryRunner.query(conn, select, new ScalarHandler<>());
    }

    @SneakyThrows
    public static int getCountOrder() {
        var select = "select count(*) from order_entity";
        long result = queryRunner.query(conn, select, new ScalarHandler<>());
        return (int) result;
    }

    @SneakyThrows
    public static String getTransactionId() {
        var select = "select transaction_id from payment_entity order by created desc limit 1";
        return runner(select);
    }

    @SneakyThrows
    public static String getBankId() {
        var select = "select bank_id from credit_request_entity order by created desc limit 1";
        return runner(select);
    }

    @SneakyThrows
    public static String getPaymentId() {
        var select = "select payment_id from order_entity order by created desc limit 1";
        return runner(select);
    }

    @SneakyThrows
    public static String getCreditId() {
        var select = "select credit_id from order_entity order by created desc limit 1";
        return runner(select);
    }
}
