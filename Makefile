TARGETS			= jenkins nginx

TAG_jenkins		= my/jenkins
TAG_nginx		= my/nginx

ifeq ($(shell uname -s), Linux)
    XARGS_ARGS = --no-run-if-empty
else
    XARGS_ARGS =
endif

.PHONY: default
default: build

.PHONY: build
build: $(TARGETS:=.build)

.PHONY: $(TARGETS:=.build)
$(TARGETS:=.build): %.build: $*
	-docker build -t $(TAG_$*) $*

.PHONY: deploy
deploy: $(STACK:=.deploy)

.PHONY: compose
compose: build
	-docker-compose up -d

.PHONY: clean
clean: rm-containers rm-untagged rm-targets

.PHONY: clobber
clobber: clean rm-images

.PHONY: pristine
pristine: clobber rm-volumes

.PHONY: rm-containers
rm-containers:
	-docker ps -a -q | xargs $(XARGS_ARGS) docker stop
	-docker ps -a -q | xargs $(XARGS_ARGS) docker rm

.PHONY: rm-untagged
rm-untagged:
	-docker images | tail -n +2 | awk '$$1 == "<none>" {print $$3}' | xargs $(XARGS_ARGS) docker rmi

.PHONY: rm-targets
rm-targets: rm-containers
	-docker rmi -f $(foreach tag,$(TARGETS),$(TAG_$(tag)))

.PHONY: rm-images
rm-images: rm-containers
	-docker images | tail -n +2 | awk '{print $$3}' | xargs $(XARGS_ARGS) docker rmi -f

.PHONY: rm-volumes
rm-volumes: rm-containers
	-docker volume ls | tail -n +2 | awk '{print $$2}' | xargs $(XARGS_ARGS) docker volume rm -f
