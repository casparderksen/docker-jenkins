import jenkins.model.Jenkins

// See https://github.com/samrocketman/jenkins-bootstrap-shared/tree/master/scripts for many scripting examples

def instance = Jenkins.getInstance()

// Disable usage statistics
instance.setNoUsageStatistics(true)

instance.save()