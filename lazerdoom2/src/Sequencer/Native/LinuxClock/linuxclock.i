/* File: example.i */
%module linuxclock
%{
#include "linuxclock.h"
%}
void set_ticktime_nanos(int nanos);
void next_tick();

