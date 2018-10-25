import jenkins.model.*
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.security.s2m.AdminWhitelistRule
 
def instance = Jenkins.getInstance()

// Disable CLI remoting
instance.getDescriptor("jenkins.CLI").get().setEnabled(false)

// Enable CSRF protection
instance.setCrumbIssuer(new DefaultCrumbIssuer(true))

// Disable deprecated ans unsecure agent protocols
Set<String> agentProtocolsList = ['JNLP4-connect', 'Ping']
instance.setAgentProtocols(agentProtocolsList)

// Enable agent-to-master access control
instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

instance.save()