#include <sys/time.h>
#include <stdio.h>

struct timespec req;
struct timespec rem;

struct timeval start_tv;
struct timeval end_tv;

void set_ticktime_nanos(int nanoseconds) {
  long nanos = (long) nanoseconds;
  
  req.tv_sec = 0;
  req.tv_nsec = nanos;
}

void next_tick() {
  nanosleep(&req,&rem);
}