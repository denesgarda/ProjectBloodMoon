package com.denesgarda.ProjectBloodMoon;

import com.denesgarda.ProjectBloodMoon.connection.Connect;

import java.util.logging.Logger;

public class Main {
    public static java.sql.Connection conn = null;
    public static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Project: Blood Moon, by Denes G. and Henry K., beta0.1");
        conn = Connect.connect(conn);

    }
}
