import hudson.model.JDK
import hudson.tasks.Maven
import jenkins.model.Jenkins;

// Configure JDK installations
def jdkDescriptorImpl = new JDK.DescriptorImpl();
jdkDescriptorImpl.setInstallations(
    new JDK('Oracle JDK8', '/usr/java/jdk1.8.0_192')
)
jdkDescriptorImpl.save()

// Configure Maven installations
def mvnDescriptorImpl = Jenkins.instance.getExtensionList(Maven.DescriptorImpl.class)[0]
mvnDescriptorImpl.setInstallations(
    new Maven.MavenInstallation('maven3', '/opt/apache-maven-3.5.4', [])
)
mvnDescriptorImpl.save()
