// Generate settings-security.xml file with Maven master password.

def env = System.getenv()

// Read master password from file
String passwordPath = env["MAVEN_MASTER_PASSWORD_FILE"] ?: "/run/secrets/jmaven-master-password"
File passwordFile = new File(passwordPath)
String password = passwordFile.exists() ? passwordFile.text.trim() : "undefined"

// Write security settings to file
String jenkinsHome = env["JENKINS_HOME"] ?: System.getProperty("user.dir")
def m2Dir = new File("${jenkinsHome}/.m2")
m2Dir.mkdir()
File securityFile = new File(m2Dir, "settings-security.xml")
securityFile.text = "<settingsSecurity>\n<master>${password}</master>\n</settingsSecurity>\n"