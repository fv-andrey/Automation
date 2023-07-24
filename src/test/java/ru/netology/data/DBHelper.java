package ru.netology.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    public static int getCountOrder() {
        var select = "select count(*) from order_entity";
        long result = queryRunner.query(conn, select, new ScalarHandler<>());
        return (int) result;
    }

    @Data
    @NoArgsConstructor
    public static class Order_entity {
        String id;
        Timestamp created;
        String credit_id;
        String payment_id;
    }

    @SneakyThrows
    public static Order_entity getOrderInfo() {
        var select = "select * from order_entity order by created desc limit 1";
        return queryRunner.query(conn, select, new BeanHandler<>(Order_entity.class));
    }

    @Data
    @NoArgsConstructor
    public static class Credit_request_entity {
        String id;
        String bank_id;
        Timestamp created;
        String status;
    }

    @SneakyThrows
    public static Credit_request_entity getCreditInfo() {
        var select = "select * from credit_request_entity order by created desc limit 1";
        return queryRunner.query(conn, select, new BeanHandler<>(Credit_request_entity.class));
    }

    @Data
    @NoArgsConstructor
    public static class Payment_entity {
        String id;
        int amount;
        Timestamp created;
        String status;
        String transaction_id;
    }

    @SneakyThrows
    public static Payment_entity getPaymentInfo() {
        var select = "select * from payment_entity order by created desc limit 1";
        return queryRunner.query(conn, select, new BeanHandler<>(Payment_entity.class));
    }
}
