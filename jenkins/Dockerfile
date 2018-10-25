FROM jenkins/jenkins:lts

USER root

# Install Apache Maven

ARG MAVEN_VERSION=3.5.4
ARG MAVEN_SHA=ce50b1c91364cb77efe3776f756a6d92b76d9038b0a0782f7d53acf1e997a14d
ARG MAVEN_BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    echo "${MAVEN_SHA} /tmp/apache-maven.tar.gz" | sha256sum -c - && \
    tar -xzf /tmp/apache-maven.tar.gz -C /opt && \
    rm -f /tmp/apache-maven.tar.gz

# Install Oracle JDK8

ARG JAVA_URL=http://download.oracle.com/otn-pub/java/jdk/8u192-b12/750e1c8617c5452694857ad95c3ee230/jdk-8u192-linux-x64.tar.gz

RUN curl -fsSLjk -H "Cookie: oraclelicense=accept-securebackup-cookie" -o /tmp/jdk8.tar.gz ${JAVA_URL} && \
    mkdir -p /usr/java && \
    tar -xzf /tmp/jdk8.tar.gz -C /usr/java && \
    rm -f /tmp/jdk8.tar.gz

USER jenkins

# Install custom plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Skip Jenkins setup wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Set context path for running behind reverse proxy
ENV JENKINS_OPTS="--prefix=/jenkins"

# Add run-time initialization scripts
COPY groovy/* /usr/share/jenkins/ref/init.groovy.d/
