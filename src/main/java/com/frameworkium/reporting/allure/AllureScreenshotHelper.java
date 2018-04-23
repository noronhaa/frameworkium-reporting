package com.frameworkium.reporting.allure;

import io.qameta.allure.Allure;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureScreenshotHelper {

   public void addScreenshotToAllureIfUsing(String name, Path content){
       try (InputStream is = Files.newInputStream(content)) {
           Allure.addAttachment(name, is);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
