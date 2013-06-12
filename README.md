bsadvise
========

##Overview

Advise to a group of persons about your absence by any way. eg: sms, whatsapp messages, etc.

##Features

1. Create groups, members who can be recieve advise messages from any member of the group it belong.
2. Define a role for each member.
3. Any member can send an advise to the group where it belongs. As well each member can receive advises from other members of the group.
4. Each advise message sent will be stored.
5. Provide a REST API for expose data.
6. Provide a Mobile UI to consume data from the REST API.

##Modules

###Core Application

** Will provide a REST API to be consume by different clients.**

### Mobile Application

** Will provide a UI friendly accesible from Desktop and mobile devices.**

###Snapshot

[![Build Status](https://travis-ci.org/caelwinner/bsadvise.png?branch=master)](https://travis-ci.org/caelwinner/bsadvise)  
Available for Scala 2.10. Based om Casbah 2.6.1.

###Requirements
To build the application and run all test you will need:
1. Download and install MongoDB.

###Build and Install

** Download the Bsadvise from Git.**
** It use SBT as Build Tool so to build just go to the project root and run any of these commands: **

1. sbt compile
2. sbt test
3. sbt update (update dependencies)
4. sbt gen-idea (if you want to use IntelliJ run this and It will generate convert it to IntelliJ Project.)





