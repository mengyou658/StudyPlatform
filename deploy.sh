apt-get remove StudyPlatform
dpkg -i StudyPlatform_1.0_all.deb
cd /usr/share/studyplatform/
./bin/studyplatform -Dconfig.file=./conf/application.conf

