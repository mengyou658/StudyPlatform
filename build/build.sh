#!/usr/bin/env bash
activator debian:packageBin
scp ./target/StudyPlatform_1.0_all.deb ./build/deploy.sh root@rema7.com:~
