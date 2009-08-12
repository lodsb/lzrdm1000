#!/bin/bash

swig -java -package Sequencer.Native.LinuxClock linuxclock.i
gcc -fpic -c linuxclock.c linuxclock_wrap.c -I /usr/lib/jvm/java-6-openjdk/include/
gcc -shared -o linuxclock.so  linuxclock.o linuxclock_wrap.o

