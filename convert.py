#!/usr/bin/env python3
import sys
import os
import re
import shutil

# Conversion script for migrating Automation v1 projects to Automation v2.
# Finds and replaces a number of common strings to their v2 equivalents.
# Note that this script will only get you "part of the way" there -
# some further work will be required.

assembly_zip = '''<assembly
	xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd">
	<id>zip</id>
	<formats>
		<format>zip</format>
	</formats>
	<includeBaseDirectory>false</includeBaseDirectory>
	<fileSets>
		<fileSet>
			<directory>./</directory>
			<outputDirectory>./</outputDirectory>
			<excludes>
				<exclude>**/*.zip</exclude>
				<exclude>target/**</exclude>
				<exclude>results/**</exclude>
				<exclude>logs/**</exclude>
				<exclude>src/main/resources/builds/**</exclude>
				<exclude>src/main/resources/cfg/**</exclude>
			</excludes>
		</fileSet>
	</fileSets>
</assembly>'''

pom = '''<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	GROUPID
	ARTIFACTID
	VERSION
	<packaging>jar</packaging>
	NAME
	<url>http://maven.apache.org</url>
	<properties>
		<com.applause.sdk.java.version>2.1.515</com.applause.sdk.java.version>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<surefire.version>2.19</surefire.version>
		<surefire.suiteXmlFiles>${suiteFile}</surefire.suiteXmlFiles>
	</properties>
	<build>
		<plugins>
			<plugin>
				<groupId>com.coveo</groupId>
				<artifactId>fmt-maven-plugin</artifactId>
				<version>2.5.1</version>
				<executions>
					<execution>
						<goals>
							<goal>format</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>${surefire.version}</version>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.5.1</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>2.6</version>
				<executions>
					<execution>
						<goals>
							<goal>test-jar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<version>2.10</version>
				<executions>
					<execution>
						<id>copy-dependencies</id>
						<phase>package</phase>
						<goals>
							<goal>copy-dependencies</goal>
						</goals>
						<configuration>
							<outputDirectory>${project.build.directory}/dependency-jars/</outputDirectory>
						</configuration>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>3.1.0</version>
				<executions>
					<execution>
						<phase>test-compile</phase>
						<goals>
							<goal>single</goal>
						</goals>
						<configuration>
							<finalName>zip-with-dependencies</finalName>
							<excludes>
								<exclude>**/*.zip</exclude>
								<exclude>**/*.tar</exclude>
								<exclude>**/target/**</exclude>
							</excludes>
							<appendAssemblyId>false</appendAssemblyId>
							<descriptors>
								<descriptor>src/main/assembly/zip.xml</descriptor>
							</descriptors>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<pluginManagement>
			<plugins>
				<!--This plugin's configuration is used to store Eclipse m2e settings
					only. It has no influence on the Maven build itself. -->
				<plugin>
					<groupId>org.eclipse.m2e</groupId>
					<artifactId>lifecycle-mapping</artifactId>
					<version>1.0.0</version>
					<configuration>
						<lifecycleMappingMetadata>
							<pluginExecutions>
								<pluginExecution>
									<pluginExecutionFilter>
										<groupId>
											org.apache.maven.plugins
										</groupId>
										<artifactId>
											maven-assembly-plugin
										</artifactId>
										<versionRange>
											[3.1.0,)
										</versionRange>
										<goals>
											<goal>single</goal>
										</goals>
									</pluginExecutionFilter>
									<action>
										<ignore />
									</action>
								</pluginExecution>
							</pluginExecutions>
						</lifecycleMappingMetadata>
					</configuration>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
	<profiles>
		<profile>
			<id>ss_run</id>
			<build>
				<plugins>
					<plugin>
						<groupId>org.codehaus.mojo</groupId>
						<artifactId>exec-maven-plugin</artifactId>
						<version>1.4.0</version>
						<executions>
							<execution>
								<id>ss_run</id>
								<phase>test-compile</phase>
								<goals>
									<goal>java</goal>
								</goals>
								<configuration>
									<mainClass>com.applause.auto.integrations.serverside.ServerSideRunner</mainClass>
									<cleanupDaemonThreads>false</cleanupDaemonThreads>
								</configuration>
							</execution>
						</executions>
					</plugin>
				</plugins>
			</build>
		</profile>
	</profiles>
	<repositories>
		<repository>
			<id>auto-sdk-java-dist-mvn-repo</id>
			<url>https://raw.github.com/applauseauto/auto-sdk-java-dist/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>com.applause</groupId>
				<artifactId>auto.sdk.java</artifactId>
				<version>${com.applause.sdk.java.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>com.applause</groupId>
			<artifactId>auto-sdk-java-page-object</artifactId>
		</dependency>
		<dependency>
			<groupId>com.applause</groupId>
			<artifactId>auto-sdk-java-common</artifactId>
		</dependency>
		<dependency>
			<groupId>com.applause</groupId>
			<artifactId>auto-sdk-java-helpers</artifactId>
		</dependency>
		<dependency>
			<groupId>com.applause</groupId>
			<artifactId>auto-sdk-java-elements</artifactId>
		</dependency>
	</dependencies>
</project>'''

system_properties = '''productId=
localResultsDirectory=<project_root>/results/
autoApiUrl=https://prod-auto-api.cloud.applause.com:443
apiKey=
localAppiumUrl=http://localhost:4723/wd/hub'''

log4j_properties = '''property.filename = logs/output.log
property.layoutPattern = [%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS}{GMT} [%t] %c{1} - %msg%n

appender.console.type = Console
appender.console.name = STDOUT
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = ${layoutPattern}

# custom appender to send logs to Applause
appender.serverside.type = ServerSide
appender.serverside.name = ServerSide
appender.serverside.layout.type = PatternLayout
appender.serverside.layout.pattern = ${layoutPattern}
#custom appender implementation
packages = com.applause.auto.log4j2CustomAppenders

appender.file.type = File
appender.file.name = FILE
appender.file.filename = ${filename}
appender.file.layout.type = PatternLayout
appender.file.layout.pattern = ${layoutPattern}

rootLogger.level = debug
rootLogger.appenderRef.stdout.ref = STDOUT
rootLogger.appenderRef.file.ref = FILE
rootLogger.appenderRef.serverside.ref = ServerSide'''

config_list = [
    ('local_android_native.json', '''{
  "device_name" : "Local Android Native",
  "driver_type" : "Android",
  "factory_key" : "MOBILE_ANDROID",
  "caps" : {
    "platformName": "ANDROID",
    "platformVersion": "6.0.1",
    "deviceName": "Android",
    "newCommandTimeout": 3000,
 	"device": "android",
 	"automationName": "uiautomator2"
  }
}'''),
    ('local_android_web.json', '''{
  "device_name" : "Local Android Web",
  "driver_type" : "Android",
  "factory_key" : "WEB_MOBILE",
  "caps" : {
    "browserName" : "Chrome",
    "platformName": "ANDROID",
    "platformVersion": "8.0",
    "deviceName": "Android",
    "udid": "fb56eb63"
  }
}'''),
    ('local_chrome.json', '''{
	"device_name" : "Local Chrome",
	"driver_type" : "chrome",
	"factory_key" : "WEB_DESKTOP_CHROME",
	"manufacturer": "OSX",
	"caps" : { 
		"browserName" : "chrome",
        "acceptSSLAlerts" : "true"
	}
}'''),
    ('local_edge.json', '''{
  "device_name" : "Local Edge",
  "driver_type" : "Edge",
  "factory_key" : "WEB_DESKTOP_EDGE",
  "caps" : {
    "browserName" : "edge",
    "acceptSSLAlerts" : "true"
  }
}'''),
    ('local_emulator_native.json', '''{
  "device_name" : "Local Emulator Native",
  "driver_type" : "Android",
  "factory_key" : "MOBILE_ANDROID",
  "caps" : {
    "platformName": "Android",
    "platformVersion": "8.1",
    "deviceName": "Pixel_2_API_27",
    "nativeWebScreenshot": "true",
    "automationName": "uiautomator2"
  }
}'''),
    ('local_emulator_web.json', '''{
  "device_name" : "Local Emulator Web",
  "driver_type" : "Android",
  "factory_key" : "WEB_MOBILE",
  "caps" : {
    "browserName" : "chrome",
    "platformName": "Android",
    "platformVersion": "8.1",
    "deviceName": "Pixel_2_API_27",
    "nativeWebScreenshot": "true"
  }
}'''),
    ('local_firefox.json', '''{
	"device_name" : "Local Firefox",
	"driver_type" : "Firefox",
	"factory_key" : "WEB_DESKTOP_FIREFOX",
	"caps" : { 
		"browserName" : "firefox",
        "acceptSSLAlerts" : "true"
	}
}'''),
    ('local_internetexplorer.json', '''{
	"device_name" : "Local Internet Explorer",
	"driver_type" : "Internet Explorer",
	"factory_key" : "WEB_DESKTOP_IE",
	"caps" : { 
		"browserName" : "internet explorer",
        "acceptSSLAlerts" : "true"
	}
}'''),
    ('local_ios_native.json', '''{
  "device_name" : "Local iOS Native",
  "driver_type" : "iOS",
  "factory_key" : "MOBILE_IOS",
  "caps" : {
    "platformName": "iOS",
    "platformVersion": "11.4",
    "deviceName": "R2D2",
    "udid": "48b55bb923a1001e8e9e8ec9e7b116ba75a3d9f7",
    "automationName": "XCUITest"
  }
}'''),
    ('local_ios_web.json', '''{
  "device_name" : "Local iOS Web",
  "driver_type" : "iOS",
  "factory_key" : "WEB_MOBILE",
  "caps" : {
    "browserName" : "Safari",
    "platformName": "iOS",
    "platformVersion": "11.2.6",
    "deviceName": "iPhone",
    "udid": "f4c1c9193012e913473cd2a4f5be7bfacd7e02e1",
    "xcodeSigningId": "iPhone Developer"
  }
}'''),
    ('local_safari.json', '''{
	"device_name" : "Local Safari",
	"driver_type" : "Safari",
	"factory_key" : "WEB_DESKTOP_SAFARI",
	"os_name" : "local",
	"os_version": "local",
	"region" : "US",
	"caps" : { 
		"browserName" : "safari",
        "acceptSSLAlerts" : "true"
	}
}'''),
    ('local_simulator_native.json', '''{
  "device_name" : "Local Simulator Native",
  "driver_type" : "iOS",
  "factory_key" : "MOBILE_IOS",
  "caps" : {
    "showXcodeLog": "true",
    "platformName": "iOS",
    "platformVersion": "10.2",
    "deviceName": "iPhone 5",
    "automationName": "XCUITest"
  }
}'''),
    ('local_simulator_web.json', '''{
  "device_name" : "Local Simulator Web",
  "driver_type" : "iOS",
  "factory_key" : "WEB_MOBILE",
  "caps" : {
    "browserName" : "Safari",
    "platformName": "iOS",
    "platformVersion": "11.2",
    "deviceName": "iPhone X",
    "automationName": "XCUITest"
  }
}''')
]


def replace_imports(contents):
    imports = [
        ('com.applause.auto.framework.pageframework.web.PageFactory', 'com.applause.auto.pageobjectmodel.factory.ComponentFactory'),
        ('com.applause.auto.framework.pageframework.web.ChunkFactory', 'com.applause.auto.pageobjectmodel.factory.ComponentFactory'),
        ('com.applause.auto.framework.pageframework.device.DeviceChunkFactory', 'com.applause.auto.pageobjectmodel.factory.ComponentFactory'),
        ('com.applause.auto.framework.pageframework.device.DeviceViewFactory', 'com.applause.auto.pageobjectmodel.factory.ComponentFactory'),

        ('com.applause.auto.framework.pageframework.web.AbstractUIData', 'com.applause.auto.pageobjectmodel.base.BaseElement'),
        ('com.applause.auto.framework.pageframework.web.AbstractPageChunk', 'com.applause.auto.pageobjectmodel.base.BaseComponent'),
        ('com.applause.auto.framework.pageframework.web.AbstractPage', 'com.applause.auto.pageobjectmodel.base.BaseComponent'),
        ('com.applause.auto.framework.pageframework.device.AbstractDeviceUIData', 'com.applause.auto.pageobjectmodel.base.BaseElement'),
        ('com.applause.auto.framework.pageframework.device.AbstractDeviceChunk', 'com.applause.auto.pageobjectmodel.base.BaseComponent'),
        ('com.applause.auto.framework.pageframework.device.AbstractDeviceView', 'com.applause.auto.pageobjectmodel.base.BaseComponent'),

        ('com.applause.auto.framework.pageframework.web.WebElementLocator', 'com.applause.auto.pageobjectmodel.annotation.Locate'),

        ('com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.web.factory.WebMobileImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.web.factory.WebSmallTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.AndroidImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.AndroidPhoneImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.AndroidSmallTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.AndroidTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.IosImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.IosPhoneImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.IosSmallTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),
        ('com.applause.auto.framework.pageframework.device.factory.IosTabletImplementation', 'com.applause.auto.pageobjectmodel.annotation.Implementation'),

        ("import com.applause.auto.framework.pageframework.util.logger.LogController;", ""),

        ('com.applause.auto.framework.pageframework.webcontrols.Button', 'com.applause.auto.pageobjectmodel.elements.Button'),
        ('com.applause.auto.framework.pageframework.webcontrols.Checkbox', 'com.applause.auto.pageobjectmodel.elements.Checkbox'),
        ('com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement', 'com.applause.auto.pageobjectmodel.elements.ContainerElement'),
        ('com.applause.auto.framework.pageframework.webcontrols.Image', 'com.applause.auto.pageobjectmodel.elements.Image'),
        ('com.applause.auto.framework.pageframework.webcontrols.Link', 'com.applause.auto.pageobjectmodel.elements.Link'),
        ('com.applause.auto.framework.pageframework.webcontrols.RadioButton', 'com.applause.auto.pageobjectmodel.elements.RadioButton'),
        ('com.applause.auto.framework.pageframework.webcontrols.Dropdown', 'com.applause.auto.pageobjectmodel.elements.SelectList'),
        ('com.applause.auto.framework.pageframework.webcontrols.Text', 'com.applause.auto.pageobjectmodel.elements.Text'),
        ('com.applause.auto.framework.pageframework.webcontrols.EditField', 'com.applause.auto.pageobjectmodel.elements.TextBox'),
        ('com.applause.auto.framework.pageframework.webcontrols.TextContainer', 'com.applause.auto.pageobjectmodel.elements.TextBox'),

        # some of these gone!
        ('com.applause.auto.framework.pageframework.devicecontrols.Button', 'com.applause.auto.pageobjectmodel.elements.Button'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Checkbox', 'com.applause.auto.pageobjectmodel.elements.Checkbox'),
        ('com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl', 'com.applause.auto.pageobjectmodel.elements.ContainerElement'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Image', 'com.applause.auto.pageobjectmodel.elements.Image'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Link', 'com.applause.auto.pageobjectmodel.elements.Link'),
        ('com.applause.auto.framework.pageframework.devicecontrols.PickerWheel', 'com.applause.auto.pageobjectmodel.elements.Picker'),
        ('com.applause.auto.framework.pageframework.devicecontrols.RadioButton', 'com.applause.auto.pageobjectmodel.elements.RadioButton'),
        ('com.applause.auto.framework.pageframework.devicecontrols.ScrollView', 'com.applause.auto.pageobjectmodel.elements.ScrollView'),
        ('com.applause.auto.framework.pageframework.devicecontrols.SegmentedControl', 'com.applause.auto.pageobjectmodel.elements.SegmentedControl'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Slider', 'com.applause.auto.pageobjectmodel.elements.Slider'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Switch', 'com.applause.auto.pageobjectmodel.elements.Switch'),
        ('com.applause.auto.framework.pageframework.devicecontrols.Text', 'com.applause.auto.pageobjectmodel.elements.Text'),
        ('com.applause.auto.framework.pageframework.devicecontrols.TextBox', 'com.applause.auto.pageobjectmodel.elements.TextBox'),

        ('com.applause.auto.test.ApplausePlatformBaseWebdriverTest', 'com.applause.auto.test.base.BaseWebTest'),
        ('com.applause.auto.pageframework.testdata.TestConstants', 'com.applause.auto.data.Constants'),
    ]

    for old_import, new_import in imports:
        contents = re.sub(old_import, new_import, contents)

    lines = contents.split('\n')
    lines_without_duplicates = []

    existing_imports = set()

    for line in lines:
        if line in existing_imports:
            continue
        elif line[:6] == "import":
            existing_imports.add(line)
        lines_without_duplicates.append(line)

    return '\n'.join(lines_without_duplicates)


def is_page_object(contents):
    return re.search('@.*Implementation', contents)


def convert_implementations(contents):
    implementations = [
        ('@WebDesktopImplementation(.*)', '@Implementation(is = CLASS, on = Platform.WEB)'),
        ('@WebMobileImplementation(.*)', '@Implementation(is = CLASS, on = Platform.WEB_MOBILE)'),
        ('@WebPhoneImplementation(.*)', '@Implementation(is = CLASS, on = Platform.WEB_MOBILE_PHONE)'),
        ('@WebSmallTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.WEB_MOBILE_SMALLTABLET)'),
        ('@WebTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.WEB_MOBILE_TABLET)'),
        ('@AndroidImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_ANDROID)'),
        ('@AndroidPhoneImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_ANDROID_PHONE)'),
        ('@AndroidSmallTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_ANDROID_SMALLTABLET)'),
        ('@AndroidTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_ANDROID_TABLET)'),
        ('@IosImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_IOS)'),
        ('@IosPhoneImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_IOS_PHONE)'),
        ('@IosSmallTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_IOS_SMALLTABLET)'),
        ('@IosTabletImplementation(.*)', '@Implementation(is = CLASS, on = Platform.MOBILE_IOS_TABLET)'),
    ]

    lines = contents.split('\n')
    modified_lines = []

    for line in lines:
        for implementation in implementations:
            if not re.match(implementation[0], line):
                continue
            class_name = line[(line.rfind('(') + 1):-1]
            line = implementation[1]
            line = line.replace("CLASS", class_name)
            break
        modified_lines.append(line)

    return '\n'.join(modified_lines)


def remove_outdated_top_matter(contents):
    lines = contents.split('\n')
    modified_lines = []

    for line in lines:
        if "LogManager" in line:
            continue
        modified_lines.append(line)

    return '\n'.join(modified_lines)


# this logic is transmuted from the v1 core framework
def get_locator_strategy(selector, is_web):
    if is_web:
        if selector.startswith("//") or selector.startswith("(.//") or selector.startswith(".//") or selector.startswith("(//"):
            return "xpath", False
        else:
            return "jQuery", False
    else:
        if "//" in selector:
            return "xpath", False
        if selector.startswith("XCUI") or selector.startswith("**/"):
            return "iOSClassChain", False
        if selector.startswith("@"):
            return "iOSNsPredicate", True
        if selector.startswith("."):
            return "iOSUIAutomation", False
        if selector.startswith("$"):
            return "className", True
        if "UiSelector" in selector:
            return "androidUIAutomator", False
        if selector.startswith("~"):
            return "name", True
        return "id", False


def convert_locators(contents):
    annotations = ['@WebElementLocator\(.*\)', '@MobileElementLocator\(.*\)']

    platforms = [
        ('web ', 'WEB'),
        ('webDesktop ', 'WEB_DESKTOP'),
        ('webMobile ', 'WEB_MOBILE'),
        ('webPhone ', 'WEB_MOBILE_PHONE'),
        ('webTablet ', 'WEB_MOBILE_TABLET'),
        ('webSmallTablet ', 'WEB_MOBILE_SMALLTABLET'),
        ('webiOSPhone ', 'WEB_IOS_PHONE'),
        ('webiOSTablet ', 'WEB_IOS_TABLET'),
        ('webiOSSmallTablet ', 'WEB_IOS_SMALLTABLET'),
        ('webAndroidPhone ', 'WEB_ANDROID_PHONE'),
        ('webAndroidTablet ', 'WEB_ANDROID_TABLET'),
        ('webAndroidSmallTablet ', 'WEB_ANDROID_SMALLTABLET'),
        ('iOS ', 'MOBILE_IOS'),
        ('iOSPhone ', 'MOBILE_IOS_PHONE'),
        ('iOSTablet ', 'MOBILE_IOS_TABLET'),
        ('iOSSmallTablet ', 'MOBILE_IOS_SMALLTABLET'),
        ('android ', 'MOBILE_ANDROID'),
        ('androidPhone ', 'MOBILE_ANDROID_PHONE'),
        ('androidTablet ', 'MOBILE_ANDROID_TABLET'),
        ('androidSmallTablet ', 'MOBILE_ANDROID_SMALLTABLET')
    ]

    new_annotation = '@Locate(STRATEGY = LOCATOR, on = Platform.PLATFORM)'

    lines = contents.split('\n')
    modified_lines = []

    for line in lines:
        if not re.search(annotations[0], line) and not re.search(annotations[1], line):
            modified_lines.append(line)
            continue

        line = line.replace('\\"', "$HACKY-ESCAPED-QUOTE$")

        for platform in platforms:
            if not re.search(platform[0], line):
                continue

            new_line = line[:line.find('@')] + new_annotation

            locator_string = line[line.find(platform[0]):]
            is_web = locator_string[:3] == "web"
            locator_string = locator_string[locator_string.find('"')+1:]
            locator_string = locator_string[:locator_string.find('"')]
            strategy, clip_first = get_locator_strategy(locator_string, is_web)
            new_line = new_line.replace("STRATEGY", strategy)
            if clip_first:
                locator_string = locator_string[1:]
            locator_string = locator_string.replace("$HACKY-ESCAPED-QUOTE$", '\\"')
            new_line = new_line.replace("LOCATOR", '"' + locator_string + '"')
            new_line = new_line.replace("PLATFORM", platform[1])

            modified_lines.append(new_line)

    wait_index = -1

    for index in range(len(modified_lines)):
        if "waitUntilVisible" in modified_lines[index]:
            wait_index = index - 1
            break

    if wait_index != -1:
        spacing = modified_lines[wait_index][:modified_lines[wait_index].find('@')]
        for index in range(wait_index, len(modified_lines)):
            if modified_lines[index][:len(spacing) + 1] == spacing + "}":
                modified_lines[index] = ""
                break
            modified_lines[index] = ""

    return '\n'.join(modified_lines)


def convert_getters(contents):
    i = 0
    getter_name_stack = []

    for match in re.finditer("\t*@Locate\(.*\)\n\t*.*\(.*\) ? {\n(\t*.*;\n)\t*.*}", contents):
        method = match.group(0)
        lines = method.split('\n')
        spacing = lines[0][:lines[0].find('@')]

        method_dec = lines[1].replace(spacing, "")
        method_dec = method_dec[method_dec.index(" ") + 1:]
        return_type = method_dec[:method_dec.index(" ")]
        method_name = method_dec[method_dec.index(" ") + 1:method_dec.index("(") + 1] + ")"

        getter_name_stack.append(method_name)

        lines.insert(1, spacing + "protected " + return_type + " " + method_name + ";")
        lines.insert(2, "")
        lines = lines[:2]
        if '\t' in spacing:
            spacing = spacing + '\t'
        else:
            spacing = spacing + '    '

        i = i + 1
        contents = contents.replace(method, '\n'.join(lines))

    for getter_name in getter_name_stack:
        contents = contents.replace(getter_name, getter_name[:-2])

    return contents


def convert_miscellaneous(contents):
    miscellaneous = [
        # Objects
        # Chunk creation syntax has changed
        ('PageFactory','ComponentFactory'),
        ('ChunkFactory','ComponentFactory'),
        ('DeviceChunkFactory','ComponentFactory'),
        ('DeviceViewFactory','ComponentFactory'),

        ('AbstractUIData', 'BaseElement'),
        ('AbstractPageChunk', 'BaseComponent'),
        ('AbstractPage','BaseComponent'),
        ('AbstractDeviceUIData', 'BaseElement'),
        ('AbstractDeviceChunk', 'BaseComponent'),
        ('AbstractDeviceView','BaseComponent'),

        ('BaseDeviceControl', 'ContainerElement'),
        ('BaseHtmlElement', 'ContainerElement'),
        ('Dropdown', 'SelectList'),
        ('EditField', 'TextBox'),
        ('PickerWheel', 'Picker'),
        ('TextContainer', 'TextBox'),

        ('syncHelper','SyncHelper'),
        ('queryHelper','QueryHelper'),
        ('getSnapshotManager', 'ScreenshotHelper'),
        ('snapshotManager', 'ScreenshotHelper'),

        # Util methods - SELECTORS HAVE TO BECOME ARRAYS OF LOCATORS, some functions have no equivalent
        ('getSyncHelper().waitForElementToAppear', 'getSyncHelper().waitUntilElementPresent'),
        ('getSyncHelper().waitForElementToDisappear', 'getSyncHelper().waitUntilElementNotPresent'),
        ('getSyncHelper().waitForElementTextToAppear', 'getSyncHelper().waitUntilElementTextAppears'),
        ('getSyncHelper().waitForTextToAppear', 'getSyncHelper().waitUntilElementTextAppears'),
        ('getSyncHelper().suspend', 'getSyncHelper().sleep'),

        ('getQueryHelper().getElementCount', 'getQueryHelper().elementCount'),
        ('getQueryHelper().getMobileElementCount', 'getQueryHelper().elementCount'),
        ('getQueryHelper().findIosElement', 'getQueryHelper().findElement'),
        ('getQueryHelper().findIosElements', 'getQueryHelper().findElements'),
        ('getQueryHelper().findAndroidElement', 'getQueryHelper().findElement'),
        ('getQueryHelper().findAndroidElements', 'getQueryHelper().findElements'),

        ('ScreenshotHelper.takeRemoteDeviceScreenshot', 'ScreenshotHelper.takeScreenshot'),

        ('check\(\)', 'click()'), # Checkbox
        ('checkCheckbox', 'click'), # Checkbox
        ('getSrcURL', 'getImageSrc'), # Image
        ('getDriver\(\).getTitle\(\)', 'GETDRIVERGETTITLELEAVETHISALONE'),
        ('getTitle', 'getImageAltText'), # Image
        ('GETDRIVERGETTITLELEAVETHISALONE', 'getDriver().getTitle()'),
        ('clickLink', 'click'), # Link
        ('getURL', 'getLinkURL'), # Link
        ('getSelectedValue', 'getSelected'), # RadioButton
        ('clickRadioButton', 'click'), # RadioButton
        ('getStringValue', 'getTextValue'), # Text
        ('clickText', 'click'), # Text
        ('isTextDisplayed', 'isVisible'), # Text
        ('setText', 'sendKeys'), # TextBox
        ('getText', 'getCurrentText'), # TextBox
        ('enterText', 'sendKeys'), # TextBox
        ('clearTextBox', 'clearText'), # TextBox
        ('clickTextBox', 'click'), # TextBox
        ('getButtonString', 'getText'), # Button
        ('pressButton', 'click'), # Button
        ('getCurrentTextValue', 'getText'),

        # Miscellaneous
        ('LOGGER', 'logger'),
        (r'.*LogController (?i)logger = new LogController.*', r'protected static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());'),
        ('driver','webDriver'),
        ('TestConstants', 'Constants'),
        ('protected void waitUntilVisible', 'public void waitUntilVisible'),
        ('ApplausePlatformBaseWebdriverTest','BaseWebTest')
    ]

    for old_text, new_text in miscellaneous:
        contents = re.sub(old_text, new_text, contents)

    return contents


def add_missing_imports(contents):
    lines = contents.split("\n")
    lines.insert(2, "import com.applause.auto.data.enums.Platform;")
    if "SyncHelper" in contents and "com.applause.auto.util.helper.SyncHelper" not in contents:
        lines.insert(2, "")
    if "QueryHelper" in contents and "com.applause.auto.util.helper.QueryHelper" not in contents:
        lines.insert(2, "import com.applause.auto.util.helper.QueryHelper;")
    if "ScreenshotHelper" in contents and "com.applause.auto.util.helper.ScreenshotHelper" not in contents:
        lines.insert(2, "import com.applause.auto.util.helper.ScreenshotHelper;")

    first_import = -1
    last_import = -1
    for index in range(len(lines)):
        if lines[index][:7] == "import ":
            if first_import == -1:
                first_import = index
            last_import = index
    lines = lines[:first_import] + sorted(lines[first_import:last_import+1]) + lines[last_import+1:]

    return "\n".join(lines)

def remove_double_blanks(contents):
    lines = contents.split("\n")
    cleaner_lines = [lines[0]]
    for line in lines[1:]:
        if cleaner_lines[-1].lstrip() == "" and line.lstrip() == "":
            continue
        cleaner_lines.append(line)
    return "\n".join(cleaner_lines)

def convert_java(filepath):
    contents = ''

    with open(filepath, 'r') as infile:
        contents = infile.read()

        contents = replace_imports(contents)
        contents = convert_miscellaneous(contents)

        if (is_page_object(contents)):
            contents = convert_implementations(contents)
            contents = remove_outdated_top_matter(contents)
            contents = convert_locators(contents)
            contents = convert_getters(contents)
            contents = add_missing_imports(contents)
            contents = remove_double_blanks(contents)

    with open(filepath, 'w') as outfile:
        outfile.write(contents)

    print('Converted ' + filepath)


def convert_pom(filepath):
    current_pom = ''

    with open(filepath, 'r') as infile:
        current_pom = infile.read()

    group_id = re.search("<groupId>.+<\/groupId>", current_pom).group(0)
    artifact_id = re.search("<artifactId>.+<\/artifactId>", current_pom).group(0)
    version = re.search("<version>.+<\/version>", current_pom).group(0)
    name = re.search("<name>.+<\/name>", current_pom).group(0)

    new_pom = pom
    new_pom = new_pom.replace("GROUPID", group_id)
    new_pom = new_pom.replace("ARTIFACTID", artifact_id)
    new_pom = new_pom.replace("VERSION", version)
    new_pom = new_pom.replace("NAME", name)

    with open(filepath, 'w') as outfile:
        outfile.write(new_pom)

    print('Converted ' + filepath)


def add_assembly_file_if_needed(filepath):
    if os.path.isdir(filepath + "/src/") and not os.path.isdir(filepath + "/src/main/assembly"):
        os.mkdir(filepath + "/src/main/assembly")

    with open(filepath + '/src/main/assembly/zip.xml', 'w') as outfile:
        outfile.write(assembly_zip)

    print("Wrote ./src/main/assembly/zip.xml")


def overwrite_resources(filepath):
    log4j_filepath = filepath + "/src/main/resources"
    if not os.path.isdir(log4j_filepath):
        os.makedirs(log4j_filepath)
    with open(log4j_filepath + "/log4j.properties", 'w') as outfile:
        outfile.write(log4j_properties)
    print("Wrote " + log4j_filepath + "/log4j.properties")

    props_filepath = filepath + "/src/main/resources/props"
    if os.path.isdir(props_filepath):
        shutil.rmtree(props_filepath)
    os.makedirs(props_filepath)
    with open(props_filepath + '/system.properties', 'w') as outfile:
        outfile.write(system_properties)
    print("Wrote " + props_filepath + '/system.properties')

    cfg_filepath = filepath + "/src/main/resources/cfg"
    if os.path.isdir(cfg_filepath):
        shutil.rmtree(cfg_filepath)
    os.makedirs(cfg_filepath)

    for config in config_list:
        with open(cfg_filepath + '/' + config[0], 'w') as outfile:
            outfile.write(config[1])
        print("Wrote " + cfg_filepath + '/' + config[0])


for filepath in sys.argv[1:]:
    if os.path.isfile(filepath) and filepath[-5:] == '.java':
        convert_java(filepath)
    if os.path.isfile(filepath) and filepath == 'pom.xml':
        convert_pom(filepath)
    elif os.path.isdir(filepath):
        for root, dirs, files in os.walk(filepath):
            for path in files:
                if path[-5:] == '.java':
                    convert_java(os.path.join(root, path))
                if path == "pom.xml":
                    convert_pom(path)
        add_assembly_file_if_needed(filepath)
        overwrite_resources(filepath)
    else:
        print(filepath + ' is not a valid Java file, pom.xml, or directory.')
