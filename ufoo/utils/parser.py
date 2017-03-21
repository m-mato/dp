#!/usr/bin/python

# Http headers Parser
import re
import csv

pattern = re.compile(".+IP.+>.+Flags.+")
data = set();

def parse(line):
	parts = line.split(': ', 1);
	data.add((parts[0], parts[1]));
	print(line);

def process(line, inside):
	if inside == 0:
	    if pattern.match(line):
	        return 1;
	    else:
	    	return 0;
	elif inside == 1: 
		if (len(line)>5 and 'host'==line[:4]):
			return 2;
		else:
			return 1;
	elif inside == 2:
		if pattern.match(line):
			return 1;
		elif (line is None or not line or line.isspace()):
			return 0;
		else:
			parse(line[:-2]);
			return 2;

with open('./tcpdump_uac_dev') as f:
	inside = 0;
	for line in f:
		inside = process(line, inside);

with open('./headers.csv', 'w') as fp:
	a = csv.writer(fp, delimiter=',');
	a.writerows(data);
