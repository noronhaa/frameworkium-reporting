# frameworkium-reporting


### Frameworkium 3

This is one of the Frameworkium 3 libraries. Frameworkium 3 is a new release of [frameworkium-core](https://github.com/Frameworkium/frameworkium-core) aka Frameworkium 2.
 In Frameworkium 3 we have split the project up into their own logical modules that can be used independently of the other modules. 
 
 Original Framewokium 2 docs [here]()
 
 Frameworkium 3 libraries:
 1. [frameworkium-ui](https://github.com/Frameworkium/frameworkium-ui)
 2. [frameworkium-api](https://github.com/Frameworkium/frameworkium-api)
 3. [frameworkium-reporting](https://github.com/Frameworkium/frameworkium-reporting)
 4. [frameworkium-jira](https://github.com/Frameworkium/frameworkium-jira)  
 
 Example Projects implementing Frameworkium 3:
 - [frameworkium-examples](https://github.com/Frameworkium/frameworkium-examples/tree/frameworkium3)
 - [frameworkium-bdd](https://github.com/Frameworkium/frameworkium-bdd/tree/frameworkium3)

***

## Summary
frameworkium-reporting is a library providing your automated testing framework with in depth [Allure](https://docs.qameta.io/allure/) 
reports about your test run.

By exposing a series of annotations for you to integrate with your project, frameworkium-reporting will gather detailed information
of your test run, during execution, so a report can be generated after.

This pairs very effectively when used in conjunction with the [frameworkium-ui](https://github.com/Frameworkium/frameworkium-ui) library as
frameworkium-ui has been built to use some frameworkium-reporting functionality if the dependency is present
### Technologies used /supported

frameworkium-reporting is build with and uses the following technologies
- Java
- TestNG
- Cucumber-JVM
- [Allure](https://docs.qameta.io/allure/) (reports) 

## Getting Started
**Note:** have a look at [frameworkium-examples](https://github.com/Frameworkium/frameworkium-examples/tree/frameworkium3) or [frameworkium-bdd](https://github.com/Frameworkium/frameworkium-bdd/tree/frameworkium3) to see examples of 
frameworkium-reporting in action
1. Add the relevant frameworkium-ui dependency to your pom depending on the use of Cucumber or not
```xml 
     <!--for use with just TestNG-->
     <dependency>
           <groupId>com.github.frameworkium</groupId>
           <artifactId>frameworkium-reporting</artifactId>
           <version>3.0-BETA1</version>
           <exclusions>
             <exclusion>
               <groupId>io.qameta.allure</groupId>
               <artifactId>allure-cucumber-jvm</artifactId>
             </exclusion>
           </exclusions>
         </dependency>
         
     <!--for use with Cucumber-JVM-->
     <dependency>
           <groupId>com.github.frameworkium</groupId>
           <artifactId>frameworkium-reporting</artifactId>
           <version>3.0-BETA1</version>
           <exclusions>
             <exclusion>
               <groupId>io.qameta.allure</groupId>
               <artifactId>allure-testng</artifactId>
             </exclusion>
           </exclusions>
     </dependency>
 ```
2. Add Maven Allure plugin to `pom.xml`. Further configuration can/may need to be added to the plugin which can be found
 in more detail in the allure [docs](https://docs.qameta.io/allure/#_maven_6) 
```$xslt
<plugin>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-maven</artifactId>
        <version>2.8</version>
    </plugin>
```
3. Run your tests normally (typically running `mvn clean verify`)
4. Generate report in `target/allure-report` by running `mvn allure:report`

## Enriching your reports with annotations
Adding annotations or adding descriptions to existing annotations will feed into the end reports with extra details and interactiveness

Full Documentation on Allure annotations can be found [here](https://docs.qameta.io/allure/#_features_4)  

### Common annotations

- `@Step` (***Not cucumber @Step***) enables rich reporting of the steps being run. `{0}` and `{1}` (in the example below) 
will be replaced in reports with the values passed into the method. These can be places on multiple methods across your framework 
for drill down debugging functionality
- `@Name` allows additional reporting and debugging information to be used later. eg more descriptive name of a page object/variable
- `@attachment` used for attaching an attachment to the report. ([frameworkium-ui](https://github.com/Frameworkium/frameworkium-ui)
 uses this to automatically add failure screenshot to the report)
### Example

```java
public class LoginPage extends BasePage<LoginPage> {
    
    @Name("Username text field")
    @Visible
    @FindBy(css="input#inputEmail")
    private WebElement usernameField;
    
    @Name("Password text field")
    @FindBy(css="input#inputPassword")
    private WebElement passwordField;
    
    @Step("Enter credentials and return logged in homepage")
    public LoggedInHomepage validLogin(String username, String password) {
        login(username, password);
        return new LoggedInHomepage().get();
    }
    
    @Step("Enter credentials and expect to stay on the login screen")
    public LoginPage invalidLogin(String username, String password) {
        login(username, password);
        return this;
    }
    
    @Step("Enter username {0} and password {1} and click login")
    private void login(String username, String password) {
        usernameField.clear();
        usernameField.sendKeys(username);
    
        passwordField.clear();
        passwordField.sendKeys(password);
    
        loginButton.click();
    }
}
```
  
## How does frameworkium-reporting work?


Information on test run is gathered throughout a test execution from TestNG or Cucumber and added to json files.
These are placed in an _allure-results_ directory, preferably configured to be in the _target_ dir. 


These files, representing a test run, are further enriched with information from annotations you have placed in 
your framework adding more detailed descriptions
of tests and steps and also the ability to drill down into what happens within steps

After the test run a tool such as the maven-allure plugin turns these files into human readable interactive HTML reports 
generated within an _allure-report_ dir 


## jenkins plugin
When running tests via Jenkins, the [Allure Jenkins Plugin](https://wiki.jenkins.io/display/JENKINS/Allure+Plugin) can be added to the Jenkins job, and it will automatically produce and store reports for every run.
 
 
 