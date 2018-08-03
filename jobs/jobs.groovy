// job_name, repo, suite_file,factoryConfigKey
jobList = [
  [ "Login_Path","template.web.auto", "run-bvt.xml","WEBDESKTOP"]
];
// triggerList : name, jobs, driver_config, provider
triggerList = [
  ["Example_Sauce_Web_Trigger","Login_Path","sauce_ff_46_osx_elcapitan","SAUCE_LABS"],
  ["Example_BrowserStack_Web_Trigger","Login_Path","bs_firefox_46_osx_elcapitan","BROWSER_STACK"]
];

triggerList.each {name, jobs,driver_config,provider  -> 

	job("${name}") { 
		parameters {
        	stringParam('debug', 'false', 'Verbose logging on or off.')
        	stringParam('logATOMResults', 'true', 'ATOM logging on or off.')
      	}
		steps {
        downstreamParameterized {
            trigger("${jobs}") {
               parameters {
                    predefinedProp('ciJobName', '${JOB_NAME}')
                    predefinedProp('runDateStamp','${BUILD_TIMESTAMP}')
                    predefinedProp('driverProvider',"${provider}")
        			predefinedProp('driverConfig',"${driver_config}")
        			predefinedProp('debug', 'false')
        			predefinedProp('logATOMResults', 'true')
                }
            }

        }
    }
	}

}  



jobList.each { name, repo, suite_file, factory_config_key -> 

  job("${name}") {
      wrappers {
        injectPasswords {
          injectGlobalPasswords()
        }
      } 
      scm {
          git {
            remote {
                github("ApplauseAuto/${repo}", 'ssh')
                credentials("9b292be1-60fe-41f6-8645-3bb930a80182")
            }
              branches('${branch}')
        }
      }
      parameters {
        stringParam('branch', 'master', 'The branch of the test project to build')
        stringParam('suiteFile', "${suite_file}", 'TestNG suite file to execute')
        stringParam('locale', '${locale}', 'Type of factory to use')
        stringParam('runDateStamp', 'NOTSET', 'Run date stamp to feed into runId.  This is overridden by the trigger.')
        stringParam('ciJobName', 'NOTSET', 'Job Name to feed into runId.  This is overridden by the trigger.')
        stringParam('debug', 'true', 'Verbose logging on or off.  This is overridden by the trigger.')
        stringParam('logATOMResults', 'false', 'ATOM logging on or off.  This is overridden by the trigger.')
        stringParam('driverProvider','${driverProvider}','driver provider to run tests.  This is passed in by the trigger.')
        stringParam('factoryConfigKey',"${factory_config_key}",'Factory Config Key to run tests against.')
        stringParam('driverConfig','${driverConfig}','driver config to run tests on.  This is passed in by the trigger.')
     }
      logRotator {
        numToKeep(100)
        artifactNumToKeep(100)
      }
     steps {
          maven {
            goals('clean')
            goals('compile')
            goals('test')
            mavenOpts('-Xms256m')
            mavenOpts('-Xmx512m')
            property('driver','CONFIGURED_DRIVER')
            property('suiteFile','${suiteFile}')
            property('driverProvider','${driverProvider}')
            property('driverConfig','${driverConfig}')
            property('logATOMResults','${logATOMResults}')
        }
      }
      publishers {
        mailer('brock@applause.com', true, true)
      }
  }
}


listView('Example Jobs') {
    description('Example Tests')
    filterBuildQueue()
    filterExecutors()
    jobs{
        names("Login_Path",)
    }
    columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
      lastDuration()
      buildButton()
  }
}
  
listView('Example Triggers') {
    description('Example Triggers')
    filterBuildQueue()
    filterExecutors()
    jobs{
        names("Example_Sauce_Web_Trigger","Example_BrowserStack_Web_Trigger")
    }
    columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
      lastDuration()
      buildButton()
  }
 }