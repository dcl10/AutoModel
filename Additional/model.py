#! /usr/bin/python3.5

from modeller import *
from modeller.automodel import *
from sys import argv

aln = argv[1]
sequence = argv[2]
known = argv[3]

env = environ()
a = automodel(env, alnfile=aln,
              knowns=known, sequence=sequence)
a.starting_model = 1
a.ending_model = 5
a.make()
