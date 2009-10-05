#!/bin/python
print "private static double noteArray[] = new double[] {"
# midi-notes to hz
for i in range(0,127):
	if i < 126:
		print "        ", (2**(float((i-69))/12.0))*440.0, ","
	else:
		print "        ", (2**(float((i-69))/12.0))*440.0
print "};"

noteArray = ["C","C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]

print "public static String noteNameArray[] = new String[] {"
j = -1;
for i in range(0,127):	
	if(i % 12 == 0 and i != 0):
		j = j + 1
	if i < 126:
		print "        ", "\""+noteArray[i%12]+str(j)+"\"", ","
	else:
		print "        ", "\""+noteArray[i%12]+str(j)+"\""
print "};"

noteArray = ["C","CSharp", "D", "DSharp", "E", "F", "FSharp", "G", "GSharp", "A", "ASharp", "B"]

print "public static enum NoteIndex {"
j = -1;
for i in range(0,127):	
	if(i % 12 == 0 and i != 0):
		j = j + 1
	if i < 126:
		print "        ", noteArray[i%12]+str(j),  ","
	else:
		print "        ", noteArray[i%12]+str(j)

print "};";



