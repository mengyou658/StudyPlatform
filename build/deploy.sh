#!/usr/bin/env bash
/etc/init.d/studyplatform_initd stop
apt-get -y remove StudyPlatform
dpkg -i StudyPlatform_1.0_all.deb
/etc/init.d/studyplatform_initd start
