import jenkins.model.*
import hudson.security.*
 
def instance = Jenkins.getInstance()
 
def env = System.getenv()

// Read admin user from file
String userPath = env["JENKINS_ADMIN_USER_FILE"] ?: "/run/secrets/jenkins-user"
File userFile = new File(userPath)
String user = userFile.exists() ? userFile.text.trim() : "jenkins"

// Read admin password from file
String passwordPath = env["JENKINS_ADMIN_PASSWORD_FILE"] ?: "/run/secrets/jenkins-password"
File passwordFile = new File(passwordPath)
String password = passwordFile.exists() ? passwordFile.text.trim() : "jenkins"

// Create admin account 
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, password)
instance.setSecurityRealm(hudsonRealm)
 
def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

instance.save()